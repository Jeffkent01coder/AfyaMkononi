package com.example.afyamkononi.patients.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.afyamkononi.databinding.FragmentNotificationBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private lateinit var symptomsEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        symptomsEditText = binding.symptomsEditText
        submitButton = binding.submitButton
        resultTextView = binding.resultTextView

        submitButton.setOnClickListener {
            val symptoms = symptomsEditText.text.toString()
            val diseases = findDiseases(requireContext(), symptoms)
            resultTextView.text = diseases
        }
    }

    private fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = context.assets.open("diseases.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }

    private fun findDiseases(context: Context, symptoms: String): String {
        var diseases = ""
        try {
            val obj = JSONObject(loadJSONFromAsset(context)!!)
            val diseasesArray = obj.getJSONArray("diseases")
            for (i in 0 until diseasesArray.length()) {
                val disease = diseasesArray.getJSONObject(i)
                val name = disease.getString("name")
                val symptomsArray = disease.getJSONArray("symptoms")
                for (j in 0 until symptomsArray.length()) {
                    val symptom = symptomsArray.getString(j)
                    if (symptoms.toLowerCase().contains(symptom.toLowerCase())) {
                        diseases += "$name\n"
                        break
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return if (diseases.isEmpty()) "No matching diseases found" else diseases
    }
}
