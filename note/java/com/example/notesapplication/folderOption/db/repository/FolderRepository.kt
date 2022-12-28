package com.example.notesapplication.folderOption.db.repository


import android.util.Log
import com.example.notesapplication.folderOption.db.FolderDao
import com.example.notesapplication.folderOption.db.model.Folder
import com.example.notesapplication.variables.REPOSITORY_CHECK

class FolderRepository(private val dao: FolderDao) {

    val allFolders = dao.showAllFolder()

    suspend fun insert(folder: Folder ) {
        dao.createFolder(folder)
        Log.i(REPOSITORY_CHECK, "Insert repository method invoked")


    }

    suspend fun update(folder: Folder) {
        dao.updateFolder(folder)
        Log.i(REPOSITORY_CHECK, "Update repository method invoked")
    }

    suspend fun delete(folder: Folder) {
        dao.deleteFolder(folder)
        Log.i(REPOSITORY_CHECK, "Delete repository method invoked")
    }

}