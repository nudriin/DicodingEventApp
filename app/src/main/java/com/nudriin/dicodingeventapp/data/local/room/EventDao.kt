package com.nudriin.dicodingeventapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nudriin.dicodingeventapp.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM events where favorite = 1")
    fun getFavoriteEvents(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEvents(events: List<EventEntity>)

    @Update
    fun updateEvent(events: EventEntity)

    @Query("DELETE FROM events WHERE favorite = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM events WHERE id = :id AND favorite = 1)")
    fun isNewsBookmarked(id: String): Boolean
}