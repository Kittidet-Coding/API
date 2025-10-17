
package com.example.antiscam.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScamDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNumber(number: ScamNumber)

    @Query("DELETE FROM scam_numbers WHERE phoneNumber = :phone")
    suspend fun deleteNumber(phone: String)

    @Query("SELECT * FROM scam_numbers WHERE phoneNumber = :phone LIMIT 1")
    suspend fun findNumber(phone: String): ScamNumber?
}
