package com.rahultyagi

import com.rahultyagi.dao.DatabaseFactory
import com.rahultyagi.di.configureDi
import com.rahultyagi.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8081, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    DatabaseFactory.init()
    configureSecurity()
    configureDi()
    configureRouting()
}
