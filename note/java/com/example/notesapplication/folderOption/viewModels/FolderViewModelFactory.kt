package com.example.notesapplication.folderOption.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapplication.folderOption.db.repository.FolderRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class FolderViewModelFactory(private val repository: FolderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FolderViewModel::class.java)) {
            return FolderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}