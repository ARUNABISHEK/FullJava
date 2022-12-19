package com.example.notesapplication

interface SetPassword {
    object getFlagNumber {
        val value: Int
            get() = 3
        val ALGORITHM : String
            get() = "AES"
        val keyValue : ByteArray
            get() = "1234567891234567".toByteArray()        //length must be 16
    }



    fun encrypt(password: String) : String
    fun decrypt(password: String) : String

}