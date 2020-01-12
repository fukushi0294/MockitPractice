package com.example.mockit

import com.example.mockit.model.UserRepository
import com.example.mockit.viewmodel.MyViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*

class MyViewModelUT {

    private fun createViewModel(userRepository: UserRepository= mock())
            = MyViewModel(
        userRepository = userRepository
    )

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testMockedValue() = testDispatcher.runBlockingTest {
        val viewModel = createViewModel(
            userRepository = mock {
                on { doSomeAction() } doReturn true
            }
        )
        Assert.assertFalse(viewModel.status)
        viewModel.mockTest() // launch method doesn't block this thread
        Thread.sleep(1000) // Need to wait some time or use async/await
        Assert.assertTrue(viewModel.status)
    }
}