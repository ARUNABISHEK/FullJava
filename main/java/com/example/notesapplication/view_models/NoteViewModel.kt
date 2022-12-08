package com.example.notesapplication.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.database.model.Notes
import com.example.notesapplication.database.repository.NoteRepository
import com.example.notesapplication.variables.METHOD
import com.example.notesapplication.variables.OPERATION_COMPLETED_TAG
import kotlinx.coroutines.launch

class NoteViewModel(private val repository : NoteRepository) : ViewModel() {

    val allNotes = repository.allNotes
    val favouriteNote = repository.favouriteNote

    private fun insertNote(note : Notes) = viewModelScope.launch {
        repository.insert(note)
        Log.i(OPERATION_COMPLETED_TAG,"Inserted")
    }

    private fun updateNote(note : Notes) = viewModelScope.launch {
        repository.update(note)
        Log.i(OPERATION_COMPLETED_TAG,"Updated")
    }

    private fun deleteNote(note : Notes) = viewModelScope.launch {
        repository.delete(note)
        Log.i(OPERATION_COMPLETED_TAG,"Deleted")
    }

    fun save(title : String,content : String,date: String,favourite : Boolean,lock: String?) {
        insertNote(Notes(0,title,content,date,favourite,lock))
        Log.i(METHOD,"save method invoked")
    }

    fun update(note : Notes) {
        updateNote(note)
        Log.i(METHOD,"update method invoked")
    }

    fun delete(note: Notes) {
        deleteNote(note)
        Log.i(METHOD,"delete method invoked")
    }

    fun insert(note : Notes) {
        insertNote(note)
        Log.i(METHOD,"insert method invoked")
    }

}