package com.codemave.mobilecomputing.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

import java.util.*

@Entity(
    tableName = "tasks",
    indices = [
        Index("id", unique = true),
        Index("task_category_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["task_category_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Task(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val taskId: Long = 0,
    @ColumnInfo(name = "message") val taskTitle: String,
    @ColumnInfo(name= "location_x") val taskLocationX: Long,
    @ColumnInfo(name= "location_y") val taskLocationY: Long,
    @ColumnInfo(name= "reminder_time") val reminderTime: Long,
    @ColumnInfo(name = "creation_time") val creationTime: Long,
    @ColumnInfo(name= "creator_id") val creatorId: String,
    @ColumnInfo(name= "reminder_seen") val reminderSeen: Long?,
    @ColumnInfo(name = "task_category_id") val taskCategoryId: Long,
    @ColumnInfo(name = "bool") val bool: Boolean,





    )
