package com.example.notesapplication.operations

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.MainActivity.Companion.COLOR
import com.example.notesapplication.R
import com.example.notesapplication.adapter.ColorAdapter
import com.example.notesapplication.database.model.Notes
import com.example.notesapplication.databinding.AddNoteBinding
import com.example.notesapplication.fragments.FragmentPage.Companion.adapter
import com.example.notesapplication.fragments.FragmentPage.Companion.noteViewModel
import com.example.notesapplication.variables.OPERATION_COMPLETED_TAG
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.*
import java.util.*


@Suppress("DEPRECATION")
class AddNote : AppCompatActivity() {

    private lateinit var binding : AddNoteBinding

    private lateinit var currentNote : Notes
    private var isUpdated = false
    private var favourite = false
    private var flagStar = false
    private var lock : String?= null
    private var lockFlag = false
    private var folderId = -1
    private var isUpdateColorFlag = false

    private var updateColor : Boolean = false


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBackPressed() {

        if(isUpdated &&
            binding.titleTextView.text.toString() == currentNote.title.toString() &&
            binding.noteTextView.text.toString() == currentNote.note.toString() &&
            currentNote.favourite == favourite &&
            currentNote.lock == lock)

            goToCalledActivity()        //Cancel

        else if(binding.titleTextView.text.toString() == "" &&
            binding.noteTextView.text.toString() == "" )
            goToCalledActivity()

        else {
            val builder = AlertDialog.Builder(this)

            with(builder) {

                if (isUpdated) {
                    setTitle("Update ")
                    setMessage("Do you want Exit? ")
                    setPositiveButton("Update") { _: DialogInterface, _: Int ->
                        update()
                        Log.i(OPERATION_COMPLETED_TAG, "Updated")

                    }

                } else {
                    setTitle("Save ")
                    setMessage("Do you want Exit? ")
                    setPositiveButton("Save") { _: DialogInterface, _: Int ->
                        save()
                        Log.i(OPERATION_COMPLETED_TAG, "Saved")
                    }

                }

                //Cancel
                setNegativeButton("Discard") { _: DialogInterface, _: Int ->
                    if (isUpdated) {
                        Toast.makeText(this@AddNote, "Canceled", Toast.LENGTH_SHORT).show()
                    }
                    Log.i(OPERATION_COMPLETED_TAG, "Canceled")
                    goToCalledActivity()
                }

            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.add_note)
        binding.dateTextView.text = Date().toString() //getCurrentDate()

        setStatusBar()
        //Intent Get Object
        if(intent.extras?.get("isUpdate") == true) {
            currentNote = intent.extras?.get("noteObject") as Notes
            isUpdated = intent.extras?.get("isUpdate") as Boolean
            setStatusBar()
            binding.titleTextView.text = currentNote.title?.toEditable()
            binding.noteTextView.text = currentNote.note?.toEditable()
            binding.noteFrame.setBackgroundColor(Color.parseColor(currentNote.color))

            initSymbols(currentNote)
        } else if(intent.extras?.get("folder_id")!=null) {
            folderId = intent.extras?.get("folder_id") as Int

            binding.noteFrame.setBackgroundColor(Color.parseColor(COLOR[noteViewModel.colorIndex]))
            setStatusBar()
        } else {
            binding.noteFrame.setBackgroundColor(Color.parseColor(COLOR[noteViewModel.colorIndex]))
            setStatusBar()
        }

        //Insert
        binding.SaveImageButton.setOnClickListener {
            if(isUpdated)
                update()
            else
                save()
        }

        binding.backArrowButton.setOnClickListener {

            if(isUpdated && !updateColor &&
                binding.titleTextView.text.toString() == currentNote.title.toString() &&
                binding.noteTextView.text.toString() == currentNote.note.toString() &&
                currentNote.favourite == favourite &&
                currentNote.lock == lock)

                goToCalledActivity()

            else if(binding.titleTextView.text.toString() == "" &&
                        binding.noteTextView.text.toString() == "" )
                    goToCalledActivity()

            else
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
            lock(it)
        }

        binding.colorChange.setOnClickListener {

            updateColor = true

            val bottomDialog = BottomSheetDialog(this@AddNote)

            val inflater = LayoutInflater.from(this).inflate(R.layout.color,findViewById(R.id.layout),false)
            bottomDialog.setContentView(inflater)
            bottomDialog.show()

            val colorAdapter = ColorAdapter(binding,this)
            val recyclerView = bottomDialog.findViewById<RecyclerView>(R.id.color_recycler_view)
            recyclerView?.adapter = colorAdapter
            recyclerView?.layoutManager = GridLayoutManager(this,3)

        }
               
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private fun initSymbols(note: Notes) {
        if(note.favourite) {
            favourite = true
            flagStar = true
            binding.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_24)
        } else {
            favourite = false
            flagStar = false
            binding.favouriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
        }

        if(note.lock!=null) {
            lock = note.lock
            lockFlag = true
            binding.lockImageButton.setImageResource(R.drawable.ic_baseline_lock_24)
        }
        else {
            lock = null
            lockFlag = false
            binding.lockImageButton.setImageResource(R.drawable.ic_baseline_lock_open_24)
        }
    }

    private fun lock(it : View) {
        var pass : String?
        val builder = AlertDialog.Builder(it.context)
        builder.setTitle("Password : ")

        val input = EditText(it.context)
        input.inputType =
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input)

        builder.setPositiveButton(
            "OK"
        ) { _, _ -> pass = input.text.toString()

            if(pass=="") {
                Toast.makeText(it.context,"Please enter password", Toast.LENGTH_SHORT).show()
            }
            else {
                Log.i("lock",pass.toString())
                setPass(pass)
            }

        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun setPass(pass : String?) {

        if(pass!=null &&  pass!="") {

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun save() {

        if(binding.noteTextView.text.toString()!="" || binding.titleTextView.text.toString()!="") {
            val title = binding.titleTextView.text.toString().trim()
            val content = binding.noteTextView.text.toString().trim()
            val date = binding.dateTextView.text.toString()

            if(lock!=null)
                lock = noteViewModel.encrypt(lock.toString())

            currentNote = if(folderId!=-1)
                Notes(0, title, content, date, favourite, lock,folderId,COLOR[noteViewModel.colorIndex])
            else
                Notes(0, title, content, date, favourite, lock,folderId,COLOR[noteViewModel.colorIndex])

            goToCalledActivity(currentNote)

        }
        else {
            Toast.makeText(this@AddNote,"Please enter text",Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun update() {
        if(binding.noteTextView.text.toString()!="" || binding.titleTextView.text.toString()!="") {

            currentNote.title = binding.titleTextView.text.toString().trim()
            currentNote.note = binding.noteTextView.text.toString().trim()
            currentNote.date = binding.dateTextView.text.toString()
            if(lock!=null)
                currentNote.lock = noteViewModel.encrypt(lock.toString())
            else
                currentNote.lock = null
            currentNote.favourite = favourite

            if(updateColor)
                currentNote.color = COLOR[noteViewModel.colorIndex]

            noteViewModel.update(currentNote)
            adapter.notifyItemChanged(currentNote.note_id)
            goToCalledActivity()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun back() {

        val builder = AlertDialog.Builder(this)

        with(builder) {

            if(isUpdated) {
                setTitle("Update ")
                setMessage("Do you want Exit? ")
                setPositiveButton("Update") { _: DialogInterface, _: Int ->
                    update()
                    Log.i(OPERATION_COMPLETED_TAG,"Updated")

                }

            } else {
                setTitle("Save ")
                setMessage("Do you want Exit? ")
                setPositiveButton("Save") { _: DialogInterface, _: Int ->
                    save()
                    Log.i(OPERATION_COMPLETED_TAG,"Saved")
                }

            }

            //Cancel
            setNegativeButton("Discard") { _: DialogInterface, _: Int ->
                if(isUpdated) {
                    Toast.makeText(this@AddNote,"Canceled",Toast.LENGTH_SHORT).show()
                }
                Log.i(OPERATION_COMPLETED_TAG,"Canceled")
                goToCalledActivity()
            }

        }
        val alertDialog = builder.create()
        alertDialog.show()

    }

    private fun goToCalledActivity(note: Notes) {
        noteViewModel.colorIndex = (COLOR.indices).random()
        val intent = Intent()
        intent.putExtra("note_object",note)
        setResult(RESULT_OK,intent)
        finish()
    }

    private fun goToCalledActivity() {
        noteViewModel.colorIndex = (COLOR.indices).random()
        val intent = Intent()
        setResult(RESULT_CANCELED,intent)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setStatusBar() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (isUpdated && !isUpdateColorFlag) {
            isUpdateColorFlag = true
            val color = Color.parseColor(currentNote.color.replace("#", "#66"))
            window.statusBarColor = color
        } else {

            val color = Color.parseColor(COLOR[noteViewModel.colorIndex].replace("#", "#66"))
            window.statusBarColor = color
        }
    }

}
