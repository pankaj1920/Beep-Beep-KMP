package org.thechance.service_identity.domain.usecases.user

import org.thechance.service_identity.domain.entity.User

interface CreateUserUseCase {
    suspend operator fun invoke(user: User): Boolean
}

