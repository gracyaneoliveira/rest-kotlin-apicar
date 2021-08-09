package com.car.apicar.domain

import com.car.apicar.interfaces.outcoming.GMapsService
import org.springframework.stereotype.Component

@Component
class TravelService (
    val travelRequestRepository: TravelRequestRepository,
    val gMapsService: GMapsService
)
{
    val MAX_TRAVEL_TIME: Int = 600

    fun saveTravelRequest(travelRequest: TravelRequest) =
            travelRequestRepository.save(travelRequest)

    fun listNearbyTravelRequests(currentAddress: String): List<TravelRequest> {
        val requests = travelRequestRepository.findByStatus(TravelRequestStatus.CREATED)
        return requests.filter { tr ->
            gMapsService.getDistanceBetweenAddress(currentAddress, tr.origin) < MAX_TRAVEL_TIME
        }
    }
}