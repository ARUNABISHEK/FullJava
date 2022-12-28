package com.example.notesapplication.folderOption.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.SetPassword
import com.example.notesapplication.folderOption.db.model.Folder
import com.example.notesapplication.folderOption.db.repository.FolderRepository
import com.example.notesapplication.operations.EventCompletion
import com.example.notesapplication.variables.METHOD
import com.example.notesapplication.variables.OPERATION_COMPLETED_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.codec.binary.Base64
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class FolderViewModel(private val repository: FolderRepository) : ViewModel(),SetPassword {

    val allFolder = repository.allFolders


    private val statusMessage = MutableLiveData<EventCompletion<String>>()
    val message : LiveData<EventCompletion<String>>
        get() = statusMessage

    private fun createFolder(folder: Folder) = viewModelScope.launch {
        repository.insert(folder)
        Log.i(OPERATION_COMPLETED_TAG,"Inserted")

        withContext(Dispatchers.Main) {
            statusMessage.value = EventCompletion("Folder Created!")
        }
    }

    private fun updateFolder(folder: Folder) = viewModelScope.launch {
        repository.update(folder)
        Log.i(OPERATION_COMPLETED_TAG,"Updated")
        withContext(Dispatchers.Main) {
            statusMessage.value = EventCompletion("Folder name changed!")
        }
    }

    private fun deleteFolder(folder: Folder) = viewModelScope.launch {
        repository.delete(folder)
        Log.i(OPERATION_COMPLETED_TAG,"Deleted")
        withContext(Dispatchers.Main) {
            statusMessage.value = EventCompletion("Folder Deleted!")
        }
    }

    fun update(folder: Folder) {
        updateFolder(folder)
        Log.i(METHOD,"update method invoked")
    }

    fun delete(folder: Folder) {
        deleteFolder(folder)
        Log.i(METHOD,"delete method invoked")
    }

    fun insert(folder: Folder) {
        createFolder(folder)
        Log.i(METHOD,"insert method invoked")
    }

    private fun generateKey(): Key {
        return SecretKeySpec(SetPassword.Companion.GetFlagNumber.keyValue, SetPassword.Companion.GetFlagNumber.ALGORITHM)
    }

    override fun encrypt(password: String): String {
        val cipher = Cipher.getInstance(SetPassword.Companion.GetFlagNumber.ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, generateKey())
        val encValue = cipher.doFinal(password.toByteArray())
        val encryptedByteValue: ByteArray = Base64().encode(encValue)
        return String(encryptedByteValue)
    }

    override fun decrypt(password: String): String {

        val cipher = Cipher.getInstance(SetPassword.Companion.GetFlagNumber.ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, generateKey())
        val decodedBytes: ByteArray = Base64().decode(password.toByteArray())
        val enactVal = cipher.doFinal(decodedBytes)
        return String(enactVal)
    }
}