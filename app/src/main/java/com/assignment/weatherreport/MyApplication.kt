package com.assignment.weatherreport

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.util.Log
import com.assignment.weatherreport.db.AppDatabase
import com.assignment.weatherreport.service.MyJobService

/**
 * Application class which is contains JobScheduler implementation to schedule the download process for every 2 hours
 *
 */
class MyApplication : Application() {

    companion object {
        /**
         * Cancel the previously created job with JOB_ID
         *
         * @param context
         */
        private fun cancelJob(context: Context) {
            val jobScheduler: JobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(JOB_ID)
        }

        /**
         * create the job to get triggered for every 2 hours, constraints are also added which when meat triggers this JOB.
         *
         * @param context
         */
        fun scheduleJob(context: Context) {
            cancelJob(context)
            val componentName = ComponentName(context, MyJobService::class.java)
            val info = JobInfo.Builder(JOB_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPeriodic(PERIODIC_TIME)
                .build()

            val jobScheduler: JobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val resultCode = jobScheduler.schedule(info)

            val isJobScheduledSuccess = resultCode == JobScheduler.RESULT_SUCCESS
            Log.d("MyApplication", "Job Scheduled ${if (isJobScheduledSuccess) SUCCESS_KEY else FAILED_KEY}")
        }

        val JOB_ID = 123
        /**
         * Repeat for every 2 hours
         */
        val PERIODIC_TIME: Long = 120 * 60 * 1000
        val SUCCESS_KEY = "SUCCESS"
        val FAILED_KEY = "FAILED"
        /**
         * Global variable which gets updated when current location is received.
         */
        var addressString: String = ""
        /**
         * KEY used for service call to get weather data
         */
        var YOUR_API_KEY: String = "5ad7218f2e11df834b0eaf3a33a39d2a"
        /**
         * Global instance of room database
         */
        var database: AppDatabase? = null
    }

}