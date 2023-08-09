package org.thechance.service_taxi.api.endpoints

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.thechance.service_taxi.api.models.trip.TripDto
import org.thechance.service_taxi.api.models.trip.toDto
import org.thechance.service_taxi.api.models.trip.toEntity
import org.thechance.service_taxi.domain.usecase.AdministratorUseCase
import org.thechance.service_taxi.domain.usecase.CustomerUseCase
import org.thechance.service_taxi.domain.usecase.DriverUseCase

fun Route.tripRoutes() {
    val driverUseCase: DriverUseCase by inject()
    val administratorUseCase: AdministratorUseCase by inject()
    val customerUseCase: CustomerUseCase by inject()


    route("/trip") {
        get {
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = administratorUseCase.getTrips(page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/{tripId}") {
            val id = call.parameters["tripId"]?.trim().orEmpty()
            val result = customerUseCase.getTripById(id)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/driver/{driverId}") {
            val id = call.parameters["driverId"]?.trim().orEmpty()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = driverUseCase.getTripsByDriverId(id, page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/client/{clientId}") {
            val id = call.parameters["clientId"]?.trim().orEmpty()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = customerUseCase.getTripsByClientId(id, page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        post {
            val tripDto = call.receive<TripDto>()
            val result = customerUseCase.createTrip(tripDto.toEntity())
            if (result) {
                call.respond(HttpStatusCode.Created, "added")
            } else {
                call.respond(HttpStatusCode.BadRequest, "taxi not added")
            }
        }

        put("/{tripId}/rate") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            val rate = call.parameters["rate"]?.toDouble() ?: throw Throwable()
            customerUseCase.rateTrip(tripId, rate)
            call.respond(HttpStatusCode.OK, "updated")
        }

        put("/{tripId}/approve") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            val driverId = call.parameters["driverId"] ?: throw Throwable()
            val taxiId = call.parameters["taxiId"] ?: throw Throwable()
            driverUseCase.approveTrip(driverId, taxiId, tripId)
            call.respond(HttpStatusCode.OK, "updated")
        }

        put("/{tripId}/finish") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            val driverId = call.parameters["driverId"] ?: throw Throwable()
            driverUseCase.finishTrip(driverId, tripId)
            call.respond(HttpStatusCode.OK, "updated")
        }

        delete("/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            val result = administratorUseCase.deleteTrip(tripId)
            if (result) {
                call.respond(HttpStatusCode.OK, "deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "taxi not deleted")
            }
        }
    }
}