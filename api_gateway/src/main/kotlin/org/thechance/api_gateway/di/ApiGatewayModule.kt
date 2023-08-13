package org.thechance.api_gateway.di

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.thechance.api_gateway.data.model.APIS

val ApiGatewayModule = module {

    single { Attributes(true) }

    single {
        HttpClient(OkHttp) {

            val clientAttributes = get<Attributes>()

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            defaultRequest {
                when (clientAttributes[AttributeKey<String>("API")]) {
                    APIS.IDENTITY_API.value -> {
                        url("http://127.0.0.1:8080")

                    }

                    APIS.RESTAURANT_API.value -> {
                        url("http://127.0.0.1:8080")

                    }

                    APIS.TAXI_API.value -> {
                        url("http://127.0.0.1:8080")

                    }

                    APIS.NOTIFICATION_API.value -> {
                        url("http://127.0.0.1:8080")

                    }

                }
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }


}