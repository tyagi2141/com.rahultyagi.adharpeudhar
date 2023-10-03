package com.rahultyagi.util

import io.ktor.http.content.*
import java.io.File
import java.util.*

object Constants {

    const val BASE_URL = "0.0.0.0"

    const val USER_IMAGES_PATH = "/Users/rftyagi/Documents/AdharPeUdhar/uploaded_images/"


    fun PartData.FileItem.save(path: String): String {
        val fileBytes = streamProvider().readBytes()
        val fileExtension = originalFileName?.takeLastWhile { it != '.' }
        val fileName = UUID.randomUUID().toString() + "." + fileExtension
        val folder = File(path)
        folder.mkdir()
        println("Path = $path $fileName")
        File("$path$fileName").writeBytes(fileBytes)
        return fileName
    }
}