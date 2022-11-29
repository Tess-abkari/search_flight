package com.example.flightapp2022

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FlightMapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlightMapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit  var mapFragment : SupportMapFragment;
    private lateinit var viewModel : FlightListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity() )[FlightListViewModel::class.java]
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_flight_map, container, false)
        mapFragment = childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment
        val args = arguments
        if (args != null){
            UpdateMapForMobile(args.getString(ARG_PARAM1).toString(), args.getString(ARG_PARAM2).toString())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FlightMapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FlightMapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }

            }
    }

    fun UpdateMap(flightModel: FlightModel){

        mapFragment.getMapAsync{ googleMap->
            viewModel.getMarkers(flightModel.lastSeen.toString(), flightModel.icao24);

            googleMap.setOnMapLoadedCallback(GoogleMap.OnMapLoadedCallback {
                var builder = LatLngBounds.builder()
                viewModel.getMarkersListLiveData().observe(viewLifecycleOwner) {
                    googleMap.clear()
                    for (marker in it.path) {
                        googleMap.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    marker[1] as Double,
                                    marker[2] as Double,
                                )
                            )
                        )
                        builder.include(LatLng(marker[1] as Double, marker[2] as Double))
                    }
                    var bounds = builder.build()
                    var cam = CameraUpdateFactory.newLatLngBounds(bounds, 15)
                    googleMap.animateCamera(cam)
                }
            })
        }
    }

    fun UpdateMapForMobile(time: String, icao24:String ){

        mapFragment.getMapAsync{ googleMap->
            viewModel.getMarkers(time, icao24);

            googleMap.setOnMapLoadedCallback(GoogleMap.OnMapLoadedCallback {
                var builder = LatLngBounds.builder()
                viewModel.getMarkersListLiveData().observe(viewLifecycleOwner) {
                    googleMap.clear()
                    for (marker in it.path) {
                        googleMap.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    marker[1] as Double,
                                    marker[2] as Double,
                                )
                            )
                        )
                        builder.include(LatLng(marker[1] as Double, marker[2] as Double))
                    }
                    var bounds = builder.build()
                    var cam = CameraUpdateFactory.newLatLngBounds(bounds, 15)
                    googleMap.animateCamera(cam)
                }
            })
        }
    }
}