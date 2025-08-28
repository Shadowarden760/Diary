package com.homeapps.diary.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.core.content.ContextCompat

class DiaryLocationManager(private val appContext: Context) {
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    enum class LocationErrors {
        ERROR_NO_AVAILABLE_PROVIDERS, ERROR_REQUESTING_LOCATION
    }

    fun ifGpsOn(): Boolean {
        val locationManager = appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun hasLocationPermissions(): Boolean {
        return locationPermissions.all { ContextCompat.checkSelfPermission(appContext, it) == PackageManager.PERMISSION_GRANTED }
    }

    fun requestSingleLocationUpdate(
        onLocationReceived: (Location) -> Unit,
        onError: (LocationErrors) -> Unit
    ) {
        val locationManager = appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = getAvailableLocationProvider(locationManager)
        if (provider == null) {
            onError(LocationErrors.ERROR_NO_AVAILABLE_PROVIDERS)
        } else {
            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    onLocationReceived(location)
                    locationManager.removeUpdates(this)
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { /* do nothing */ }
                override fun onProviderEnabled(provider: String) { /* do nothing */ }
                override fun onProviderDisabled(provider: String) { /* do nothing */ }
            }

            try {
                locationManager.requestLocationUpdates(
                    provider,
                    MIN_REQUEST_TIME_MS,
                    MIN_DISTANCE_UPDATE_M,
                    locationListener,
                    Looper.getMainLooper()
                )
            } catch (_: SecurityException) {
                onError(LocationErrors.ERROR_REQUESTING_LOCATION)
            } catch (_: Exception) {
                onError(LocationErrors.ERROR_REQUESTING_LOCATION)
            }
        }
    }

    private fun getAvailableLocationProvider(locationManager: LocationManager): String? {
        locationProviders.forEach { provider ->
            if (locationManager.isProviderEnabled(provider)) {
                return provider
            }
        }
        return null
    }

    companion object {
        private val locationProviders = listOf(
            LocationManager.GPS_PROVIDER,
            LocationManager.NETWORK_PROVIDER,
            LocationManager.PASSIVE_PROVIDER
        )
        private const val MIN_REQUEST_TIME_MS = 5_000L
        private const val MIN_DISTANCE_UPDATE_M = 50F
    }
}