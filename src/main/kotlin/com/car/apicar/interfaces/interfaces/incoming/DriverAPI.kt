package com.car.apicar.interfaces.interfaces.incoming

import com.car.apicar.interfaces.domain.Driver
import com.car.apicar.interfaces.domain.DriverRepository
import com.car.apicar.interfaces.domain.PatchDriver
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Service
@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class DriverAPI(
    private val driverRepository: DriverRepository
) {

    @GetMapping("/drivers")
    fun listDrivers(): MutableList<Driver> {
        return driverRepository.findAll()
    }

    @GetMapping("/drivers/{id}")
    fun findDriver(@PathVariable("id") id: Long): Driver {
        return driverRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
    }

    @PostMapping("/drivers")
    fun createDriver(@RequestBody driver: Driver): Driver {
        return driverRepository.save(driver)
    }

    @PutMapping("/drivers/{id}")
    fun fullUpdateDriver(@PathVariable("id") id: Long,
                         @RequestBody driver: Driver): Driver {
        val foundDriver = findDriver(id)
        val copyDriver = foundDriver.copy(
            birthDate = driver.birthDate,
            name = driver.name
        )
        return driverRepository.save(copyDriver)
    }

    @PatchMapping("/drivers/{id}")
    fun incrementalUpdateDriver(@PathVariable("id") id: Long,
                                @RequestBody driver: PatchDriver): Driver {
        val foundDriver = findDriver(id)
        val copyDriver = foundDriver.copy(
            birthDate = driver.birthDate ?: foundDriver.birthDate,
            name = driver.name ?: foundDriver.name
        )
        return driverRepository.save(copyDriver)
    }

    @DeleteMapping("/drivers/{id}")
    fun deleteDriver(@PathVariable("id") id: Long) {
        return  driverRepository.delete(findDriver(id))
    }
}
