package com.codemave.mobilecomputing.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "logininfos",
    indices = [
        Index("username", unique = true)
    ]
)
data class LoginInfo(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String
)