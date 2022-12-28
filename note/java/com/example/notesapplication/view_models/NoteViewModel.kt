package com.example.notesapplication.view_models

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.MainActivity.Companion.COLOR
import com.example.notesapplication.SetPassword
import com.example.notesapplication.SetPassword.Companion.GetFlagNumber.ALGORITHM
import com.example.notesapplication.SetPassword.Companion.GetFlagNumber.keyValue
import com.example.notesapplication.database.model.Notes
import com.example.notesapplication.database.repository.NoteRepository
import com.example.notesapplication.operations.EventCompletion
import com.example.notesapplication.variables.METHOD
import com.example.notesapplication.variables.OPERATION_COMPLETED_TAG
import kotlinx.coroutines.*
import org.apache.commons.codec.binary.Base64
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


class NoteViewModel(private val repository : NoteRepository) : ViewModel(),SetPassword {

    val allNotes = repository.allNotes
    val favouriteNote = repository.favouriteNote
    var colorIndex = (COLOR.indices).random()

    private val statusMessage = MutableLiveData<EventCompletion<String>>()
    val message : LiveData<EventCompletion<String>>
        get() = statusMessage

    private fun insertNote(note : Notes) = viewModelScope.launch {
        repository.insert(note)
        Log.i("save","Goto view model background")
        Log.i(OPERATION_COMPLETED_TAG,"Inserted")

        withContext(Dispatchers.Main) {
            statusMessage.value = EventCompletion("Inserted!")
        }
    }

    private fun updateNote(note : Notes) = viewModelScope.launch {
        repository.update(note)
        Log.i(OPERATION_COMPLETED_TAG,"Updated")
        withContext(Dispatchers.Main) {
            statusMessage.value = EventCompletion("Updated!")
        }
    }

    private fun deleteNote(note : Notes) = viewModelScope.launch {
        repository.delete(note)
        Log.i(OPERATION_COMPLETED_TAG,"Deleted")
        withContext(Dispatchers.Main) {
            statusMessage.value = EventCompletion("Deleted!")
        }
    }

    private fun deleteInsideFolder(id : Int) = viewModelScope.launch {
        repository.deleteInsideFolder(id)
        Log.i(OPERATION_COMPLETED_TAG,"Deleted")
        withContext(Dispatchers.Main) {
            statusMessage.value = EventCompletion("Deleted!")
        }
    }

    fun update(note : Notes) {
        val job : Job = updateNote(note)

        if(!job.isActive) {
            runBlocking {
                repository.update(note)
            }
        }
        Log.i(METHOD,"update method invoked")
    }

    fun delete(note: Notes) {
        val job : Job = deleteNote(note)

        if(!job.isActive) {
            runBlocking {
                repository.delete(note)
            }
        }
        Log.i(METHOD,"delete method invoked")
    }

    fun deleteInFolder(id : Int) {
        val job : Job = deleteInsideFolder(id)

        if(!job.isActive) {
            runBlocking {
                repository.deleteInsideFolder(id)
            }
        }
        Log.i(METHOD,"delete method invoked")
    }

    fun insert(note : Notes) {
        Log.i("save",note.toString())
        val job : Job = insertNote(note)

        if(!job.isActive) {
            runBlocking {
                repository.insert(note)
            }
        }

        Log.i(METHOD,"insert method invoked")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun encrypt(password: String): String {

        return encryption(password, generateKey())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun decrypt(password: String): String {

        return decryption(password,generateKey())

    }

    //-------------------------------------------------------------------

    @Throws(Exception::class)
    private fun generateKey(): Key {
        return SecretKeySpec(keyValue, ALGORITHM)
    }

    @Throws(Exception::class)
    fun encryption(valueToEnc: String, key: Key?): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encValue = cipher.doFinal(valueToEnc.toByteArray())
        val encryptedByteValue: ByteArray = Base64().encode(encValue)
        return String(encryptedByteValue)
    }

    @Throws(Exception::class)
    fun decryption(encryptedValue: String, key: Key?): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedBytes: ByteArray = Base64().decode(encryptedValue.toByteArray())
        val decryptValue = cipher.doFinal(decodedBytes)

        return String(decryptValue)
    }

    //-------------------------------------------------------------------

}