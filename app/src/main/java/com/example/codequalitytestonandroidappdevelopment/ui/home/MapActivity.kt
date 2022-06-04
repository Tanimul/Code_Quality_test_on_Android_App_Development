package com.example.codequalitytestonandroidappdevelopment.ui.home

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.codequalitytestonandroidappdevelopment.R
import com.example.codequalitytestonandroidappdevelopment.databinding.ActivityMapBinding
import com.example.codequalitytestonandroidappdevelopment.utils.extentions.toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

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


        binding.searchplace.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val location: String = binding.searchplace.query.toString()
                var addressList: List<Address>? = null
                if (location != null || location != "") {
                    val geocoder = Geocoder(this@MapActivity)
                    try {
                        addressList = geocoder.getFromLocationName(location, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val address = addressList!![0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(latLng).title(location))
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            latLng,
                            15f
                        )
                    )
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment

        mapFragment?.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        val whereFrom = LatLng(23.6960147, 90.5004324)
        mMap.addMarker(
            MarkerOptions().position(whereFrom).title("whereFrom")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(whereFrom, 7f))
        val distance: Float = getDistance(23.7530815, 90.3776818, 23.6960147, 90.5004324)
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