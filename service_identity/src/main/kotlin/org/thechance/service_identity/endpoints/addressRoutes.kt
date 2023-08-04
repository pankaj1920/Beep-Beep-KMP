package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.api.model.AddressDto
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.data.mappers.toEntity
import org.thechance.service_identity.domain.usecases.useraccount.UserAccountUseCase

fun Route.addressRoutes() {

    val userAccountUseCase: UserAccountUseCase by inject()

    route("/address") {

        post {
            val address = call.receive<AddressDto>()
            call.respond(HttpStatusCode.Created, userAccountUseCase.addAddress(address.toEntity()))
        }

        get("/{id}") {
            val id = call.parameters["id"].orEmpty()
            call.respond(HttpStatusCode.OK, userAccountUseCase.getAddress(id).toDto())
        }

        delete("/{id}") {
            val id = call.parameters["id"].orEmpty()
            call.respond(HttpStatusCode.OK, userAccountUseCase.deleteAddress(id))
        }

        put("/{id}") {
            val id = call.parameters["id"].orEmpty()
            val address = call.receive<AddressDto>()
            call.respond(HttpStatusCode.Created, userAccountUseCase.updateAddress(id, address.toEntity()))
        }

    }

    get("/addresses/{userId}") {
        val id = call.parameters["userId"].orEmpty()
        val userAddresses = userAccountUseCase.getUserAddresses(id)
        call.respond(HttpStatusCode.OK, userAddresses.map { it.toDto() })
    }

}