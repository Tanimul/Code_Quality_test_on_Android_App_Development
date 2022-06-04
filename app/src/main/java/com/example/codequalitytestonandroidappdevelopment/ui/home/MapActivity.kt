package com.example.codequalitytestonandroidappdevelopment.ui.home

import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.codequalitytestonandroidappdevelopment.R
import com.example.codequalitytestonandroidappdevelopment.databinding.ActivityMapBinding
import com.example.codequalitytestonandroidappdevelopment.utils.extentions.toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private val TAG: String = "MapActivity"
    }

    private lateinit var binding: ActivityMapBinding
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.searchplace.queryHint = getString(R.string.hint)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment

        mapFragment?.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        val distance: Float = getDistance(23.7530815,90.3776818,23.6960147,90.5004324,)
        toast("Your Distance is: $distance")
    }

    private fun getDistance(
        startLat: Double,
        startLang: Double,
        endLat: Double,
        endLang: Double
    ): Float {
        val locStart = Location("")
        locStart.latitude = startLat
        locStart.longitude = startLang
        val locEnd = Location("")
        locEnd.latitude = endLat
        locEnd.longitude = endLang
        return locStart.distanceTo(locEnd)
    }
}