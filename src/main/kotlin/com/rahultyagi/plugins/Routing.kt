package com.rahultyagi.plugins

import com.rahultyagi.route.authRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        authRouting()
    }
}
