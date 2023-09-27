package com.rahultyagi.db

import com.rahultyagi.dao.DatabaseFactory.dbQuery
import com.rahultyagi.model.Registration
import com.rahultyagi.model.User
import com.rahultyagi.repository.user.UserRepo
import com.rahultyagi.security.hashPassword
import com.rahultyagi.users.SignUpParams
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

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
}