package com.rahultyagi.repository.user

import com.rahultyagi.db.UserDao
import com.rahultyagi.plugins.generateToken
import com.rahultyagi.security.hashPassword
import com.rahultyagi.users.*
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
                code = HttpStatusCode.Conflict, data = AuthResponse(data = null, errorMessage = "User not exists")
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

    override suspend fun forgotPassword(params: ForgotPasswordRequest): Response<ForgotPasswordResponse> {

        if (userAlreadyExist(params.email)) {
            return when (val update = userDao.updatePassword(params)) {
                true -> {
                    Response.Success(
                        data = ForgotPasswordResponse(
                            status = update, message = "password update successfully.."
                        )
                    )
                }
                else -> {
                    Response.Success(
                        data = ForgotPasswordResponse(
                            status = update, message = "password update successfully..2"
                        )
                    )

                }
            }
        } else {
            return Response.Error(
                data = ForgotPasswordResponse(
                    status = false, message = "Email not found"
                ), code = HttpStatusCode.NotFound
            )
        }


    }

    override suspend fun insertImage(params: ProfileImageRequest): Response<ProfileImage> {
        val saveImageResult = userDao.insertImage(params)
        return if (saveImageResult == null) {
            Response.Error(code = HttpStatusCode.NotFound, data = ProfileImage(status = false, id = 0, url = ""))
        } else {
            Response.Success(data = ProfileImage(status = true, id = saveImageResult.id, url = saveImageResult.url))
        }
    }

    override suspend fun getProfileImage(userId: Int): Response<ProfileImage> {

        val profileResponse = userDao.getProfileImage(userId = userId.toString())

        return if (profileResponse == null) {
            Response.Error(code = HttpStatusCode.NotFound, data = ProfileImage(id = -1, url = "", status = false))
        } else {
            Response.Success(data = profileResponse)
        }
    }


    private suspend fun userAlreadyExist(email: String): Boolean {
        return userDao.findByEmail(params = email) != null
    }


}