package com.example.android.roomwordssample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val editWordActivityRequestCode = 2
    private val deleteWordActivityRequestCode = 3
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val add = findViewById<FloatingActionButton>(R.id.add)
        add.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
            Log.d("MainActivity", "The add button has been clicked")
        }

//        val edit = findViewById<FloatingActionButton>(R.id.edit)
//        edit.setOnClickListener {
//            val words = adapter.currentList.map { it.word }
//            if (words.isNotEmpty()) {
//                val intent = Intent(this@MainActivity, EditWordActivity::class.java)
//                intent.putExtra(EditWordActivity.EXTRA_EDIT_WORD, words[0])
//                startActivityForResult(intent, editWordActivityRequestCode)
//            }
//            Log.d("MainActivity", "The edit button has been clicked")
//        }

        val edit = findViewById<FloatingActionButton>(R.id.edit)
        edit.setOnClickListener {
            val words = adapter.currentList
            if (words.isNotEmpty()) {
                val wordToEdit = words[0].word // Edit the first word as an example
                val intent = Intent(this@MainActivity, EditWordActivity::class.java).apply {
                    putExtra(EditWordActivity.EXTRA_EDIT_WORD, wordToEdit)
                }
                startActivityForResult(intent, editWordActivityRequestCode)
                Log.d("MainActivity", "The edit button has been clicked")
            }
        }

        val delete = findViewById<FloatingActionButton>(R.id.delete)
        delete.setOnClickListener {
            val words = adapter.currentList.map { it.word }
            if (words.isNotEmpty()) {
                val intent = Intent(this@MainActivity, DeleteWordActivity::class.java)
                intent.putStringArrayListExtra(DeleteWordActivity.EXTRA_WORD_LIST, ArrayList(words))
                startActivityForResult(intent, deleteWordActivityRequestCode)
            }
            Log.d("MainActivity", "The delete button has been clicked")
        }

        wordViewModel.allWords.observe(this) { words ->
            words.let {
                adapter.submitList(it)
                Log.d("MainActivity", "Previous amount of items in RecycleView: ${adapter.itemCount}")
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let { word ->
                val wordObj = Word(word)
                wordViewModel.insert(wordObj)
                Log.d("MainActivity", "Book added: ${wordObj}")
            }
        }

        else if (requestCode == editWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(EditWordActivity.EXTRA_EDIT_REPLY)?.let { editedWord ->
                val word = Word(editedWord)
                wordViewModel.update(word)
                Log.d("MainActivity", "Book updated: ${word}")
            }
        }

        else if (requestCode == deleteWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(DeleteWordActivity.EXTRA_DELETE_REPLY)?.let { wordToDelete ->
                val word = Word(wordToDelete)
                wordViewModel.delete(word)
                Log.d("MainActivity", "Book deleted: ${word}")
            }
        }
        else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }


    }
    
}


