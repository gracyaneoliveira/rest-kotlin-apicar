package com.car.apicar.interfaces

import com.car.apicar.domain.TravelRequestStatus
import com.car.apicar.domain.TravelService
import com.car.apicar.interfaces.mapping.TravelRequestMapper
import org.springframework.hateoas.EntityModel
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Service
@RestController
@RequestMapping(path = ["/travelRequests"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TravelRequestAPI(
        val travelService: TravelService,
        val mapper: TravelRequestMapper
) {

    @PostMapping
    fun makeTravelRequest(@RequestBody travelRequestInput: TravelRequestInput)
            : EntityModel<TravelRequestOutput> {
        val travelRequest = travelService.saveTravelRequest(mapper.map(travelRequestInput))
        val output = mapper.map(travelRequest)
        return mapper.buildOutputModel(travelRequest, output)
    }
}

data class TravelRequestInput(
        val passengerId: Long,
        val origin: String,
        val destination: String
)

data class TravelRequestOutput(
        val id: Long,
        val origin: String,
        val destination: String,
        val status: TravelRequestStatus,
        val creationDate: LocalDateTime
)