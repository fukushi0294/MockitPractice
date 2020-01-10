package com.example.mockit.model

import androidx.lifecycle.LiveData

class User(val userId: Int, val userName:String)

class UserRepository() :IUserRepository{
    override fun createUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOneUser(userId: Int): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllUser(): LiveData<MutableList<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}