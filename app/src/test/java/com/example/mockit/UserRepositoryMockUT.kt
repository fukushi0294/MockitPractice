package com.example.mockit

import android.util.Log
import com.example.mockit.model.User
import com.example.mockit.model.UserRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.spy
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

const val TAG = "MockedHttpServerTest"

class UserRepositoryMockUT {

    private val server = MockWebServer()
    private val dispatcher = object : Dispatcher(){
        override fun dispatch(request: RecordedRequest): MockResponse {
            return if (request == null || request.path == null) {
                MockResponse().setResponseCode(400)
            } else when (request.path) {
                "/create" -> MockResponse().setBody("Hello world!").setResponseCode(200)
                "/update" -> MockResponse().setResponseCode(301)
                "/delete" -> MockResponse().setResponseCode(403)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    @Before
    fun setUp(){
        server.dispatcher = this.dispatcher
        server.start()
    }

    @After
    fun cleanUp(){
        server.shutdown()
    }

    @Test
    fun simpleRequestTest(){
        val spyUserRepository = spy<UserRepository> {
            on {
                getServerURL()
            } doReturn server.url("/").toString()
        }

        val user = User(userId = 1, userName = "TestUser")
        spyUserRepository.createUser(user = user, callback = object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body.toString()
                Assert.assertEquals("Hello world!", body)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Failed")
            }
        })


    }
}