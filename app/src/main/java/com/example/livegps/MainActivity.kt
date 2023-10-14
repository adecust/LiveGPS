package com.example.livegps
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    private lateinit var locationManager: LocationManager
    private lateinit var locationText: TextView

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val latitude = location.latitude
            val longitude = location.longitude
            // Konumu ekrandaki TextView'a yazdırabilirsiniz
            locationText.text = "Konum: Enlem $latitude, \n Boylam $longitude"
            val car1 :LocationModel= LocationModel(31,latitude,longitude)
            postData(car1)
        }
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
        
    }

    //MARK- LİFECYCLE
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationText = findViewById(R.id.locationText)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        fetchAll()
    // UPDATE LATİTUDE AND LONGİTUDE
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000, 0f, locationListener
        )
    }
    fun fetchAll(){
        apiService.getLastLocations().enqueue(object : Callback<List<LocationModel>>{
            override fun onResponse(
                call: Call<List<LocationModel>>,
                response: Response<List<LocationModel>>) {
                if (response.isSuccessful){
                    val data=response.body()
                    if(data !=null){
                        Log.e("aaaa",data[0].toString())
                    }

                }

            }

            override fun onFailure(call: Call<List<LocationModel>>, t: Throwable) {

            }

        })
    }
    fun postData(temp :LocationModel){
        apiService.setLastLocation(temp).enqueue(object :Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.e("aaa","Lokasyon atıldı")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("aaa",",Error atıldı")
            }

        })

    }

}
