package com.example.notesapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.ContactsContract.CommonDataKinds.Note
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.*
import com.example.notesapplication.database.model.Notes
import com.example.notesapplication.databinding.NotesListBinding
import com.example.notesapplication.operations.UpdateOrDeleteNote
import com.example.notesapplication.view_models.NoteViewModel


class ListAdapter(private var list: List<Notes>,
                  private val noteViewModel: NoteViewModel,
                  private val isStared : Boolean = false
                  ) : RecyclerView.Adapter<MyViewHolder>() {

    private lateinit var binding : NotesListBinding
    var myList = mutableListOf<Notes>()

    init {
        myList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.notes_list,parent,false)
        return MyViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(!isStared)
            holder.bind(myList[position],noteViewModel)

        else if(myList[position].favourite)
            holder.bindStared(myList[position],noteViewModel)

    }

    fun search(search : String) {
        myList.clear()
        for(item in list) {
            if(item.title?.lowercase()?.contains(search.lowercase())==true ||
                item.note?.lowercase()?.contains(search.lowercase())==true) {
                myList.add(item)
            }
        }
    }

    fun getNoteAt(position: Int): Notes {
        return myList.get(position)
    }


}


class MyViewHolder(val binding: NotesListBinding) : RecyclerView.ViewHolder(binding.root) {

    private var m_Text : String = ""

    @SuppressLint("ResourceAsColor", "SetTextI18n", "ResourceType")
    fun bind(note : Notes, noteViewModel: NoteViewModel) {
        binding.title.text = note.title
        binding.note.text = note.note
        binding.date.text = note.date
        binding.indexLock.setImageResource(R.drawable.ic_baseline_lock_open_24)
        binding.indexPin.setImageResource(R.drawable.ic_baseline_star_border_24)
        //binding.cardView.setCardBackgroundColor(COLOR[(0 until COLOR.size).random()])


        if(note.lock!=null) {
            binding.cardView.setCardBackgroundColor(Color.RED)
            binding.note.text = "Password Producted"
            binding.indexLock.setImageResource(R.drawable.ic_baseline_lock_24)
        }

        if(note.favourite) {
            binding.indexPin.setImageResource(R.drawable.ic_baseline_star_24)
        }
        //Update
        binding.listItem.setOnClickListener {
            openNote(it.context,note)
        }

        binding.indexPin.setOnClickListener {
            if(note.favourite) {
                binding.indexPin.setImageResource(R.drawable.ic_baseline_star_border_24)
                note.favourite = false
                noteViewModel.update(note)
            }
            else {
                binding.indexPin.setImageResource(R.drawable.ic_baseline_star_24)
                note.favourite = true
                noteViewModel.update(note)
            }
        }

        binding.indexLock.setOnClickListener {
                var pass : String?
                if(note.lock==null) {
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
                            note.lock = pass
                            noteViewModel.update(note)
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
                    ) { _, _ ->
                        pass = input.text.toString()

                        if (pass == note.lock) {
                            note.lock = null
                            noteViewModel.update(note)
                        }

                    }
                    builder.setNegativeButton(
                        "Cancel"
                    ) { dialog, _ -> dialog.cancel() }

                    builder.show()
            }
        }
    }


    fun bindStared(note : Notes, noteViewModel: NoteViewModel) {
        binding.title.text = note.title
        binding.note.text = note.note
        binding.date.text = note.date
        binding.indexLock.setImageResource(R.drawable.ic_baseline_lock_open_24)
        //binding.cardView.setCardBackgroundColor(COLOR[(0 until COLOR.size).random()])
        binding.indexPin.setImageResource(R.drawable.ic_baseline_star_24)
        //Password Producted
        if (note.lock != null) {
            binding.cardView.setCardBackgroundColor(Color.RED)
            binding.note.text = "Password Producted"
            binding.indexLock.setImageResource(R.drawable.ic_baseline_lock_24)
        }

        binding.listItem.setOnClickListener {
            openNote(it.context,note)
        }

        binding.indexPin.setOnClickListener {
            if(note.favourite) {
                note.favourite = false
                noteViewModel.update(note)
            }
        }

        //lock
        binding.indexLock.setOnClickListener {
            var pass : String?
            if(note.lock==null) {
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
                        note.lock = pass
                        noteViewModel.update(note)
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
                ) { _, _ ->
                    pass = input.text.toString()

                    if (pass == note.lock) {
                        note.lock = null
                        noteViewModel.update(note)
                    }

                }
                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog.cancel() }

                builder.show()
            }
        }
    }

    private fun openNote(it : Context,note : Notes) {
        if(note.lock!=null) {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Password : ")

            val input = EditText(it)
            input.inputType =
                InputType.TYPE_TEXT_VARIATION_PASSWORD
            builder.setView(input)

            builder.setPositiveButton(
                "OK"
            ) { dialog, which -> m_Text = input.text.toString()

                if(m_Text==note.lock) {
                    val i = Intent(it, UpdateOrDeleteNote::class.java)
                    i.putExtra("noteObject",note)
                    startActivity(it,i,null)
                }
                else {
                    Toast.makeText(it,"Incorrect password",Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, which -> dialog.cancel() }

            builder.show()
        } else {
            val i = Intent(it, UpdateOrDeleteNote::class.java)
            i.putExtra("noteObject",note)
            startActivity(it,i,null)
        }
    }

}


