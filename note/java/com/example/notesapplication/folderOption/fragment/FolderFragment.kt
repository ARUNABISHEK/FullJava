package com.example.notesapplication.folderOption.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.R
import com.example.notesapplication.database.NoteDatabase
import com.example.notesapplication.databinding.FragmentFolderBinding
import com.example.notesapplication.folderOption.adapter.FolderAdapter
import com.example.notesapplication.folderOption.db.model.Folder
import com.example.notesapplication.folderOption.db.repository.FolderRepository
import com.example.notesapplication.folderOption.viewModels.FolderViewModel
import com.example.notesapplication.folderOption.viewModels.FolderViewModelFactory
import com.example.notesapplication.fragments.FragmentPage.Companion.adapter
import com.example.notesapplication.fragments.FragmentPage.Companion.noteViewModel

@Suppress("DEPRECATION")
class FolderFragment(private val search : String = "") : Fragment() {

    private lateinit var binding : FragmentFolderBinding

    companion object{
        lateinit var folderViewModel: FolderViewModel
        lateinit var folderAdapter: FolderAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_folder, container,false)
        initViewModel(container)

        if(search=="")
            initRecyclerView(inflater)
        else
            displayFolderList(search)

        folderViewModel.message.observe(viewLifecycleOwner) { event_completion_obj ->
            event_completion_obj.getContentIfNotHandled()?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }


        swipe()
        return binding.root

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
                val currentFolder = folderAdapter.getFolder(viewHolder.adapterPosition)
                if(currentFolder.lock==null)
                    deleteDialogBox(currentFolder)
                else
                    passwordDialogBox(currentFolder)

            }
        }).attachToRecyclerView(binding.recyclerView)

    }


    private fun displayFolderList(text: String) {
        folderAdapter = FolderAdapter()
        binding.recyclerView.adapter = folderAdapter
        folderViewModel.allFolder.observe(viewLifecycleOwner) {
            if(it.isEmpty()) {
                binding.group.visibility = View.VISIBLE
                binding.imageView2.setImageResource(R.drawable.folder_image)
            }
            else {
                binding.group.visibility = View.GONE
            }
            folderAdapter.search(text)

        }
    }

    private fun initRecyclerView(inflater: LayoutInflater) {
        binding.recyclerView.layoutManager = GridLayoutManager(inflater.context,2)
        folderAdapter = FolderAdapter()

        binding.recyclerView.adapter = folderAdapter
        folderViewModel.allFolder.observe(viewLifecycleOwner) {
            if(it.isEmpty()) {
                binding.group.visibility = View.VISIBLE
                binding.imageView2.setImageResource(R.drawable.folder_image)
            }
            else {
                binding.group.visibility = View.GONE
            }
            folderAdapter.setFolder(it)
        }


    }

    private fun initViewModel(container: ViewGroup?) {
        val dao = NoteDatabase.getInstance(container?.context!!).FolderDao()
        val repository = FolderRepository(dao)
        val factory = FolderViewModelFactory(repository)

        //View_Model
        folderViewModel = ViewModelProvider(this@FolderFragment,factory)[FolderViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteDialogBox(folder : Folder) {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setCancelable(false)
        builder?.setTitle("Delete :")
        builder?.setMessage("Do you want delete ${folder.folder_name} folder")

        builder?.setPositiveButton(
            "OK"
        ) { _, _ ->

            noteViewModel.deleteInFolder(folder.folder_id)
            adapter.notifyDataSetChanged()

            folderViewModel.delete(folder)
            folderAdapter.notifyItemRemoved(folder.folder_id)
            //Toast.makeText(context,"${folder.folder_name} is Deleted",Toast.LENGTH_SHORT).show()

        }
        builder?.setNegativeButton(
            "Cancel"
        ) { dialog, _ ->
            Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT)
                .show()
            folderAdapter.notifyDataSetChanged()
            dialog.cancel()

        }

        builder?.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun passwordDialogBox(folder: Folder) {
        var pass: String
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setCancelable(false)
        builder?.setTitle("Password : ")

        val input = EditText(context)
        input.inputType =
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder?.setView(input)

        builder?.setPositiveButton(
            "OK"
        ) { _, _ ->
            pass = input.text.toString()
            val decrypt = noteViewModel.decrypt(folder.lock.toString())
            if (pass == decrypt) {
                deleteDialogBox(folder)
            }
            else {
                folderAdapter.notifyDataSetChanged()
            }

        }
        builder?.setNegativeButton(
            "Cancel"
        ) { dialog, _ ->
            dialog.cancel()
            Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT)
                .show()
           folderAdapter.notifyDataSetChanged() }

        builder?.show()
    }

}