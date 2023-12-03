package com.example.afyamkononi.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afyamkononi.databinding.FragmentHomeBinding
import com.example.afyamkononi.screens.BmiCalculator
import com.example.afyamkononi.screens.News

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bmi.setOnClickListener {
            val intent = Intent(requireActivity(), BmiCalculator::class.java)
            startActivity(intent)
        }

        binding.news.setOnClickListener {
            val intent = Intent(requireActivity(), News::class.java)
            startActivity(intent)
        }
    }
}
