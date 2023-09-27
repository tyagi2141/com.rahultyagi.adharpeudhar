package com.rahultyagi.users

@kotlinx.serialization.Serializable
data class SignUpParams(
    val name: String,
    val surname: String,
    val mobile: String,
    val email: String,
    val type: String,
    val address: String,
    val pinCode: Int,
    val password: String,
)
