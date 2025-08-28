package com.specialtech.diary.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat

class DiaryLocationManager(private val appContext: Context) {
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun ifGpsOn(): Boolean {
        val locationManager = appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun hasLocationPermissions(): Boolean {
        return locationPermissions.all { ContextCompat.checkSelfPermission(appContext, it) == PackageManager.PERMISSION_GRANTED }
    }
    @androidx.annotation.RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getLastKnownLocation(): Location? {
        val locationManager = appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return runCatching  {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?:
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                ?:
                locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        }.getOrNull()
    }
}