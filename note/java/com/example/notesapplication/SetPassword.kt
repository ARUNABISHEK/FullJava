package com.example.notesapplication

interface SetPassword {


    fun encrypt(password: String) : String
    fun decrypt(password: String) : String

    companion object {
        object GetFlagNumber {
            val ALGORITHM : String
                get() = "AES"
            val keyValue : ByteArray
                get() = "1234567891234567".toByteArray()        //length must be 16
        }
    }

}