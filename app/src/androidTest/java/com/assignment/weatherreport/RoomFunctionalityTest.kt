package com.assignment.weatherreport

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.assignment.weatherreport.db.AppDatabase
import com.assignment.weatherreport.db.ServiceResponseDao
import com.assignment.weatherreport.db.ServiceResponseEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RoomFunctionalityTest {
    @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private var database: AppDatabase? = null
    private var dao: ServiceResponseDao? = null

    @Mock
    private val observer: Observer<ServiceResponseEntity>? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()
        dao = database!!.serviceResponseDao()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        database!!.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun insert() {
        val todo = ServiceResponseEntity(
            0, "-6.25",
            "53.36",
            "Mist",
            "mist",
            "50d",
            "279.94",
            "274.28",
            "279.82",
            "280.15",
            "1027",
            "93",
            "3000",
            "6.7",
            "30",
            "1585296178",
            "1",
            "IE",
            "1585289373",
            "1585335044",
            "0",
            "Dublin City",
            "200"
        )
        if (observer != null) {
            dao!!.getData().observeForever(observer)
        }
        dao!!.insert(todo)
        verify(observer)?.onChanged(todo)
    }
}