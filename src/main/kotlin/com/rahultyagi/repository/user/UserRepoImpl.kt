package com.rahultyagi.repository.user

import com.rahultyagi.db.UserDao
import com.rahultyagi.plugins.generateToken
import com.rahultyagi.security.hashPassword
import com.rahultyagi.users.AuthResponse
import com.rahultyagi.users.AuthResponseData
import com.rahultyagi.users.SignInParams
import com.rahultyagi.users.SignUpParams
import com.rahultyagi.util.Response
import io.ktor.http.*

class UserRepoImpl(val userDao: UserDao) : UserRepo {
    override suspend fun signUp(params: SignUpParams): Response<AuthResponse> {

        return if (userAlreadyExist(params.email)) {

            Response.Error(
                code = HttpStatusCode.Conflict, data = AuthResponse(null, errorMessage = "User Already Exists")
            )
        } else {

            val insertUser = userDao.insert(params = params)
            if (insertUser == null) {
                Response.Error(
                    code = HttpStatusCode.InternalServerError,
                    data = AuthResponse(null, errorMessage = "unable to save the data")
                )
            } else {

                Response.Success(
                    data = AuthResponse(
                        data = AuthResponseData(
                            id = insertUser.id,
                            name = insertUser.name,
                            surname = insertUser.surname,
                            mobile = insertUser.mobile,
                            email = insertUser.email,
                            type = insertUser.type,
                            address = insertUser.address,
                            pinCode = insertUser.pinCode,
                            token = generateToken(params.email)
                        )
                    )
                )
            }
        }

    }

    override suspend fun signIn(params: SignInParams): Response<AuthResponse> {
        val getUser = userDao.findByEmail(params.email)


        return if (getUser == null) {
            Response.Error(
                code = HttpStatusCode.Conflict,
                data = AuthResponse(data = null, errorMessage = "User not exists")
            )
        } else {

            val hashPassword = hashPassword(params.password)


            if (getUser.password == hashPassword) {
                Response.Success(
                    data = AuthResponse(
                        data = AuthResponseData(
                            id = getUser.id,
                            name = getUser.name,
                            surname = getUser.surname,
                            mobile = getUser.mobile,
                            email = getUser.email,
                            type = getUser.type,
                            address = getUser.address,
                            pinCode = getUser.pinCode,
                            token = generateToken(params.email)
                        )
                    )
                )
            } else {
                Response.Error(
                    code = HttpStatusCode.InternalServerError,
                    data = AuthResponse(data = null, errorMessage = "Password not match")
                )
            }
        }
    }

    private suspend fun userAlreadyExist(email: String): Boolean {
        return userDao.findByEmail(params = email) != null
    }
}