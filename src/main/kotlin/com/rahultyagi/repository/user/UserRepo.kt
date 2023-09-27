package com.rahultyagi.repository.user

import com.rahultyagi.users.AuthResponse
import com.rahultyagi.users.SignInParams
import com.rahultyagi.users.SignUpParams
import com.rahultyagi.util.Response

interface UserRepo {

    suspend fun signUp(params: SignUpParams): Response<AuthResponse>
    suspend fun signIn(params: SignInParams): Response<AuthResponse>
}