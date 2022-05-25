package com.example.myapplication19

import android.Manifest
    import android.Manifest.permission.ACCESS_FINE_LOCATION
    import android.content.pm.PackageManager
    import android.graphics.Camera
    import android.location.Location
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Log
    import android.view.Menu
    import android.view.MenuItem
    import android.widget.Toast
    import androidx.activity.result.contract.ActivityResultContracts
    import androidx.core.content.ContextCompat
    import com.google.android.gms.common.ConnectionResult
    import com.google.android.gms.common.api.GoogleApi
    import com.google.android.gms.common.api.GoogleApiClient
    import com.google.android.gms.location.FusedLocationProviderClient
    import com.google.android.gms.location.LocationServices
    import com.google.android.gms.maps.CameraUpdateFactory
    import com.google.android.gms.maps.GoogleMap
    import com.google.android.gms.maps.OnMapReadyCallback
    import com.google.android.gms.maps.SupportMapFragment
    import com.google.android.gms.maps.model.BitmapDescriptorFactory
    import com.google.android.gms.maps.model.CameraPosition
    import com.google.android.gms.maps.model.LatLng
    import com.google.android.gms.maps.model.MarkerOptions
    import com.google.android.gms.tasks.OnSuccessListener

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener{
    var googleMap : GoogleMap? = null
    lateinit var apiClient : GoogleApiClient
    lateinit var providerClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)!!.getMapAsync(this)

        providerClient = LocationServices.getFusedLocationProviderClient(this)
        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {
            if(it.all { permission -> permission.value == true}){
                apiClient.connect()
            } else {
                Toast.makeText(this, "권한 거부..", Toast.LENGTH_SHORT).show()
            }
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) !== PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE
                )
            )
        }
        else{
            apiClient.connect()
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0
/*
            //googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            val latLng = LatLng(37.568256, 126.897240)
            val position: CameraPosition = CameraPosition.Builder()
                .target(latLng)
                .zoom(16F)
                .build()
            googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))

            val markerOp = MarkerOptions()
            markerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            markerOp.position(latLng)
            markerOp.title("월드컵경기장")
            googleMap?.addMarker(markerOp)

 */
    }
    private fun moveMap(latitude: Double, longitude: Double) {
        val latLng = LatLng(latitude, longitude)
        val position: CameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(16F)
            .build()
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        val markerOp = MarkerOptions()
        markerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOp.position(latLng)
        markerOp.title("My Location")
        googleMap?.addMarker(markerOp)
    }
    override fun onConnected(p0: Bundle?) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
            providerClient.lastLocation.addOnSuccessListener(
                this@MainActivity,
                object:OnSuccessListener<Location>{

                    override fun onSuccess(p0: Location?) {
                        p0?.let{
                            val latitude = p0.latitude
                            val longitude = p0.longitude
                            Log.d("mobileApp", "lat: $latitude, lng: $longitude")
                            moveMap(latitude, longitude)
                        }
                    }
                }
            )
            apiClient.disconnect()
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onConnectionSuspended(p0: Int) {

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add(0, 1, 0, "위성지도")
        menu?.add(0, 2, 0, "일반지도")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            1 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            2 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
        }
        return false
    }
    }