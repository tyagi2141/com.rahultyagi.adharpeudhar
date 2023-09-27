package com.rahultyagi.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.rahultyagi.users.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*


val jwtAudience = "jwt-audience"
val jwtDomain = "https://jwt-provider-domain/"
val jwtRealm = "ktor sample app"
val jwtSecret = "secret"

private const val CLAIM = "email"

fun Application.configureSecurity() {
    // Please read the jwt property from the config file if you are using EngineMain


    authentication {
        jwt {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
                // if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
                if (credential.payload.getClaim(CLAIM).asString() != null) {
                    JWTPrincipal(payload = credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(errorMessage = "invalid token code or expire")
                )
            }
        }
    }
}

fun generateToken(email: String): String {
    return JWT.create().withAudience(jwtAudience).withIssuer(jwtDomain).withClaim(CLAIM, email).sign(
        Algorithm.HMAC256(
            jwtSecret
        )
    )

}

