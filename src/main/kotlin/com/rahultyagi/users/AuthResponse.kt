package com.rahultyagi.users

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val data: AuthResponseData? = null, val errorMessage: String? = null
)
