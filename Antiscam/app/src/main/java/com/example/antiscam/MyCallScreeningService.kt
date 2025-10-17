
package com.example.antiscam

import android.content.Context
import android.speech.tts.TextToSpeech
import android.telecom.CallScreeningService
import android.telecom.Call.Details
import android.telecom.CallResponse
import com.example.antiscam.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MyCallScreeningService : CallScreeningService(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null

    override fun onScreenCall(callDetails: Details) {
        val phoneNumber = callDetails.handle.schemeSpecificPart
        val dao = AppDatabase.getDatabase(applicationContext).scamDao()

        CoroutineScope(Dispatchers.IO).launch {
            val scam = dao.findNumber(phoneNumber)
            if (scam != null) {
                speakWarning(applicationContext)
                respondToCall(callDetails, CallResponse.Builder()
                    .setDisallowCall(false)
                    .build()
                )
            }
        }
    }

    private fun speakWarning(context: Context) {
        if (tts == null) {
            tts = TextToSpeech(context, this)
        } else {
            tts?.speak(
                "โปรดระวัง เบอร์นี้ต้องสงสัยว่าเป็นสแกมเมอร์",
                TextToSpeech.QUEUE_FLUSH,
                null,
                "ScamWarning"
            )
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale("th", "TH")
            tts?.speak(
                "โปรดระวัง เบอร์นี้ต้องสงสัยว่าเป็นสแกมเมอร์",
                TextToSpeech.QUEUE_FLUSH,
                null,
                "ScamWarning"
            )
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
