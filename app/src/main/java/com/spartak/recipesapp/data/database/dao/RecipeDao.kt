package com.spartak.recipesapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spartak.recipesapp.data.database.entity.RecipeEntity
import com.spartak.recipesapp.data.database.entity.RecipeInfoEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface RecipeDao {

    @Query("SELECT * FROM ${RecipeEntity.TABLE_NAME}")
    fun fetchRecipes(): Flowable<List<RecipeEntity>>

    @Query("SELECT * FROM ${RecipeEntity.TABLE_NAME} WHERE ${RecipeEntity.TITLE_COLUMN} LIKE  '%' || :text || '%'")
    fun searchRecipes(text: String): Flowable<List<RecipeEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(recipe: RecipeEntity): Completable

    @Query("DELETE FROM ${RecipeEntity.TABLE_NAME} WHERE :id = ${RecipeEntity.ID_COLUMN}")
    fun deleteRecipe(id: Int): Completable

    @Query("DELETE FROM ${RecipeInfoEntity.TABLE_NAME} WHERE :id = ${RecipeEntity.ID_COLUMN}")
    fun deleteRecipeInfo(id: Int): Completable


    @Query("SELECT EXISTS (SELECT 1 FROM ${RecipeEntity.TABLE_NAME} WHERE :id = ${RecipeEntity.ID_COLUMN})")
    fun existsFavorite(id: Int): Single<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipeInfo(recipeInfo: RecipeInfoEntity): Completable

    @Query("SELECT * FROM ${RecipeInfoEntity.TABLE_NAME} WHERE :id = ${RecipeInfoEntity.ID_COLUMN}")
    fun fetchRecipeInfoById(id: Int): Single<RecipeInfoEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM ${RecipeInfoEntity.TABLE_NAME} WHERE :id = ${RecipeInfoEntity.ID_COLUMN})")
    fun existsRecipeInfoFavorite(id: Int): Single<Boolean>
}