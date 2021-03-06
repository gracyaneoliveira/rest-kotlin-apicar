package com.car.apicar.interfaces.interfaces.outcoming

import com.jayway.jsonpath.JsonPath
import net.minidev.json.JSONArray
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GMapsService(
    @Value("\${app.car.domain.googlemaps.apikey}")
    val appKey: String,

    @Value("\${interfaces.outcoming.gmaps.host:https://maps.googleapis.com}")
    val gMapsHost: String
) {

    private val gMapsTamplete =
        "$gMapsHost/maps/api/directions/json?origin={origin}&destination={destination}&key={key}"

    fun getDistanceBetweenAddress(addressOne: String, addressTwo: String): Int {
        val template = RestTemplate()
        val jsonResult = template.getForObject(gMapsTamplete, String::class.java, addressOne, addressTwo, appKey)
        val rawResults: JSONArray = JsonPath.parse(jsonResult).read("\$..legs[*].duration.value")

        return rawResults.map { it as Int }.minOrNull() ?: Int.MAX_VALUE
    }
}