package com.rahultyagi.db

import com.rahultyagi.dao.DatabaseFactory.dbQuery
import com.rahultyagi.model.ImageTable
import com.rahultyagi.model.ImageTable.imageId
import com.rahultyagi.model.ImageTable.imageUrl
import com.rahultyagi.model.ImageTable.userId
import com.rahultyagi.model.Registration
import com.rahultyagi.model.User
import com.rahultyagi.security.hashPassword
import com.rahultyagi.users.ForgotPasswordRequest
import com.rahultyagi.users.ProfileImage
import com.rahultyagi.users.ProfileImageRequest
import com.rahultyagi.users.SignUpParams
import org.jetbrains.exposed.sql.*

class UserDaoImpl : UserDao {
    override suspend fun insert(params: SignUpParams): User? {
        return dbQuery {
            val insertUser = Registration.insert {
                it[name] = params.name
                it[surname] = params.surname
                it[mobile] = params.mobile
                it[email] = params.email
                it[type] = params.type
                it[address] = params.address
                it[pinCode] = params.pinCode
                //it[password] = params.password
                it[password] = hashPassword(params.password)
            }
            insertUser.resultedValues?.singleOrNull()?.let {
                rowToUser(it)
            }
        }
    }

    override suspend fun findByEmail(params: String): User? {
        return dbQuery {
            val getUser = Registration.select { Registration.email eq params }.map { rowToUser(it) }.singleOrNull()
            getUser
        }
    }

    override suspend fun updatePassword(params: ForgotPasswordRequest): Boolean {
        return dbQuery {
            Registration.update({ Registration.email eq params.email }) {
                it[password] = hashPassword(params.password)
            } > 0
        }
    }

    override suspend fun insertImage(params: ProfileImageRequest): ProfileImage? {
        return dbQuery {
            val saveImage = ImageTable.insert {
                it[userId] = params.id
                it[imageUrl] = params.url
            }
            saveImage.resultedValues?.singleOrNull()?.let {
                ProfileImage(it[imageId], it[imageUrl])
            }
        }

    }

    override suspend fun getProfileImage(userid: String): ProfileImage? {
        return dbQuery {
            val profileImage = ImageTable.select { ImageTable.userId eq userid.toInt() }
                .map { rowToProfileImage(it) }.singleOrNull()
            profileImage
        }
    }

    private fun rowToUser(row: ResultRow): User? {
        return User(
            id = row[Registration.id],
            name = row[Registration.name],
            surname = row[Registration.surname],
            mobile = row[Registration.mobile],
            email = row[Registration.email],
            type = row[Registration.type],
            address = row[Registration.address],
            pinCode = row[Registration.pinCode],
            password = row[Registration.password]
        )
    }

    private fun rowToProfileImage(row: ResultRow): ProfileImage? {
        return ProfileImage(id = row[userId], url = row[imageUrl])
    }
}