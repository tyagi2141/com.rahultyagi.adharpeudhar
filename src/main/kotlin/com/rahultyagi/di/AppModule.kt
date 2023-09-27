package com.rahultyagi.di

import com.rahultyagi.db.UserDao
import com.rahultyagi.db.UserDaoImpl
import com.rahultyagi.repository.user.UserRepo
import com.rahultyagi.repository.user.UserRepoImpl
import org.koin.dsl.module


val appModule = module {
    single<UserRepo> { UserRepoImpl(get()) }
    single<UserDao> { UserDaoImpl() }
}