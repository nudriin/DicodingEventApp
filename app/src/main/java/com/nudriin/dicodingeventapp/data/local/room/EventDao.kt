package com.nudriin.dicodingeventapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nudriin.dicodingeventapp.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<EventEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEvent(events: EventEntity)

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Int): LiveData<EventEntity?>

    @Query("DELETE FROM events WHERE id = :id")
    fun deleteEventById(id: Int)
}