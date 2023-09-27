package com.rahultyagi.users

@kotlinx.serialization.Serializable
data class SignInParams(
    val email: String,
    val password: String
)