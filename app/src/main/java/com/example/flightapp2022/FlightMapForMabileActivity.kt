package com.example.flightapp2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

class FlightMapForMabileActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_map_for_mabile)


        var time= intent.getStringExtra("time")
        var icao24= intent.getStringExtra("icao24")

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, FlightMapFragment.newInstance(time.toString(), icao24.toString()))
        transaction.commit()

    }
}