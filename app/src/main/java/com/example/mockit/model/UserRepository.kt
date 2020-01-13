package com.example.mockit.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


class User(val userId: Int, val userName:String)

const val TAG = "Http request"
val mediaType = "application/json; charset=utf-8".toMediaType()

open class UserRepository :IUserRepository{

    private var client = OkHttpClient()
    private val mapper = jacksonObjectMapper()

    override fun getServerURL() : String{
        return  "http://dummy.example.co.jp"
    }

    /**
     * Ref https://stackoverflow.com/questions/57100451/okhttp3-requestbody-createcontenttype-content-deprecated
     */
    private fun createRequestBody(user: User) : RequestBody {
        val jsonString = mapper.writeValueAsString(user)
        return jsonString.toRequestBody(mediaType)
    }

    /**
     * This is default call back property which will be replaced
     */
    private var callback : Callback = object :Callback {

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            Log.d(TAG, response.body.toString())
        }

        override fun onFailure(call: Call, arg1: IOException) {
            Log.e(TAG, arg1.toString())
        }
    }

    override fun createUser(user: User, callback: Callback) {
        val requestBody = createRequestBody(user)
        val request = Request.Builder()
            .url("${getServerURL()}+/create")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(callback)
    }

    override fun updateUser(user: User, callback: Callback) {
        val requestBody = createRequestBody(user)
        val request = Request.Builder()
            .url("${getServerURL()}+/update")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(callback)
    }

    override fun deleteUser(user: User, callback: Callback) {
        val requestBody = createRequestBody(user)
        val request = Request.Builder()
            .url("${getServerURL()}+/delete")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(callback)
    }

    override fun getOneUser(userId: Int, callback: Callback): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllUser(callback: Callback): LiveData<MutableList<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * This is test function to check co-routine scope.
     *
     */
    override fun doSomeAction(): Boolean {
        return false
    }
}