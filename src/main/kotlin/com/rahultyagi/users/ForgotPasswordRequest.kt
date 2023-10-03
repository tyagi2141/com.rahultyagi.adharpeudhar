package com.rahultyagi.users

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordRequest(val email: String, val mobile: String, val password: String)


@Serializable
data class ForgotPasswordResponse(val status: Boolean, val message: String)
