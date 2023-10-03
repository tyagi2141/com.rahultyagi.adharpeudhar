package com.rahultyagi.db

import com.rahultyagi.model.User
import com.rahultyagi.users.ForgotPasswordRequest
import com.rahultyagi.users.ProfileImage
import com.rahultyagi.users.ProfileImageRequest
import com.rahultyagi.users.SignUpParams

interface UserDao {

    suspend fun insert(params: SignUpParams): User?

    suspend fun findByEmail(params: String): User?
    suspend fun updatePassword(params: ForgotPasswordRequest): Boolean

    suspend fun insertImage(params: ProfileImageRequest):ProfileImage?

    suspend fun getProfileImage(userId:String):ProfileImage?


}