package com.homeapps.diary.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat

class DiaryLocationManager(private val appContext: Context) {
    private var locationManager: LocationManager = appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var locationListener: LocationListener? = null
    private var locationHandler: Handler? = null
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    enum class LocationErrors {
        ERROR_NO_AVAILABLE_PROVIDERS, ERROR_REQUESTING_LOCATION, ERROR_LOCATION_TIMEOUT
    }

    fun ifGpsOn(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun hasLocationPermissions(): Boolean {
        return locationPermissions.all { ContextCompat.checkSelfPermission(appContext, it) == PackageManager.PERMISSION_GRANTED }
    }

    fun requestSingleLocationUpdate(
        onLocationReceived: (Location) -> Unit,
        onError: (LocationErrors) -> Unit
    ) {
        val provider = getAvailableLocationProvider()
        if (provider == null) {
            onError(LocationErrors.ERROR_NO_AVAILABLE_PROVIDERS)
        } else {
            locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    cleanUp()
                    onLocationReceived(location)
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { /* do nothing */ }

                override fun onProviderEnabled(provider: String) { /* do nothing */ }

                override fun onProviderDisabled(provider: String) { /* do nothing */ }
            }

            try {
                locationHandler = Handler(Looper.getMainLooper())
                locationHandler!!.postDelayed({
                    cleanUp()
                    onError(LocationErrors.ERROR_LOCATION_TIMEOUT)
                }, LOCATION_TIMEOUT)

                locationManager.requestLocationUpdates(
                    provider,
                    MIN_REQUEST_TIME_MS,
                    MIN_DISTANCE_UPDATE_M,
                    locationListener!!,
                    locationHandler!!.looper
                )
            } catch (_: SecurityException) {
                cleanUp()
                onError(LocationErrors.ERROR_REQUESTING_LOCATION)
            } catch (_: Exception) {
                cleanUp()
                onError(LocationErrors.ERROR_REQUESTING_LOCATION)
            }
        }
    }

    private fun cleanUp() {
        locationListener?.let { locationManager.removeUpdates(it) }
        locationHandler?.removeCallbacksAndMessages(null)
        locationListener = null
        locationHandler = null
    }

    private fun getAvailableLocationProvider(): String? {
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
        private const val LOCATION_TIMEOUT = 10_000L
    }
}