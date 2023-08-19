package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.security.HashingService
import org.thechance.service_identity.domain.entity.InsufficientFundsException
import org.thechance.service_identity.domain.entity.InvalidCredentialsException
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.usecases.validation.IUserInfoValidationUseCase
import org.thechance.service_identity.domain.usecases.validation.IWalletBalanceValidationUseCase
import org.thechance.service_identity.domain.util.INSUFFICIENT_FUNDS
import org.thechance.service_identity.domain.util.INVALID_CREDENTIALS

interface IUserAccountManagementUseCase {

    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String,
    ): Boolean

    suspend fun deleteUser(id: String): Boolean

    suspend fun updateUser(
        id: String, fullName: String? = null,
        username: String? = null,
        password: String? = null,
        email: String? = null
    ): Boolean

    suspend fun getUser(id: String): User

    suspend fun addToWallet(userId: String, amount: Double): Boolean

    suspend fun subtractFromWallet(userId: String, amount: Double): Boolean

    suspend fun login(username: String, password: String): Boolean

    suspend fun updateRefreshToken(userId: String, refreshToken: String, expirationDate: Long): Boolean

    suspend fun getUserByUsername(username: String): UserManagement

    suspend fun validateRefreshToken(refreshToken: String): Boolean

    suspend fun getUserByRefreshToken(refreshToken: String): UserManagement
}

@Single
class UserAccountManagementUseCase(
    private val dataBaseGateway: IDataBaseGateway,
    private val walletBalanceValidationUseCase: IWalletBalanceValidationUseCase,
    private val userInfoValidationUseCase: IUserInfoValidationUseCase,
    private val hashingService: HashingService
) : IUserAccountManagementUseCase {

    override suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String,
    ): Boolean {
        userInfoValidationUseCase.validateUserInformation(fullName, username, password, email)
        val saltedHash = hashingService.generateSaltedHash(password)
        return dataBaseGateway.createUser(saltedHash, fullName, username, email)
    }

    override suspend fun getUserByUsername(username: String): UserManagement {
        return dataBaseGateway.getUserByUsername(username)
    }

    override suspend fun login(username: String, password: String): Boolean {
        val saltedHash = dataBaseGateway.getSaltedHash(username)
        return if(hashingService.verify(password, saltedHash)) true
            else throw InvalidCredentialsException(INVALID_CREDENTIALS)
    }

    override suspend fun updateRefreshToken(
        userId: String,
        refreshToken: String,
        expirationDate: Long
    ): Boolean {
        return dataBaseGateway.updateRefreshToken(userId, refreshToken, expirationDate)
    }

    override suspend fun deleteUser(id: String): Boolean {
        return dataBaseGateway.deleteUser(id)
    }

    override suspend fun updateUser(
        id: String, fullName: String?,
        username: String?,
        password: String?,
        email: String?
    ): Boolean {
        userInfoValidationUseCase.validateUpdateUserInformation(fullName, username, password, email)
        val saltedHash = password?.let {
            hashingService.generateSaltedHash(it)
        }
        return dataBaseGateway.updateUser(id, saltedHash, fullName, username, email)
    }

    override suspend fun getUser(id: String): User {
        return dataBaseGateway.getUserById(id)
    }

    override suspend fun addToWallet(userId: String, amount: Double): Boolean {
        walletBalanceValidationUseCase.validateWalletBalance(amount)
        return dataBaseGateway.addToWallet(userId, amount)
    }

    override suspend fun subtractFromWallet(userId: String, amount: Double): Boolean {
        walletBalanceValidationUseCase.validateWalletBalance(amount)
        if (amount > dataBaseGateway.getWalletBalance(userId)) {
            throw InsufficientFundsException(INSUFFICIENT_FUNDS)
        }
        return dataBaseGateway.subtractFromWallet(userId, amount)
    }

    override suspend fun validateRefreshToken(refreshToken: String): Boolean {
        return dataBaseGateway.validateRefreshToken(refreshToken)
    }

    override suspend fun getUserByRefreshToken(refreshToken: String): UserManagement {
        return dataBaseGateway.getUserByRefreshToken(refreshToken)
    }

}