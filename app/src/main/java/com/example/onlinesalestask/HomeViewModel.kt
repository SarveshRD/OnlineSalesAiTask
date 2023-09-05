package com.example.onlinesalestask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.LinkedBlockingQueue

class HomeViewModel : ViewModel() {

    val expressionQueue = LinkedBlockingQueue<String>()

    fun addExpressionToQueue(expression: String) {
        expressionQueue.offer(expression)
    }

    // Coroutine to process the queue and send expressions
    fun processQueue() {
        viewModelScope.launch {
            while (true) {
                val expression = expressionQueue.poll()
                if (expression != null) {
                    // Check rate limit and send the expression to the API
                    if (isRateLimitExceeded()) {
                        // Delay or retry
                        delay(1000)
                    } else {
                        sendExpressionToApi(expression)
                    }
                }
                delay(100) // Control the rate at which expressions are processed
            }
        }
    }

    // Function to check if the rate limit is exceeded
    private fun isRateLimitExceeded(): Boolean {
        // Implement rate limit tracking logic here
        return false // Return true if rate limit is exceeded
    }

    private suspend fun sendExpressionToApi(expression: String) {
        try {
            // Make an API request to evaluate the expression
            val result = makeApiRequest(expression)
            // Process the result if needed

            // Simulate a delay for API response (you can replace this with actual API call)
            delay(500)
        } catch (e: Exception) {
            // Handle API request error
            e.printStackTrace()
        }
    }

    // Function to make an API request to evaluate an expression (you need to implement this)
    private fun makeApiRequest(expression: String): Double {
        // Implement your API request logic here
        // You may use libraries like Retrofit for API calls
        // Return the result of the API call (a Double in this case)
        return 0.0
    }
}