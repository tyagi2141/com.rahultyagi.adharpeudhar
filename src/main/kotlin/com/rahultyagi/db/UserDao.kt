package com.rahultyagi.db

import com.rahultyagi.model.User
import com.rahultyagi.users.SignUpParams

interface UserDao {

    suspend fun insert(params: SignUpParams): User?

    suspend fun findByEmail(params: String): User?


}