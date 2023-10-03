package com.rahultyagi.repository.user

import com.rahultyagi.users.*
import com.rahultyagi.util.Response

interface UserRepo {

    suspend fun signUp(params: SignUpParams): Response<AuthResponse>
    suspend fun signIn(params: SignInParams): Response<AuthResponse>
    suspend fun forgotPassword(params:ForgotPasswordRequest):Response<ForgotPasswordResponse>

    suspend fun insertImage(params: ProfileImageRequest):Response<ProfileImage>
    suspend fun getProfileImage(userId:Int):Response<ProfileImage>
}