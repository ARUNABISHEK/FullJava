package com.example.notesapplication.operations

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
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
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddNote : AppCompatActivity() {

    private lateinit var binding : AddNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private var favourite = false
    private var flagStar = false
    private var lock : String?= null
    private var lockFlag = false

    companion object {
            @JvmStatic var flag: Boolean = false
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.add_note)

        binding.noteFrame.setBackgroundColor(COLOR[(0 until COLOR.size).random()])
        binding.dateTextView.text = Date().toString() //getCurrentDate()

        val dao = NoteDatabase.getInstance(application).noteDao()
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)

        //ViewModel
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        //Insert
        binding.SaveImageButton.setOnClickListener {
            Log.i("Tag","Save button")
            save()
        }

        binding.backArrowButton.setOnClickListener {
            back()
        }

        binding.favouriteImageButton.setOnClickListener {
            if(!flagStar) {
                favourite = true
                flagStar = true
                binding.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_24)
            } else {
                favourite = false
                flagStar = false
                binding.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
        }

        binding.lockImageButton.setOnClickListener {
            var pass : String? = null
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("Password : ")

            val input = EditText(it.context)
            input.inputType =
                InputType.TYPE_TEXT_VARIATION_PASSWORD
            builder.setView(input)

            builder.setPositiveButton(
                "OK"
            ) { dialog, which -> pass = input.text.toString()

                if(pass=="" || pass==null) {
                    Toast.makeText(it.context,"Please enter password",Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.i("lock",pass.toString())
                    setPass(pass)
                }

            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, which -> dialog.cancel() }

            builder.show()

            //Log.i("lock","Pass " + pass.toString())

        }
               
    }

    private fun setPass(pass : String?) {
        if (pass != null) {
            Log.i("lock","Lock" + pass)
        } else {
            Log.i("lock","Lock")
        }
        if(pass!=null &&  pass!="") {
            Log.i("lock","come")
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
    }

    private fun save() {

        if(binding.noteTextView.text.toString()!="" || binding.titleTextView.text.toString()!="") {
            val title = binding.titleTextView.text.toString().trim()
            val content = binding.noteTextView.text.toString().trim()
            val date = binding.dateTextView.text.toString()

            noteViewModel.save(title, content, date,favourite,lock)
            goToMainActivity()
            Toast.makeText(this@AddNote, "Inserted", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this@AddNote,"Please enter text",Toast.LENGTH_SHORT).show()
        }
    }

    private fun back() {

        val builder = AlertDialog.Builder(this)

        with(builder) {
            setTitle("Save? ")
            setMessage("Do you want Exit? ")

            //Save
            setPositiveButton("OK") { _: DialogInterface, _: Int ->
                save()
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
        val intent = Intent(this, MainActivity::class.java)
        //intent.putExtra("flag",false)
        flag = true
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

}