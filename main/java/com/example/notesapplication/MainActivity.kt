package com.example.notesapplication

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.notesapplication.databinding.ActivityMainBinding
import com.example.notesapplication.fragments.HomeFragment
import com.example.notesapplication.fragments.StaredFragment
import com.example.notesapplication.operations.AddNote
import com.example.notesapplication.variables.NAVIGATION_INFO
import com.example.notesapplication.variables.REFERESH
import com.example.notesapplication.variables.TEST_TAG
import com.google.android.material.navigation.NavigationBarView


class MainActivity : FragmentActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onRestart() {
        super.onRestart()

        if(AddNote.flag) {
            AddNote.flag = false
            Log.i(REFERESH,"Refreshed")
            this.finish()           //Refresh
            startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        initNavigation()

    //Search bar
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {

                if(newText!=null) {
                    setCurrentFragment(HomeFragment(true,newText))
                }
                return true
            }
        })

    }

    private fun initNavigation() {

        val firstFragment=HomeFragment()
        val secondFragment=StaredFragment()

        setCurrentFragment(firstFragment)
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED

        binding.bottomNavigation.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.home-> {
                    setCurrentFragment(firstFragment)

                    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                        override fun onQueryTextSubmit(query: String): Boolean {
                            return false
                        }
                        override fun onQueryTextChange(newText: String): Boolean {
                            Log.i(TEST_TAG,"Query entered ($newText)")
                            if(newText!=null) {
                                setCurrentFragment(HomeFragment(true,newText))
                            }
                            return true
                        }
                    })

                    binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
                    Log.i(NAVIGATION_INFO,"Home Navigation invoked")

                }
                R.id.stared-> {
                    setCurrentFragment(secondFragment)
                    binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
                    Log.i(NAVIGATION_INFO,"Stared Navigation invoked")

                    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                        override fun onQueryTextSubmit(query: String): Boolean {
                            return false
                        }
                        override fun onQueryTextChange(newText: String): Boolean {
                            Log.i(TEST_TAG,"Query entered ($newText)")
                            if(newText!=null) {
                                setCurrentFragment(StaredFragment(true,newText))
                            }
                            return true
                        }
                    })

                }

            }
            true
        }
    }

    private fun setCurrentFragment(fragment:Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

}


