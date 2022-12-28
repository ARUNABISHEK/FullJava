package com.example.notesapplication.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.R
import com.example.notesapplication.adapter.Adapter
import com.example.notesapplication.database.NoteDatabase
import com.example.notesapplication.database.model.Notes
import com.example.notesapplication.database.repository.NoteRepository
import com.example.notesapplication.databinding.FragmentPageBinding
import com.example.notesapplication.view_models.NoteViewModel
import com.example.notesapplication.view_models.NoteViewModelFactory
import com.google.android.material.textfield.TextInputLayout


@Suppress("DEPRECATION")
class FragmentPage(private val isStared : Boolean = false,
                   private val search : String = "") : Fragment() {

    private lateinit var binding : FragmentPageBinding

    companion object{
        lateinit var noteViewModel: NoteViewModel
        lateinit var adapter: Adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_page, container,false)
        initViewModel(container)


        if(search=="")
            initRecyclerView(inflater)
        else
            displayNoteList(search)

        noteViewModel.message.observe(viewLifecycleOwner) { event_completion_obj ->
            event_completion_obj.getContentIfNotHandled()?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    private fun displayNoteList(text: String) {
        adapter = Adapter()
        binding.recyclerView.adapter = adapter
        noteViewModel.allNotes.observe(viewLifecycleOwner) {
            if(it.isEmpty()) {
                binding.imageView2.visibility = View.VISIBLE
                binding.imageView2.setImageResource(R.drawable.pencil)
                binding.emptyNoteFlag.visibility = View.VISIBLE

            }
            else {
                binding.emptyNoteFlag.visibility = View.GONE
                binding.imageView2.visibility = View.GONE
            }
            adapter.search(text)

        }
    }

    private fun initRecyclerView(inflater: LayoutInflater) {

        //Initialize_Recycler_View
        binding.recyclerView.layoutManager = LinearLayoutManager(inflater.context)

        if(isStared) {
            adapter = Adapter()
            binding.recyclerView.adapter = adapter
            noteViewModel.favouriteNote.observe(viewLifecycleOwner) {
                if(it.isEmpty()) {
                    binding.imageView2.visibility = View.VISIBLE
                    binding.imageView2.setImageResource(R.drawable.pencil)
                    binding.emptyNoteFlag.visibility = View.VISIBLE

                }
                else {
                    binding.emptyNoteFlag.visibility = View.GONE
                    binding.imageView2.visibility = View.GONE
                }
                adapter.setNote(it)

            }
        }
        else {
            adapter = Adapter()
            binding.recyclerView.adapter = adapter
            noteViewModel.allNotes.observe(viewLifecycleOwner){
                if(it.isEmpty()) {
                    binding.imageView2.visibility = View.VISIBLE
                    binding.imageView2.setImageResource(R.drawable.empty_note)
                    binding.emptyNoteFlag.visibility = View.VISIBLE
                }
                else {
                    binding.emptyNoteFlag.visibility = View.GONE
                    binding.imageView2.visibility = View.GONE
                }

                adapter.setNote(it)
            }
        }

        swipe()

    }

    private fun swipe() {

    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentNote = adapter.getNote(viewHolder.adapterPosition)
                if(currentNote.lock==null)
                    deleteDialogBox(currentNote)
                else
                    passwordDialogBox(currentNote)

            }
        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun initViewModel(container : ViewGroup?) {
        val dao = NoteDatabase.getInstance(container?.context!!).noteDao()
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)

        //View_Model
        noteViewModel = ViewModelProvider(this@FragmentPage,factory)[NoteViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteDialogBox(note : Notes) {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setCancelable(false)
        builder?.setTitle("Delete :")
        builder?.setMessage("Do you want to delete? ")

        builder?.setPositiveButton(
            "OK"
        ) { _, _ ->

            noteViewModel.delete(note)
            adapter.notifyItemRemoved(note.note_id)
            Toast.makeText(context,"Deleted...",Toast.LENGTH_SHORT).show()
        }
        builder?.setNegativeButton(
            "Cancel"
        ) { dialog, _ ->
            Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT)
                .show()
            adapter.notifyDataSetChanged()
            dialog.cancel()

        }

        builder?.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun passwordDialogBox(note: Notes) {

        val dialogBox = context?.let { Dialog(it) }
        dialogBox?.setContentView(R.layout.delete_custom_layout)
        dialogBox?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //dialogBox?.window?.attributes?.windowAnimations = R.style.animation


        val delete = dialogBox?.findViewById<Button>(R.id.delete)
        val cancel = dialogBox?.findViewById<Button>(R.id.cancel)
        val deleteCheck = dialogBox?.findViewById<CheckBox>(R.id.checkBox)

        val textInputLayout: TextInputLayout? = dialogBox?.findViewById(R.id.textInputLayout)
        val pass = textInputLayout!!.editText!!.text

        if (deleteCheck != null) {

            delete?.setOnClickListener {

                if (deleteCheck.isChecked) {
                    if(pass.toString() == noteViewModel.decrypt(note.lock.toString())) {
                        noteViewModel.delete(note)
                        adapter.notifyItemRemoved(note.note_id)
                        dialogBox.dismiss()
                    }
                    else {
                        Toast.makeText(context,"Wrong password",Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    deleteCheck.setTextColor(Color.WHITE)
                    deleteCheck.setBackgroundColor(Color.RED)
                    Toast.makeText(context,"please select check box",Toast.LENGTH_SHORT).show()
                }
            }

            cancel?.setOnClickListener {
                Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT)
                    .show()
                adapter.notifyDataSetChanged()
                dialogBox.dismiss()
            }

            dialogBox.setCancelable(false)

            dialogBox.show()
        }
    }


}
