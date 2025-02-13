package com.example.android.roomwordssample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class EditWordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_word)

        editWordView = findViewById(R.id.edit_word)

        val word = intent.getStringExtra(EXTRA_EDIT_WORD)
        editWordView.setText(word)

        val button = findViewById<Button>(R.id.buttonSave)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }

            else {
                val editedWord = editWordView.text.toString()
                replyIntent.putExtra(EXTRA_EDIT_REPLY, editedWord)
                replyIntent.putExtra(EXTRA_EDIT_WORD, word) // Send back the original word as well
                setResult(Activity.RESULT_OK, replyIntent)
                Log.d("EditWordActivity", "Edited book: $editedWord")
            }
            Log.d("EditWordActivity", "Save Button clicked")
            finish()
        }
    }

    companion object {
        const val EXTRA_EDIT_WORD = "com.example.android.wordlistsql.EDIT_WORD"
        const val EXTRA_EDIT_REPLY = "com.example.android.wordlistsql.EDIT_REPLY"
    }
}
