package com.codemave.mobilecomputing.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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
    @ColumnInfo(name = "task_title") val taskTitle: String,
    @ColumnInfo(name = "task_date") val taskDate: Long,
    @ColumnInfo(name = "task_category_id") val taskCategoryId: Long,
    @ColumnInfo(name = "task_amount") val taskAmount: Double
)
