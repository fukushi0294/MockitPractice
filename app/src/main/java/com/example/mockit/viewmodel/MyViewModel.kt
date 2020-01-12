package com.example.mockit.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.mockit.model.IUserRepository
import com.example.mockit.model.User
import com.example.mockit.model.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyViewModel(private val userRepository : IUserRepository) : ViewModel() {

    private val users: MutableLiveData<MutableList<User>> by lazy {
        MutableLiveData<MutableList<User>>().also {
            loadUsers()
        }
    }

    fun getUsers(): LiveData<MutableList<User>> {
        return users
    }

    fun insert(user: User) = viewModelScope.launch {
        userRepository.createUser(user)
        val allUsers = users.value
        allUsers?.add(user)
        users.value = allUsers
    }

    private fun loadUsers() {
        userRepository.getAllUser()
    }

    var status = false
    fun mockTest()
         = viewModelScope.launch {
            status = userRepository.doSomeAction()
        }

}