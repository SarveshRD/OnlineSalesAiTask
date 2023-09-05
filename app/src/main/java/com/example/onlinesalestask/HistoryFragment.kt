package com.example.onlinesalestask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinesalestask.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyEntryAdapter: HistoryEntryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the RecyclerView by its ID
        val recyclerView = binding.recyclerViewHistory

        // Retrieve history entries from the database
        val historyEntries = getAllHistoryEntries()

        // Create and set up the adapter for the RecyclerView
        historyEntryAdapter = HistoryEntryAdapter(historyEntries)
        recyclerView.adapter = historyEntryAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    // Function to retrieve all history entries
    private fun getAllHistoryEntries(): List<HistoryEntry> {
        // Retrieve history entries from the database and return them
        // You can use your database helper class here
        val historyDatabaseHelper = HistoryDatabaseHelper(requireContext())
        return historyDatabaseHelper.getAllHistoryEntries()
    }
}