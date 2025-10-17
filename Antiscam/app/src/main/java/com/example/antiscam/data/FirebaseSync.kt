
package com.example.antiscam.data

import android.content.Context
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object FirebaseSync {
    fun startSync(context: Context) {
        val database = FirebaseDatabase.getInstance().getReference("scam_numbers")
        val dao = AppDatabase.getDatabase(context).scamDao()

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch {
                    snapshot.children.forEach { child ->
                        val phone = child.getValue(String::class.java)
                        if (!phone.isNullOrEmpty()) {
                            dao.insertNumber(ScamNumber(phone))
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // log error if needed
            }
        })
    }
}
