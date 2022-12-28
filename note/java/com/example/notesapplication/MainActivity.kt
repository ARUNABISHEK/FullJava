package com.example.notesapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesapplication.database.model.Notes
import com.example.notesapplication.databinding.ActivityMainBinding
import com.example.notesapplication.folderOption.db.model.Folder
import com.example.notesapplication.folderOption.fragment.FolderFragment.Companion.folderAdapter
import com.example.notesapplication.folderOption.fragment.FolderFragment.Companion.folderViewModel
import com.example.notesapplication.fragments.FragmentPage.Companion.adapter
import com.example.notesapplication.fragments.FragmentPage.Companion.noteViewModel
import com.example.notesapplication.operations.AddNote
import com.example.notesapplication.operations.MainPage
import com.example.notesapplication.operations.NavigationBar
import com.example.notesapplication.view_models.MainViewModel
import com.google.android.material.navigation.NavigationBarView
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : FragmentActivity(),MainPage,NavigationBar {

    companion object {
        val COLOR = mutableListOf<String>()

    }

    private lateinit var viewModel: MainViewModel       //For handle orientation changes
    private lateinit var binding : ActivityMainBinding
    var isFolder = false
    private val requestCode: Int = 1
    override fun onRestart() {
        super.onRestart()
        binding.searchView.setQuery(null,false)
        binding.searchView.clearFocus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val colorCode = this.resources.getStringArray(R.array.color_code)
        COLOR.addAll(colorCode)


        setStatusBar()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
        initNavigation()


        binding.searchView.setOnQueryTextListener(
            searchQuery()
        )

        binding.floatingActionButton.setOnClickListener {
            if(isFolder) {
                createNewFolder()
            }
            else {
                val intent = Intent(this@MainActivity, AddNote::class.java)
                startActivityForResult(intent, requestCode)
            }
        }

    }

    override fun initNavigation() {

        if (viewModel.checkOrientationIsFolder) {
            attributes(viewModel.folderFragment,
                checkOrientationIsFolder = true,
                addButton = true,
                is_folder = true
            )
            binding.floatingActionButton.setImageResource(R.drawable.ic_baseline_folder_24)

        }
        else {
            setCurrentFragment(viewModel.currentFragment)
            viewModel.checkOrientationIsFolder = false

            if(viewModel.currentFragment==viewModel.staredFragment){
                binding.floatingActionButton.visibility = View.GONE
            }
        }

        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED

        binding.bottomNavigation.setOnNavigationItemSelectedListener {

            when(it.itemId){

                R.id.home-> {
                    attributes(viewModel.homeFragment,
                        checkOrientationIsFolder = false,
                        addButton = true,
                        is_folder = false
                    )
                    viewModel.currentFragment = viewModel.homeFragment
                    binding.floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24)
                }

                R.id.stared-> {
                    attributes(viewModel.staredFragment,
                        checkOrientationIsFolder = false,
                        addButton = false,
                        is_folder = false
                    )
                    viewModel.currentFragment = viewModel.staredFragment
                }

                R.id.folder -> {
                    attributes(viewModel.folderFragment,
                        checkOrientationIsFolder = true,
                        addButton = true,
                        is_folder = true
                    )
                    binding.floatingActionButton.setImageResource(R.drawable.ic_baseline_folder_24)
                }
            }
            true
        }
    }

    private fun attributes(fragment: Fragment, checkOrientationIsFolder : Boolean, addButton : Boolean, is_folder : Boolean) {
        setCurrentFragment(fragment)
        viewModel.checkOrientationIsFolder = checkOrientationIsFolder
        binding.searchView.setQuery(null,false)
        binding.searchView.clearFocus()
        isFolder = is_folder
        if(addButton)
            binding.floatingActionButton.visibility = View.VISIBLE
        else
            binding.floatingActionButton.visibility = View.GONE
    }

    override fun setCurrentFragment(fragment:Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

    override fun searchQuery(): SearchView.OnQueryTextListener {

        return object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if(isFolder)
                    folderAdapter.search(newText)
                else
                    adapter.search(newText)

                return true
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==this.requestCode && resultCode == RESULT_OK) {
            val note : Notes = data?.extras?.get("note_object") as Notes
            noteViewModel.insert(note)

            adapter.notifyItemInserted(adapter.itemCount)
            adapter.notifyDataSetChanged()
            Log.i("save","Note saved")
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNewFolder() {
        var name: String
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Folder Name : ")

        val input = EditText(this)
        input.inputType =
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input)

        builder.setPositiveButton(
            "OK"
        ) { _, _ ->
            name = input.text.toString()
            if(name=="")
                name = "Untitled"
            folderViewModel.insert(Folder(0,name, Date().toString()))
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    override fun onBackPressed() {
        if(binding.bottomNavigation.selectedItemId == R.id.home) {
            super.onBackPressed()
            finish()
        } else {
            binding.bottomNavigation.selectedItemId = R.id.home
        }

    }

    private fun setStatusBar() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.main_status_bar)
    }

}




