package com.rahultyagi.users

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseData(
    val id: Int,
    val name: String = "",
    val surname: String = "",
    val mobile: String = "",
    val email: String = "",
    val type: String = "",
    val address: String = "",
    val pinCode: Int = 0,
    val token: String,
)
