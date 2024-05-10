package org.d3if3051.assessment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3051.assessment2.model.Finance

@Database(entities = [Finance::class], version = 1, exportSchema = false)
abstract class FinanceDb: RoomDatabase() {
    abstract val dao: FinanceDao

    companion object{
        @Volatile
        private var INSTANCE: FinanceDb? = null

        fun getInstance(context: Context): FinanceDb{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FinanceDb::class.java,
                        "finance.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}