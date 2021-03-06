package com.car.apicar.interfaces.domain

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Driver(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val name: String,
    val birthDate: LocalDate
)

data class PatchDriver(
    val name: String?,
    val birthDate: LocalDate?
)

@Entity
data class Passenger(
    @Id
    @GeneratedValue
    var id: Long? = null,
    val name: String
)

data class PatchPassenger(
    val name: String?
)

@Entity
data class TravelRequest(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @ManyToOne
    val passenger: Passenger,
    val origin: String,
    val destination: String,
    val status: TravelRequestStatus = TravelRequestStatus.CREATED,
    val creationDate: LocalDateTime = LocalDateTime.now()
)

enum class TravelRequestStatus {
    CREATED, ACCEPTED, REFUSED
}