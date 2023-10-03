package com.rahultyagi.users


@kotlinx.serialization.Serializable
data class ProfileImage(
    val id: Int,
    val url: String,
    val status: Boolean = true
)
