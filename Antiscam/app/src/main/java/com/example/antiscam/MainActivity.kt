
package com.example.antiscam

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.antiscam.data.AppDatabase
import com.example.antiscam.data.FirebaseSync
import com.example.antiscam.data.ScamNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseSync.startSync(this)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val input = EditText(this).apply {
            hint = "กรอกเบอร์ที่ต้องการเพิ่มใน Blacklist"
        }

        val addBtn = Button(this).apply {
            text = "เพิ่มเบอร์สแกม"
            setOnClickListener {
                val phone = input.text.toString()
                if (phone.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        AppDatabase.getDatabase(applicationContext)
                            .scamDao()
                            .insertNumber(ScamNumber(phone))
                    }
                }
            }
        }

        val settingBtn = Button(this).apply {
            text = "ตั้งเป็นแอปป้องกันสแกม (Default Call Screening App)"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS))
            }
        }

        layout.addView(input)
        layout.addView(addBtn)
        layout.addView(settingBtn)

        setContentView(layout)
    }
}
