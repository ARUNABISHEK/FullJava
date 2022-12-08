package com.example.notesapplication.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.R
import com.example.notesapplication.adapter.ListAdapter
import com.example.notesapplication.database.NoteDatabase
import com.example.notesapplication.database.model.Notes
import com.example.notesapplication.database.repository.NoteRepository
import com.example.notesapplication.databinding.FragmentHomeBinding
import com.example.notesapplication.operations.AddNote
import com.example.notesapplication.view_models.NoteViewModel
import com.example.notesapplication.view_models.NoteViewModelFactory
import com.google.android.material.snackbar.Snackbar


class HomeFragment(private val flag : Boolean = false, private val text : String = "") : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var noteViewModel: NoteViewModel
    private var recyclerView : RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initViewModel(container)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container,false)

        //Initialize_Recycler_View
        recyclerView = binding.recyclerView
        recyclerView?.layoutManager = LinearLayoutManager(inflater.context)

        if(flag && text != "")      //Searching sorted note
            displayNoteList(text)
        else {
            displayNoteList(container)      //All note
        }

        //Add New Note
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(context, AddNote::class.java)
            startActivity(intent)
        }

        return binding.root


    }


    private fun initViewModel(container: ViewGroup?) {
        val dao = NoteDatabase.getInstance(container?.context!!).noteDao()
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        //View_Model
        noteViewModel = ViewModelProvider(this,factory)[NoteViewModel::class.java]

    }


    private fun displayNoteList(container: ViewGroup?) {
        Log.i("life","display")
        noteViewModel.allNotes.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = ListAdapter(it,noteViewModel)

            //Swipe for delete
            //--------------------------------------------------------
            var adapter = ListAdapter(it,noteViewModel)
            adapter.notifyDataSetChanged()

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val deletedCourse: Notes = it[viewHolder.adapterPosition]

                    val position = viewHolder.adapterPosition

                    if(it[viewHolder.adapterPosition].lock==null) {


                        //----------------------------------------
                        val builder = container?.let { it1 -> AlertDialog.Builder(it1.context) }

                        with(builder) {
                            this?.setTitle("Conform? ")
                            this?.setMessage("Do you want Delete? ")


                            this?.setPositiveButton("OK") { _: DialogInterface, _: Int ->

                                adapter.notifyItemRemoved(position)
                                noteViewModel.delete(deletedCourse)
                                displayNoteList(container)

                            }

                            this?.setNegativeButton("CANCEL") { _: DialogInterface, _: Int ->
                                noteViewModel.insert(deletedCourse)
                                adapter.notifyItemInserted(position)

                            }



                        }
                        builder?.create()?.show()
                        //----------------------------------------

//                        noteViewModel.delete(it[viewHolder.adapterPosition])
//
//                        adapter.notifyItemRemoved(viewHolder.adapterPosition)

//                        Snackbar.make(binding.recyclerView, "Deleted " + deletedCourse.title, Snackbar.LENGTH_LONG)
//                            .setAction(
//                                "Undo"
//
//                            ) {
//                                noteViewModel.insert(deletedCourse)
//                                adapter.notifyItemInserted(position)
//                            }.show()

                    }

                    else {

                        val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
                        builder?.setTitle("Password : ")

                        val input = EditText(context)
                        input.inputType =
                            InputType.TYPE_TEXT_VARIATION_PASSWORD
                        builder?.setView(input)

                        builder?.setPositiveButton(
                            "OK"
                        ) { dialog, which ->
                            var m_Text = input.text.toString()

                            if(m_Text==it[viewHolder.adapterPosition].lock) {
                                noteViewModel.delete(it[viewHolder.adapterPosition])
                                Snackbar.make(binding.recyclerView, "Deleted " + deletedCourse.title, Snackbar.LENGTH_LONG)
                                    .setAction(
                                        "Undo"

                                    ) {
                                        noteViewModel.insert(deletedCourse)
                                        adapter.notifyItemInserted(position)
                                    }.show()
                            }
                            else {
                                Toast.makeText(context,"Wrong password",Toast.LENGTH_SHORT).show()
                                noteViewModel.allNotes.observe(viewLifecycleOwner) {
                                    binding.recyclerView.adapter = ListAdapter(it,noteViewModel)
                                }
                            }
                        }

                        builder?.setNegativeButton(
                            "Cancel"
                        ) { dialog, which ->
                            dialog.cancel()
                            noteViewModel.allNotes.observe(viewLifecycleOwner) {
                                binding.recyclerView.adapter = ListAdapter(it, noteViewModel)
                            }
                        }

                        builder?.show()

                    }

                }

            }).attachToRecyclerView(binding.recyclerView)


            //--------------------------------------------------------

        }
    }

    private fun displayNoteList(text: String) {

        noteViewModel.allNotes.observe(viewLifecycleOwner) {
            var adapter = ListAdapter(it,noteViewModel)
            adapter.search(text)
            binding.recyclerView.adapter = adapter
        }

    }

    private fun getNote(index : Int) : Notes {
        var note : Notes = Notes(0,"","","")
        noteViewModel.allNotes.observe(viewLifecycleOwner){
            note = it[index]
        }
        return note
    }

}


