package com.meteoro.localdata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meteoro.localdata.data.FavoriteGif

@Database(entities = [FavoriteGif::class], version = 1)
abstract class GifDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao
}
