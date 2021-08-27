package com.mudhut.postsapp.db.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    @Embedded val address: Address,
    val phone: String,
    val website: String,
    @Embedded val company: Company
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded val geo: Geo
)

data class Geo(
    val lat: String,
    val lng: String
)

data class Company(
    val companyName: String,
    val catchPhrase: String,
    val bs: String,
)
