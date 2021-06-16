package com.tiagorocha.tasks.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tiagorocha.tasks.service.model.PriorityModel
import com.tiagorocha.tasks.service.repository.local.Interface.IPriorityDAO


@Database(entities = [PriorityModel::class], version = 1)
abstract class TaskDataBase  : RoomDatabase(){

    abstract  fun IPriorityDAO() : IPriorityDAO

    companion object{
        private lateinit var INSTANCE : TaskDataBase

        fun getDataBase(context: Context):TaskDataBase{
            if(!Companion::INSTANCE.isInitialized) {
                synchronized(TaskDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context, TaskDataBase::class.java, "TaskDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}