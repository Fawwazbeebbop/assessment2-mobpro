package org.d3if3051.assessment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "finance")
data class Finance(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val note: String,
    val category: String,
    val date: String
)
