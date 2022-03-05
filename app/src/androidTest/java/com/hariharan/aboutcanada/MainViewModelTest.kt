package com.hariharan.aboutcanada

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.hariharan.aboutcanada.ui.main.MainViewModel
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.awaitility.Awaitility
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection
import java.nio.charset.StandardCharsets
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * Test cases for Main View Model class
 */
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private var responsecode = -1

    private lateinit var viewmodel: MainViewModel

    private lateinit var mockWebServer: MockWebServer

    @get:Rule
    public val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val application =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        viewmodel = MainViewModel(application as Application)
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun clear() {
        mockWebServer.shutdown()
    }

    @Test
    public fun testFactsList() {
        val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getFactsResponse())
        mockWebServer.enqueue(mockResponse)
        viewmodel.getResponseLD().observeForever { response ->
            responsecode = response?.facts!!.size
        }
        viewmodel.fetchData()
        Awaitility.await().atMost(10, TimeUnit.SECONDS).until(getFakeCallable())
        Assert.assertEquals(14, responsecode)
    }

    private fun getFakeCallable(): Callable<Boolean> {
        return Callable<Boolean> { responsecode != -1; }
    }

    private fun getFactsResponse(): String {
        val inputStream =
            InstrumentationRegistry.getInstrumentation().targetContext.assets.open("facts.json")
        val bytes = inputStream.readBytes()
        val text = String(bytes, StandardCharsets.UTF_8)
        inputStream.close()
        return text
    }


}