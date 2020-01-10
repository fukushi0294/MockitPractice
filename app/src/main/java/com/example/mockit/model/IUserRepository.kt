package com.example.mockit.model

import androidx.lifecycle.LiveData

interface IUserRepository {

    fun createUser(user: User)

    fun updateUser(user:User)

    fun deleteUser(user: User)

    fun getOneUser(userId: Int) : User

    fun getAllUser() : LiveData<MutableList<User>>
}