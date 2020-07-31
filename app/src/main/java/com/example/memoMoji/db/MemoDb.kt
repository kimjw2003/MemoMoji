package com.example.memoMoji.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Memo::class], version = 2)
abstract class MemoDb: RoomDatabase() {
    abstract fun MemoDao(): MemoDao

    companion object { //DB제작
        private var INSTANCE: MemoDb? = null

        fun getInstance(context: Context): MemoDb? {
            if (INSTANCE == null) {
                synchronized(MemoDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MemoDb::class.java, "Memo.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}