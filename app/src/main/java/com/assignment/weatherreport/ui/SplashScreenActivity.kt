package com.assignment.weatherreport.ui

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.assignment.weatherreport.R

/**
 * Launch activity,check whether GPS is enabled, appropriate location permission is granted, if all this is done then jump to Mainactivity.
 *
 */
class SplashScreenActivity : AppCompatActivity() {

    /**
     * Location permission flag
     */
    val LOCATION_REQUEST = 100
    /**
     * GPS is enabled or not
     */
    var isGPSEnaled: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onResume() {
        super.onResume()
        if (!isGPSEnaled)
            checkGPSStatus()
        else {
            if (isPermissionsGranted())
                gotoNext()
            else
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), LOCATION_REQUEST
                )
        }
    }

    /**
     * Jump to MainActivity after GPS and permission is granted
     *
     */
    fun gotoNext()
    {
        val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    /**
     * Check permission is granted or not
     *
     */
    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED


    /**
     * GPS is enabled or not,if not popup dialog to ask user whether to enable or not
     *
     */
    private fun checkGPSStatus() {
        isGPSEnaled = false
        val manager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            enableGPSDialog()
        } else {
            isGPSEnaled = true
            if (isPermissionsGranted())
                gotoNext()
            else
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), LOCATION_REQUEST
                )
        }
    }

    /**
     * Dialog to get action from user whether to enable the GPS by navigating to Settings page or not.
     *
     */
    private fun enableGPSDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert = builder.create()
        alert.show()
    }

    /**
     * After user clicks on permission grant button in dialog then goto next activity.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                gotoNext()
            }
        }
    }
}
