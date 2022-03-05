package com.hariharan.aboutcanada


import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hariharan.aboutcanada.ui.main.MainFragment
import com.hariharan.aboutcanada.ui.main.MainViewModel
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.awaitility.Awaitility
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import java.net.HttpURLConnection
import java.nio.charset.StandardCharsets
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * test case for main fragment
 */
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

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
    fun testEventFragment() {
        val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getFactsResponse())
        mockWebServer.enqueue(mockResponse)
        val scenario = launchFragmentInContainer<MainFragment>()
        Awaitility.await().atMost(30, TimeUnit.SECONDS)
        onView(withId(R.id.recyclerview)).check(CustomAssertions.hasItemCount(14));
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