package com.example.onlinesalestask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.onlinesalestask.databinding.FragmentHomeBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.btnSubmit.setOnClickListener {
            val input = binding.etExpression.text.toString()
            val results = evaluateExpressions(input)
            binding.tvResult.text = results.joinToString("\n")

            // Add expressions to the queue for rate limiting
            for (expression in input.split("\n")) {
                viewModel.addExpressionToQueue(expression.trim())
            }
        }

        // Start the coroutine to process the queue
        viewModel.processQueue()

    }
    private fun evaluateExpressions(input: String): List<String> {
        val expressionList = input.split("\n").map { it.trim() }
        val results = mutableListOf<String>()

        val historyDatabaseHelper = HistoryDatabaseHelper(requireContext())  // Initialize the database helper

        for (expression in expressionList) {
            try {
                val result = evaluateExpression(expression)
                results.add("$expression => $result")

                // Insert the history entry into the database
                val entry = HistoryEntry(
                    id = 0, // SQLite will automatically assign an ID
                    expression = expression,
                    result = result.toString(), // Assuming result is of type Double
                    submissionDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                        Date()
                    )
                )
                historyDatabaseHelper.addHistoryEntry(entry)
            } catch (e: Exception) {
                results.add("$expression => Error: ${e.message}")
            }
        }

        return results
    }

    private fun evaluateExpression(expression: String): Double {
        val exp = ExpressionBuilder(expression).build()
        return exp.evaluate()
    }

}