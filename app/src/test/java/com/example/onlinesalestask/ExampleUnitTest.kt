package com.example.onlinesalestask

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.text.Editable
import android.widget.EditText
import androidx.core.widget.TextViewCompat
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Mock
    private lateinit var mockContext: Context
    private lateinit var historyDatabaseHelper: HistoryDatabaseHelper

    @Mock
    lateinit var viewModel: HomeViewModel
    private lateinit var homeFragment: HomeFragment

    @Test
    fun testAddHistoryEntry() {
        // Prepare a test HistoryEntry
        val entry = HistoryEntry(1, "2 + 2", "4.0", "2023-09-05 10:00:00")

        // Mock SQLiteDatabase and ContentValues
        val mockDb = mock(SQLiteDatabase::class.java)
        val mockValues = mock(ContentValues::class.java)

        // Mock database write operation
        `when`(historyDatabaseHelper.writableDatabase).thenReturn(mockDb)
        `when`(mockDb.insert(anyString(), anyString(), any())).thenReturn(1L)

        // Call the method to add a history entry
        historyDatabaseHelper.addHistoryEntry(entry)

        // Verify that ContentValues were correctly prepared and insert was called
        verify(mockValues).put("expression", "2 + 2")
        verify(mockValues).put("result", "4.0")
        verify(mockValues).put("submission_date", "2023-09-05 10:00:00")
        verify(mockDb).insert("history", null, mockValues)
    }



}