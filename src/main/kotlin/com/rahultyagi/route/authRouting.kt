package com.rahultyagi.route

import com.rahultyagi.repository.user.UserRepo
import com.rahultyagi.users.*
import com.rahultyagi.util.Constants
import com.rahultyagi.util.Constants.BASE_URL
import com.rahultyagi.util.Constants.save
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.File

fun Routing.authRouting() {

    val repository by inject<UserRepo>()


    route("/home") {
        authenticate {
            get {
                call.respondText("Hello Rahul!")
            }
        }

    }

    route(path = "/signup") {
        post {

            val params = call.receiveNullable<SignUpParams>()

            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Invalid credentials!"
                    )
                )

                return@post
            }

            val result = repository.signUp(params = params)
            call.respond(
                status = result.code,
                message = result.response
            )

        }
    }

    route("/signIn") {
        post {
            val params = call.receiveNullable<SignInParams>()

            if (params == null) {
                call.respond(status = HttpStatusCode.BadRequest, message = "invalid credential")
                return@post
            } else {
                val result = repository.signIn(params)
                call.respond(status = HttpStatusCode.OK, message = result.response)
            }
        }
    }

    route("/passwordUpdate") {
        post {
            val params = call.receiveNullable<ForgotPasswordRequest>()

            if (params == null) {
                call.respond(status = HttpStatusCode.BadRequest, message = "invalid gmail account")
                return@post
            } else {
                val result = repository.forgotPassword(params)
                call.respond(status = HttpStatusCode.OK, message = result.response)
            }
        }
    }



    route("/add_image") {
        post() {
            val multipart = call.receiveMultipart()
            var fileName: String? = null
            var text: String? = null
            var id: Int? = -1
            try {
                multipart.forEachPart { partData ->
                    when (partData) {
                        is PartData.FormItem -> {
                            //to read additional parameters that we sent with the image
                            if (partData.name == "text") {
                                text = partData.value
                            }

                            if (partData.name == "id") {
                                id = partData.value.toInt()
                            }
                        }
                        is PartData.FileItem -> {
                            fileName = partData.save(Constants.USER_IMAGES_PATH)
                        }
                        is PartData.BinaryItem -> Unit
                        else -> {
                            println("Errors.....")
                        }
                    }
                }

               // val imageUrl = "${BASE_URL}/uploaded_images/$fileName"
                val imageUrl = "/uploaded_images/$fileName"
                repository.insertImage(ProfileImageRequest(id = id ?: -1, url = imageUrl))
                call.respond(HttpStatusCode.OK, imageUrl)
            } catch (ex: Exception) {
                File("${Constants.USER_IMAGES_PATH}/$fileName").delete()
                call.respond(HttpStatusCode.InternalServerError, "Error")
            }
        }
    }



    route("/image") {


        //0.0.0.0/uploaded_images/ef48466b-5448-41b1-847b-63b047a0a7d6.jpeg
        val imageUrl = "${BASE_URL}/uploaded_images/fileName"
        ///folder_name/image.jpg
        get {

            val params = call.receiveNullable<String>()

            val userid = call.parameters["id"]

            val image = repository.getProfileImage(userid?.toInt() ?: -1)
            // call.respondFile(File("./folder_name/image.jpg"))
            call.respond(image.response)
        }
    }

    route("/greet") {
        get() {
            val name = call.parameters["name"]
///Users/rftyagi/Documents/AdharPeUdhar/uploaded_images/6d8a9e9c-2fdd-451a-ba68-297b106288ba.jpeg
            println("requesting......  "+call.parameters["name"])
            call.respondText("Hello, $name!")
        }
    }


}