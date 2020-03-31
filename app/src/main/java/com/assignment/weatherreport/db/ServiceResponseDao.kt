package com.assignment.weatherreport.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Data Access Object class to access data from Database
 *
 */
@Dao
interface ServiceResponseDao {

    @Query("SELECT * FROM ServiceResponse_table")
    fun getData(): LiveData<ServiceResponseEntity>

    @Insert
    suspend fun insert(vararg user: ServiceResponseEntity)

    @Query("DELETE FROM ServiceResponse_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(user: ServiceResponseEntity)

    @Update
    suspend fun updateUser(vararg user: ServiceResponseEntity)
}