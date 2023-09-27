package com.rahultyagi.model

import org.jetbrains.exposed.sql.Table

object Registration : Table(name = "registration") {


    val id = integer(name = "user_id").autoIncrement()
    val name = varchar(name = "name", length = 255)
    val surname = varchar(name = "surname", length = 255)
    val mobile = varchar(name = "mobile", length = 255)
    val email = varchar(name = "email", length = 255)
    val type = varchar(name = "type", length = 255)
    val address = varchar(name = "address", length = 255)
    val pinCode = integer(name = "pinCode")
    val password = varchar(name = "password", length = 255)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(id)
}

data class User(
    val id: Int,
    val name: String = "",
    val surname: String = "",
    val mobile: String = "",
    val email: String = "",
    val type: String = "",
    val address: String = "",
    val pinCode: Int = 0,
    val password: String,
)