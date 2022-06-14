package com.example.chucknorries.data.local.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chucknorries.domain.utils.Constant.CHUCK_NORRIS_TABLE

@Entity(tableName = CHUCK_NORRIS_TABLE)
data class ChuckNorrisEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val categories: List<String>,
    val created_at: String,
    val value: String,
    val isFavourite:Boolean
)
