package com.vtsb.hipago.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "language_tag",
    inheritSuperIndices = true,
    indices = [
        Index(value = ["name"], unique = true)
    ]
)
data class LanguageTag(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    val local: String,
)
