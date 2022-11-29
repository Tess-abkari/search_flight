package com.example.flightapp2022


data class  MarkersModel (val icao24: String,
                                val callsign: String,
                                val startTime: Long,
                                val endTime: Long,
                                val path: ArrayList<List<Any>>,
)