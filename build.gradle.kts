
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val postgres_version: String by project
val h2_version: String by project
val exposed_version: String by project

val hikaricp_version:String by project
val koin_version:String by project

plugins {
    kotlin("jvm") version "1.8.20"
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
}

group = "com.rahultyagi"
version = "0.0.1"

application {
    mainClass.set("com.rahultyagi.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("com.zaxxer:HikariCP:$hikaricp_version")

    // hikaricp_version = 5.0.1
    // ehcache_version = 3.10.8
    // implementation("com.zaxxer:HikariCP:5.0.1")
    //implementation("org.ehcache:ehcache:3.10.8")
    implementation("io.insert-koin:koin-ktor:$koin_version")

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")

    implementation("io.ktor:ktor-server-call-logging:$ktor_version")



    //implementation ("io.insert-koin:koin-core:3.1.2")
}
