package com.example.onlinesalestask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryEntryAdapter (private val historyEntries: List<HistoryEntry>)
: RecyclerView.Adapter<HistoryEntryAdapter.ViewHolder>()
{
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define UI elements in the history item layout here
        val expressionTextView: TextView = itemView.findViewById(R.id.tvExpression)
        val resultTextView: TextView = itemView.findViewById(R.id.tvResult)
        val dateTextView: TextView = itemView.findViewById(R.id.tvSubmissionDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = historyEntries[position]
        // Bind data to UI elements
        holder.expressionTextView.text = entry.expression
        holder.resultTextView.text = entry.result
        holder.dateTextView.text = entry.submissionDate
    }

    override fun getItemCount(): Int {
        return historyEntries.size
    }
}