package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeech.databinding.ActivityMainBinding
import com.example.texttospeech.databinding.ContentMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var amBinding: ActivityMainBinding
    private lateinit var cmBinding: ContentMainBinding

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amBinding = ActivityMainBinding.inflate(layoutInflater)
        cmBinding = ContentMainBinding.inflate(layoutInflater)
        setContentView(amBinding.root)
        setContentView(cmBinding.root)

        tts = TextToSpeech(this, this)

        amBinding.tvTitle.text = "New Title"

        cmBinding.btnSpeak.setOnClickListener {
            if (cmBinding.etEnteredText.text.isNullOrEmpty()) {
                Toast.makeText(this, "Enter text to use TTS.", Toast.LENGTH_SHORT).show()
            } else {
                speakOut(cmBinding.etEnteredText.text.toString())
            }
        }

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language specified is not supported")
            }
        } else {
            Log.e("TTS", "Initialization Failed")
        }
    }

    public override fun onDestroy() {

        if (tts != null) {
            tts.stop()
            tts.shutdown()
        }

        super.onDestroy()
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}