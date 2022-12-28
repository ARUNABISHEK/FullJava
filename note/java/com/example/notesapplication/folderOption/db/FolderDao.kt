package com.example.notesapplication.folderOption.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapplication.folderOption.db.model.Folder
import com.example.notesapplication.variables.FOLDER_TABLE_NAME

@Dao
interface FolderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createFolder(folder : Folder)

    @Update
    suspend fun updateFolder(folder : Folder)

    @Delete
    suspend fun deleteFolder(folder : Folder)

    @Query("SELECT * FROM $FOLDER_TABLE_NAME")
    fun showAllFolder(): LiveData<List<Folder>>

    @Query("SELECT * FROM $FOLDER_TABLE_NAME WHERE favourite=1")
    fun favouriteFolder() : LiveData<List<Folder>>

}