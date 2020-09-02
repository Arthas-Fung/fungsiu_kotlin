package fungsiu.kotlin.database.post

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PostModel.TABLE_NAME)
class PostModel {

    companion object {
        const val TABLE_NAME = "post_table"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var userId = 0
//    var id = 0
    var title = ""
    var body = ""

}