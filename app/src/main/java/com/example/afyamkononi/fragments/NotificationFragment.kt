package com.example.afyamkononi.fragments

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
import com.example.afyamkononi.adapters.NotificationAdapter
import com.example.afyamkononi.databinding.FragmentNotificationBinding
import com.example.afyamkononi.model.NotificationData


class NotificationFragment : Fragment(), NotificationAdapter.OnNotificationClickListener {

    private lateinit var binding: FragmentNotificationBinding

    private lateinit var adapter: NotificationAdapter
    private lateinit var recyclerView: RecyclerView
    private var notificationArrayList = mutableListOf<NotificationData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.notificationsRecycler)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = NotificationAdapter(notificationArrayList, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onNotificationClick(notification: NotificationData, position: Int) {
        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_LONG).show()
    }

   private fun dataInitialize(){
       notificationArrayList = arrayListOf(
           NotificationData("BMI Updated", "Your new Bmi is 23.4"),
           NotificationData("New Scan", "New Injury scan"),
           NotificationData("New Meeting", "You have a new meeting"),
           NotificationData("Past Meeting", "Just attended this meeting"),
           NotificationData("BMI Updated", "Your new Bmi is 23.4"),
           NotificationData("New Scan", "New Injury scan"),
           NotificationData("New Meeting", "You have a new meeting"),
           NotificationData("Past Meeting", "Just attended this meeting")
       )
   }


}