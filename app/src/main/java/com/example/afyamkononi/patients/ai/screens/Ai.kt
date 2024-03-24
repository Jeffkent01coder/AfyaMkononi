package com.example.afyamkononi.patients.ai.screens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.databinding.ActivityAiBinding
import com.example.afyamkononi.patients.ai.adapter.ChatAdapter
import com.example.afyamkononi.patients.ai.constants.Constants
import com.example.afyamkononi.patients.ai.model.CompletionResponse
import com.example.afyamkononi.patients.ai.model.Message
import com.example.afyamkononi.patients.ai.model.MessageType
import com.example.afyamkononi.patients.ai.model.PromptRequest
import com.example.afyamkononi.patients.ai.retrofit.ApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Ai : AppCompatActivity() {

    private lateinit var binding: ActivityAiBinding
    private lateinit var adapter: ChatAdapter
    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide action bar
        supportActionBar?.hide()

        // Set up RecyclerView
        adapter = ChatAdapter(messages)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Handle send button click
        binding.sendButton.setOnClickListener {
            val userInput = binding.inputEditText.text.toString().trim()
            if (userInput.isNotEmpty()) {
                // Add user input to messages list
                messages.add(Message(userInput, MessageType.SENDER))
                adapter.notifyItemInserted(messages.size - 1)
                binding.recyclerView.scrollToPosition(messages.size - 1)

                // Call API to get response
                val requestBody = PromptRequest(userInput)
                fetchCompletion(requestBody)

                // Clear input field
                binding.inputEditText.text.clear()
            }
        }
    }

    private fun fetchCompletion(prompt: PromptRequest) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val authorizationHeader = "Bearer ${Constants.API_KEY}"

        // Create a JSON string representing the request body
        val json = Gson().toJson(
            mapOf(
                "prompt" to prompt.prompt,
                "model" to "gpt-3.5-turbo-instruct",
                "max_tokens" to 500
            )
        )

        // Convert the JSON string to RequestBody
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = service.getCompletion(authorizationHeader, requestBody)

        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    Log.d(TAG, "Response body: $responseBody")

                    // Parse the JSON response to extract the text
                    val gson = Gson()
                    val completionResponse =
                        gson.fromJson(responseBody, CompletionResponse::class.java)
                    val text = completionResponse.choices[0].text

                    // Add the extracted text to the messages list
                    messages.add(Message(text, MessageType.RESPONSE))
                    adapter.notifyItemInserted(messages.size - 1)
                    binding.recyclerView.scrollToPosition(messages.size - 1)
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e(TAG, "Error message: $errorMessage")
                    // Handle API error
                    Toast.makeText(this@Ai, "Error: ${response.message()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "Network error", t)
                // Handle network failure
                Toast.makeText(this@Ai, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    companion object {
        private const val TAG = "AiActivity"
    }
}