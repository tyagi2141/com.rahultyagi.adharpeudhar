package com.rahultyagi.model

import org.jetbrains.exposed.sql.Table


object ImageTable : Table(name = "image_table") {

    val imageId = integer(name = "imageId").autoIncrement()
    val userId = integer(name = "userId")
    val imageUrl = varchar(name = "imageUrl", length = 255)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(imageId)

}

