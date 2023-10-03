package com.rahultyagi.users


@kotlinx.serialization.Serializable
data class ProfileImageRequest(
    val id: Int,
    val url: String
)
