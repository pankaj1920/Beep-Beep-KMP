package org.thechance.service_notification.domain.gateway

import org.thechance.service_notification.domain.model.User

interface UserGateway {
    suspend fun createUser(user: User): Boolean
    suspend fun getUsers(): List<User>
    suspend fun getUser(id:String): User
}