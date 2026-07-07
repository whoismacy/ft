package com.shrmrm.ft.data.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int = 1,
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
)
