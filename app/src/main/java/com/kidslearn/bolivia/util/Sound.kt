package com.kidslearn.bolivia.util

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

object Sound {
    private var tts: TextToSpeech? = null

    fun init(context: Context) {
        if (tts != null) return
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val es = Locale("es", "BO")
                val result = tts?.setLanguage(es)
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    tts?.language = Locale("es", "ES")
                }
                tts?.setSpeechRate(0.85f)
                tts?.setPitch(1.15f)
            }
        }
    }

    fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "kids")
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
