package com.example.mockit.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mockit.model.IUserRepository
import com.example.mockit.model.User
import com.example.mockit.model.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.Exception


const val TAG = "View model"

class MyViewModel(private val userRepository : IUserRepository) : ViewModel() {

    private val users: MutableLiveData<MutableList<User>> by lazy {
        MutableLiveData<MutableList<User>>().also {
            loadUsers()
        }
    }

    private fun incrementUser(user: User){
        val allUsers = users.value
        allUsers?.add(user)
        users.value = allUsers
    }

    fun getUsers(): LiveData<MutableList<User>> {
        return users
    }

    fun insert(user: User) = viewModelScope.launch {
        userRepository.createUser(user, object :Callback{
            override fun onResponse(call: Call, response: Response) {
                try {
                    val user = response.body as User
                    this@MyViewModel.users.value?.add(user)
                }catch (e : Exception){
                    Log.e(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

    private fun loadUsers() {
        userRepository.getAllUser(object :Callback{
            override fun onResponse(call: Call, response: Response) {
                try {
                    val users = response.body as MutableList<User>
                    this@MyViewModel.users.postValue(users)
                }catch (e : Exception){
                    Log.e(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed:$e")
            }
        })
    }

    var status = false
    fun mockTest() = viewModelScope.launch {
        status = userRepository.doSomeAction()
    }

}