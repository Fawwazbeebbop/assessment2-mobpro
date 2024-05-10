package org.d3if3051.assessment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3051.assessment2.model.Finance

@Dao
interface FinanceDao {
    @Insert
    suspend fun insert(finance: Finance)

    @Update
    suspend fun update(finance: Finance)

    @Query("SELECT * FROM finance ORDER BY date DESC")
    fun getFinance(): Flow<List<Finance>>

    @Query("SELECT * FROM finance WHERE id = :id")
    suspend fun getFinanceById(id: Long): Finance?

    @Query("DELETE FROM finance WHERE id = :id")
    suspend fun deleteById(id: Long)
}