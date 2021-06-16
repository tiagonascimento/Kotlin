package com.tiagorocha.tasks.service.repository.local.Interface

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tiagorocha.tasks.service.model.PriorityModel

@Dao
interface IPriorityDAO {
    @Insert
    fun save(list: List<PriorityModel>)

    @Query("Select * from priority")
    fun all() : List<PriorityModel>

    @Query("Select * from priority where id = :id")
    fun getPriorityById(id:Int) : PriorityModel

    @Query("Delete From priority")
    fun clear()
}