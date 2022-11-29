package com.example.flightapp2022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FlightListActivity : AppCompatActivity() {

    private var flightMapFragment: FlightMapFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)


        flightMapFragment = supportFragmentManager.findFragmentById(R.id.map_container) as FlightMapFragment?

        val begin = intent.getLongExtra("BEGIN", 0)
        val end = intent.getLongExtra("END", 0)
        val isArrival = intent.getBooleanExtra("IS_ARRIVAL", false)
        val icao = intent.getStringExtra("ICAO")

        val viewModel = ViewModelProvider(this).get(FlightListViewModel::class.java)

        Log.i("MAIN ACTIVITY", "begin = $begin \n end = $end \n icao = $icao \n is arrival = $isArrival")

        // DO NOT DO REQUEST IN ACTIVITY LIKE THE COMMENT BELOW
        //RequestManager.get("https://google.fr", HashMap())

        viewModel.doRequest(begin, end, isArrival, icao!!)

        viewModel.getClickedFlightLiveData().observe(this, Observer {
            // Afficher le bon vol
            val isTablet = findViewById<FragmentContainerView>(R.id.map_container) != null
            if (isTablet  ) {

                flightMapFragment?.UpdateMap(it);

            }else{

                var intent = Intent(this, FlightMapForMabileActivity::class.java)
                intent.putExtra("time",it.lastSeen.toString())
                intent.putExtra("icao24",it.icao24)
                startActivity(intent )
            }


        })

    }


}