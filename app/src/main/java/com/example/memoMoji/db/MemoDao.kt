package com.example.memoMoji.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MemoDao {
    @Query("SELECT * FROM Memo")
    fun getAll(): List<Memo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: Memo)

    @Query("DELETE  FROM Memo WHERE title = :title AND content = :content")
    fun delete(title: String , content: String)

}