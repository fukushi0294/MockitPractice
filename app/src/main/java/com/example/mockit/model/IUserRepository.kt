package com.example.mockit.model

import androidx.lifecycle.LiveData
import okhttp3.Callback

interface IUserRepository {
    /**
     * For mock the server URL
     */
    fun getServerURL() :String

    fun createUser(user: User, callback : Callback)

    fun updateUser(user:User, callback : Callback)

    fun deleteUser(user: User, callback : Callback)

    fun getOneUser(userId: Int, callback : Callback) : User

    fun getAllUser(callback : Callback) : LiveData<MutableList<User>>

    fun doSomeAction() : Boolean

}