package com.example.notesapplication.operations

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.notesapplication.MainActivity
import com.example.notesapplication.R
import com.example.notesapplication.database.NoteDatabase
import com.example.notesapplication.database.model.Notes
import com.example.notesapplication.database.repository.NoteRepository
import com.example.notesapplication.databinding.AddNoteBinding
import com.example.notesapplication.variables.COLOR
import com.example.notesapplication.variables.OPERATION_COMPLETED_TAG
import com.example.notesapplication.view_models.NoteViewModel
import com.example.notesapplication.view_models.NoteViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class UpdateOrDeleteNote : AppCompatActivity() {

    private lateinit var binding : AddNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var oldNote : Notes
    private var favourite = false
    private var flag = false
    private var lock : String?= null
    private var lockFlag = false

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.add_note)
        binding.noteFrame.setBackgroundColor(COLOR[(0 until COLOR.size).random()])

        binding.dateTextView.text = Date().toString()//getCurrentDate()

        val dao = NoteDatabase.getInstance(this).noteDao()
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)

        //ViewModel
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        //Intent Get Object
        oldNote = intent.extras?.get("noteObject") as Notes

        binding.titleTextView.text = oldNote.title?.toEditable()
        binding.noteTextView.text = oldNote.note?.toEditable()

        binding.SaveImageButton.setOnClickListener {
            Log.i("Tag","Update button")
            updated(oldNote)
        }

        if(oldNote.favourite) {
            favourite = true
            flag = true
            binding.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_24)
        } else {
            favourite = false
            flag = false
            binding.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
        }

        if(oldNote.lock!=null) {
            lock = oldNote.lock
            lockFlag = true
            binding.lockImageButton.setImageResource(R.drawable.ic_baseline_lock_24)
            Log.i("log",lock.toString())
        }
        else {
            Log.i("log","Null")
            lock = null
            lockFlag = false
            binding.lockImageButton.setImageResource(R.drawable.ic_baseline_lock_open_24)
        }

        binding.backArrowButton.setOnClickListener {
            if(binding.titleTextView.text.toString() == oldNote.title.toString() &&
                binding.noteTextView.text.toString() == oldNote.note.toString() &&
                oldNote.favourite == favourite &&
                oldNote.lock == lock)

                goToMainActivity()
            else
                back()
        }

        binding.favouriteImageButton.setOnClickListener {
            if(!flag) {
                favourite = true
                flag = true
                binding.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_24)
            } else {
                favourite = false
                flag = false
                binding.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
        }

        binding.lockImageButton.setOnClickListener {

            var pass: String? = null

            if (lock == null) {
                val builder = AlertDialog.Builder(it.context)
                builder.setTitle("Set Password : ")

                val input = EditText(it.context)
                input.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                builder.setView(input)

                builder.setPositiveButton(
                    "OK"
                ) { dialog, which ->
                    pass = input.text.toString()

                    if (pass == "" || pass == null) {
                        Toast.makeText(it.context, "Please enter password", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.i("lock", pass.toString())
                        lock = pass
                        lockFlag = true
                        binding.lockImageButton.setImageResource(R.drawable.ic_baseline_lock_24)
                    }

                }
                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, which -> dialog.cancel() }

                builder.show()
            }


            else {
                val builder = AlertDialog.Builder(it.context)
                builder.setTitle("Password : ")

                val input = EditText(it.context)
                input.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                builder.setView(input)

                builder.setPositiveButton(
                    "OK"
                ) { dialog, which ->
                    pass = input.text.toString()

                    if (pass == "" || pass == null) {
                        Toast.makeText(it.context, "Please enter password", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.i("lock", pass.toString())
                        setPass(pass)
                    }

                }
                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, which -> dialog.cancel() }

                builder.show()
            }
        }

    }

    private fun setPass(pass : String?) {

        if(lockFlag && pass==oldNote.lock) {

            if (!lockFlag) {
                lock = pass
                lockFlag = true
                binding.lockImageButton.setImageResource(R.drawable.ic_baseline_lock_24)
            } else {
                lock = null
                lockFlag = false
                binding.lockImageButton.setImageResource(R.drawable.ic_baseline_lock_open_24)
            }
        }
        else {
            Toast.makeText(this@UpdateOrDeleteNote,"Wrong Password",Toast.LENGTH_SHORT).show()
        }
    }

    private fun updated(newNote: Notes) {

        if(binding.noteTextView.text.toString()!="" || binding.titleTextView.text.toString()!="") {

            newNote.title = binding.titleTextView.text.toString().trim()
            newNote.note = binding.noteTextView.text.toString().trim()
            newNote.date = binding.dateTextView.text.toString()
            newNote.favourite = favourite
            newNote.lock = lock

            noteViewModel.update(newNote)

            Toast.makeText(this@UpdateOrDeleteNote, "Updated", Toast.LENGTH_SHORT).show()
            goToMainActivity()
        }
        else {
            Toast.makeText(this@UpdateOrDeleteNote,"Please enter text", Toast.LENGTH_SHORT).show()
        }
    }

    private fun back() {
        val builder = AlertDialog.Builder(this)

        with(builder) {
            setTitle("Save? ")
            setMessage("Do you want Exit? ")

            //Save
            setPositiveButton("OK") { _: DialogInterface, _: Int ->
                updated(oldNote)
                Log.i(OPERATION_COMPLETED_TAG,"Saved")
                goToMainActivity()
            }

            //Cancel
            setNegativeButton("CANCEL") { _: DialogInterface, _: Int ->
                Log.i(OPERATION_COMPLETED_TAG,"Canceled")
                goToMainActivity()
            }

        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun goToMainActivity() {

        AddNote.flag = true
        val intent = Intent(this, MainActivity::class.java)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate() : String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted = current.format(formatter)
        return formatted.toString()
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}