package org.thechance.api_gateway.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import org.thechance.api_gateway.data.model.TokenType
import io.ktor.server.response.*

fun Application.configureJWTAuthentication() {

    val jwtSecret = ApplicationConfig("jwt.secret").toString()
    val jwtDomain = ApplicationConfig("jwt.issuer").toString()
    val jwtAudience = ApplicationConfig("jwt.audience").toString()
    val jwtRealm = ApplicationConfig("jwt.realm").toString()

    authentication {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .withClaim("tokenType", TokenType.ACCESS_TOKEN.name)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload)
                } else
                    null
            }
            respondUnauthorized()
        }

        jwt("refresh-jwt") {
            realm = jwtRealm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .withClaim("tokenType", TokenType.REFRESH_TOKEN.name)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload)
                } else
                    null
            }

            respondUnauthorized()
        }
    }
}

private fun JWTAuthenticationProvider.Config.respondUnauthorized() {
    challenge { _, _ ->
        call.respond(UnauthorizedResponse())
    }
}