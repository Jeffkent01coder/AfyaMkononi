package com.example.afyamkononi.patients.report

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.afyamkononi.databinding.ActivityReportsBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Reports : AppCompatActivity() {
    private lateinit var binding : ActivityReportsBinding
    private lateinit var database: DatabaseReference
    private val STORAGE_PERMISSION_CODE: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReportsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = Firebase.database.reference

        // Request storage permission if not granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestStoragePermission()
        }

        // Load charts and perform analysis
        loadDataAndGenerateCharts()

        // Print button click listener
        binding.printButton.setOnClickListener {
            createPdf()
        }
    }

    // Load data from Firebase and generate charts
    private fun loadDataAndGenerateCharts() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Retrieve data from Firebase
                val messagesCount = dataSnapshot.child("messages").childrenCount.toInt()
                val bmiDataCount = dataSnapshot.child("BMI_Data").childrenCount.toInt()
                val eventsCount = dataSnapshot.child("UpcomingEvents").childrenCount.toInt()

                // Calculate percentages
                val total = messagesCount + bmiDataCount + eventsCount
                val messagesPercentage = (messagesCount * 100 / total).toFloat()
                val bmiDataPercentage = (bmiDataCount * 100 / total).toFloat()
                val eventsPercentage = (eventsCount * 100 / total).toFloat()

                // Generate HTML for pie chart
                val pieChartHtml = generatePieChartHtml(messagesPercentage, bmiDataPercentage, eventsPercentage)

                // Generate HTML for histogram chart
                val histogramChartHtml = generateHistogramChartHtml(messagesCount, bmiDataCount, eventsCount)

                // Load charts into WebViews
                loadChartIntoWebView(pieChartHtml, com.example.afyamkononi.R.id.pieChartWebView)
                loadChartIntoWebView(histogramChartHtml, com.example.afyamkononi.R.id.histogramChartWebView)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                Toast.makeText(this@Reports, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Generate HTML for pie chart based on percentages
    private fun generatePieChartHtml(messagesPercentage: Float, bmiDataPercentage: Float, eventsPercentage: Float): String {
        val html = """
        <html>
        <head>
            <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
            <script type="text/javascript">
                google.charts.load('current', {'packages':['corechart']});
                google.charts.setOnLoadCallback(drawChart);
                function drawChart() {
                    var data = google.visualization.arrayToDataTable([
                        ['Category', 'Percentage'],
                        ['Messages', $messagesPercentage],
                        ['BMI Data', $bmiDataPercentage],
                        ['Upcoming Events', $eventsPercentage]
                    ]);
                    var options = {
                        title: 'Data Distribution'
                    };
                    var chart = new google.visualization.PieChart(document.getElementById('piechart'));
                    chart.draw(data, options);
                }
            </script>
        </head>
        <body>
            <div id="piechart" style="width: 100%; height: 100%;"></div>
        </body>
        </html>
    """.trimIndent()

        return html
    }

    // Generate HTML for histogram chart based on data counts
    private fun generateHistogramChartHtml(messagesCount: Int, bmiDataCount: Int, eventsCount: Int): String {
        val html = """
        <html>
        <head>
            <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
            <script type="text/javascript">
                google.charts.load('current', {'packages':['corechart']});
                google.charts.setOnLoadCallback(drawChart);
                function drawChart() {
                    var data = google.visualization.arrayToDataTable([
                        ['Category', 'Count'],
                        ['Messages', $messagesCount],
                        ['BMI Data', $bmiDataCount],
                        ['Upcoming Events', $eventsCount]
                    ]);
                    var options = {
                        title: 'Data Distribution'
                    };
                    var chart = new google.visualization.BarChart(document.getElementById('histogramchart'));
                    chart.draw(data, options);
                }
            </script>
        </head>
        <body>
            <div id="histogramchart" style="width: 100%; height: 100%;"></div>
        </body>
        </html>
    """.trimIndent()

        return html
    }

    // Load HTML content into WebView
    private fun loadChartIntoWebView(htmlContent: String, webViewId: Int) {
        val webView: WebView = findViewById(webViewId)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadData(htmlContent, "text/html", "UTF-8")
    }

    // Create PDF using WebView content
    private fun createPdf() {
        // Get PrintManager and WebView instances
        val printManager = getSystemService(PRINT_SERVICE) as PrintManager
        val pieChartWebView = findViewById<WebView>(com.example.afyamkononi.R.id.pieChartWebView)
        val histogramChartWebView = findViewById<WebView>(com.example.afyamkononi.R.id.histogramChartWebView)

        // Create PrintDocumentAdapter for WebViews
        val pieChartPrintAdapter = pieChartWebView.createPrintDocumentAdapter()
        val histogramChartPrintAdapter = histogramChartWebView.createPrintDocumentAdapter()

        // Print pie chart
        pieChartPrintAdapter?.let {
            printManager.print("Pie Chart", it, PrintAttributes.Builder().build())
        }

        // Print histogram chart
        histogramChartPrintAdapter?.let {
            printManager.print("Histogram Chart", it, PrintAttributes.Builder().build())
        }
    }

    // Request storage permission
    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    // Handle permission request result
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createPdf()
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
