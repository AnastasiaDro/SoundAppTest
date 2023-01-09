package com.example.soundapptest.sound

class SoundManager {
    init {
        System.loadLibrary("native-lib")
    }

    external fun startSound(): String
}