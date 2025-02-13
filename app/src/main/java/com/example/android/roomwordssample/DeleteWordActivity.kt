package com.example.android.roomwordssample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class DeleteWordActivity : AppCompatActivity() {

    private lateinit var wordSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_word)

        wordSpinner = findViewById(R.id.spinner_words)

        val wordsList = intent.getStringArrayListExtra(EXTRA_WORD_LIST)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wordsList!!)
        wordSpinner.adapter = adapter

        val button = findViewById<Button>(R.id.button_delete)
        button.setOnClickListener {
            val selectedWord = wordSpinner.selectedItem.toString()
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_DELETE_REPLY, selectedWord)
            setResult(Activity.RESULT_OK, replyIntent)
            Log.d("DeleteWordActivity", "Delete Button pressed")
            Log.d("DeleteWordActivity", "Book deleted: ${selectedWord}")
            finish()
        }
    }

    companion object {
        const val EXTRA_WORD_LIST = "com.example.android.wordlistsql.WORD_LIST"
        const val EXTRA_DELETE_REPLY = "com.example.android.wordlistsql.DELETE_REPLY"
    }
}
