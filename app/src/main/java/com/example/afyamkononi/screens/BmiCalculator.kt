package com.example.afyamkononi.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityBmiCalculatorBinding

class BmiCalculator : AppCompatActivity() {
    private lateinit var binding: ActivityBmiCalculatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBmiCalculatorBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.heightPicker.minValue = 100
        binding.heightPicker.maxValue = 250

        binding.weightPicker.minValue = 30
        binding.weightPicker.maxValue = 150

        binding.weightPicker.setOnValueChangedListener { _, _, _ ->
            calculateBMI()
        }
        binding.heightPicker.setOnValueChangedListener { _, _, _ ->
            calculateBMI()
        }
    }

    private fun calculateBMI() {
        val height = binding.heightPicker.value
        val doubleHeight = height.toDouble() / 100

        val weight = binding.weightPicker.value
        val bmi = weight.toDouble() / (doubleHeight * doubleHeight)

        binding.tvResults.text = String.format("Your BMI IS : %.2f", bmi)
        binding.tvHealthy.text = String.format("You are considered : %s", healthyMessage(bmi))

    }

    private fun healthyMessage(bmi: Double): String {

        if (bmi < 18.5)
            return "UnderWeight"
        if (bmi < 25.0)
            return "Healthy"
        if (bmi < 30.0)
            return "OverWeight"
        return "Obese"
    }
}