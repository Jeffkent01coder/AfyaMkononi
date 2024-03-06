package com.example.afyamkononi.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.R
import com.example.afyamkononi.adapters.EventsAdapter
import com.example.afyamkononi.databinding.FragmentHomeBinding
import com.example.afyamkononi.model.EventData
import com.example.afyamkononi.screens.*

class HomeFragment : Fragment(), EventsAdapter.OnEventClickListener {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: EventsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventArrayList: ArrayList<EventData>

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

        binding.doctors.setOnClickListener {
            val intent = Intent(requireActivity(), Doctors::class.java)
            startActivity(intent)
        }

        binding.todo.setOnClickListener {
            val intent = Intent(requireActivity(), Todo::class.java)
            startActivity(intent)
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent(requireActivity(), Profile::class.java)
            startActivity(intent)
        }

        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = EventsAdapter(eventArrayList, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


    }

    private fun dataInitialize() {
        eventArrayList = arrayListOf(
            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Heart Checkup","Waiyaki Way","12:00 - 14:00","13/23/2023"),
            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Mental Checkup","Weiwaei Way","12:00 - 14:00","13/23/2023"),
            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Tummy Checkup","Waiyaki Way","12:00 - 15:00","13/23/2023"),
            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Skin Checkup","Waiyaki Way","12:00 - 12:00","13/23/2023"),
            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Heart Checkup","Waiyaki Way","21:00 - 22:00","13/23/2023"),
            EventData(R.drawable.docsimage, "Dr Sean Leaky", "head Checkup","Waiyaki Way","12:00 - 14:00","13/23/2023"),
            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Heart Checkup","Waiyaki Way","12:00 - 14:00","13/23/2023"),
            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Bank Checkup","Waiyaki Way","12:00 - 14:00","13/23/2023"),

        )}

    override fun onEventClick(event: EventData, position: Int) {
        Toast.makeText(requireActivity(),"Event clicked", Toast.LENGTH_LONG).show()
    }
}
