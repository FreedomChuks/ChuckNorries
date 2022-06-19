package com.example.chucknorries.data.local.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chucknorries.domain.utils.Constant.CHUCK_NORRIS_TABLE

@Entity(tableName = CHUCK_NORRIS_TABLE)
data class JokesCacheEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val categories: List<String>,
    val value: String,
)
