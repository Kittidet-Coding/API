
package com.example.antiscam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scam_numbers")
data class ScamNumber(
    @PrimaryKey val phoneNumber: String
)
