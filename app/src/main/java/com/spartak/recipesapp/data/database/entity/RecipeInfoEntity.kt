package com.spartak.recipesapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RecipeInfoEntity.TABLE_NAME)
data class RecipeInfoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    val id: Int,
    @ColumnInfo(name = TITLE_COLUMN)
    val title: String = "",
    @ColumnInfo(name = IMAGE_COLUMN)
    val image: String = "",
    @ColumnInfo(name = INSTRUCTION_COLUMN)
    val instruction: String = "",
    @ColumnInfo(name = INGREDIENTS_COLUMN)
    val ingredients: List<IngredientEntity> = listOf()
) {
    companion object {
        const val TABLE_NAME = "recipe_info_table"
        const val ID_COLUMN = "id"
        const val TITLE_COLUMN = "title"
        const val INSTRUCTION_COLUMN = "instruction"
        const val INGREDIENTS_COLUMN = "ingredients"
        const val IMAGE_COLUMN = "image"
    }
}
data class IngredientEntity(
    @ColumnInfo(name = ID_COLUMN)
    val id: Int,
    @ColumnInfo(name = NAME_COLUMN)
    val name: String,
    @ColumnInfo(name = ORIGINAL_COLUMN)
    val original: String,
    @ColumnInfo(name = AMOUNT_COLUMN)
    val amount: Double,
    @ColumnInfo(name = UNIT_COLUMN)
    val unit: String,
) {
    companion object {
        const val ID_COLUMN = "ingredients_id"
        const val NAME_COLUMN = "ingredients_name"
        const val ORIGINAL_COLUMN = "ingredients_original"
        const val AMOUNT_COLUMN = "ingredients_amount"
        const val UNIT_COLUMN = "ingredients_unit"
    }
}