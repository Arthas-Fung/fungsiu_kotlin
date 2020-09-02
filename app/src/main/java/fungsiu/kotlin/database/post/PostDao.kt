package fungsiu.kotlin.database.post

import androidx.room.*

@Dao
interface  PostDao {

    @Query("select * from " + PostModel.TABLE_NAME)
    fun getAll(): List<PostModel>


    @Query("select * from " + PostModel.TABLE_NAME + " where id LIKE :id LIMIT 1")
    fun selectById(id: Long): PostModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PostModel):Long

    @Query("delete from " + PostModel.TABLE_NAME + " where title LIKE :title")
    fun deleteByTitle(title: String): Int

//    @Update
//    fun update(item: PostModel):Int
//
//
//    @Delete
//    fun delete(item: PostModel):Int

}