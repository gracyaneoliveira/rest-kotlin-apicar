package com.car.apicar.interfaces.interfaces.incoming

import com.car.apicar.infrastructure.loadFileContents
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.WireMockServer
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import com.github.tomakehurst.wiremock.client.WireMock.*
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test
import org.hamcrest.Matchers.equalTo as equalToHamcrest
import org.hamcrest.Matchers.notNullValue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock (port = WireMockConfiguration.DYNAMIC_PORT)
@ActiveProfiles("test")
class TravelRequestAPITestIT {

    @LocalServerPort
    private var port:Int = 0

    @Autowired
    lateinit var server: WireMockServer

    @BeforeEach
    fun setup() {
        RestAssured.port = port
    }

    fun setupServer() {
        server.stubFor(get(urlPathEqualTo("/maps/api/directions/json"))
            .withQueryParam("origin", equalTo("Avenida Paulista, 1000"))
            .withQueryParam("destination", equalTo("Avenida Paulista, 1000"))
            .withQueryParam("key", equalTo("APIKEY"))
            .willReturn(okJson(loadFileContents("/responses/gmaps/sample_response.json")))
        )
    }

    @Test
    fun testFindNearbyTravelRequests() {
        setupServer()
        given()
            .contentType(ContentType.JSON)
            .body(loadFileContents("/requests/passengers_api/create_new_passenger.json"))
            .post("/passengers")
            .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("name", equalToHamcrest("Alexandre Saudate"))

        given()
            .contentType(ContentType.JSON)
            .body(loadFileContents("/requests/travel_requests_api/create_new_request.json"))
            .post("/travelRequests")
            .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("origin", equalToHamcrest("Avenida Paulista, 1000"))
            .body("destination", equalToHamcrest("Avenida Ipiranga, 100"))
            .body("status", equalToHamcrest("CREATED"))
            .body("_links.passenger.title", equalToHamcrest("Alexandre Saudate"))

        given()
            .get("/travelRequests/nearby?currentAddress=Avenida Paulista, 1000")
            .then()
            .statusCode(200)
            .body("[0].id", notNullValue())
            .body("[0].origin", equalToHamcrest("Avenida Paulista, 1000"))
            .body("[0].destination", equalToHamcrest("Avenida Ipiranga, 100"))
            .body("[0].status", equalToHamcrest("CREATED"))
    }
}