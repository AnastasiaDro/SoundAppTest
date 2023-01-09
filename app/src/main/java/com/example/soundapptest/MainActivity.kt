package com.example.soundapptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.soundapptest.databinding.ActivityMainBinding
import com.example.soundapptest.sound.SoundManager

class MainActivity : AppCompatActivity() {

    private lateinit var soundManager: SoundManager
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soundManager = SoundManager()
        with(viewBinding) {
            sampleText.text = soundManager.startSound()
        }
    }
}
