package com.spartak.recipesapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RecipeEntity.TABLE_NAME)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    val id: Int,
    @ColumnInfo(name = TITLE_COLUMN)
    val title: String = "",
    @ColumnInfo(name = IMAGE_COLUMN)
    val image:String = "",
    @ColumnInfo(name = IS_FAVORITE_COLUMN)
    val isFavorite: Boolean = false,
) {
    companion object {
        const val TABLE_NAME = "recipe_table"
        const val ID_COLUMN = "id"
        const val TITLE_COLUMN = "title"
        const val IS_FAVORITE_COLUMN = "is_favorite"
        const val IMAGE_COLUMN = "image"
    }
}