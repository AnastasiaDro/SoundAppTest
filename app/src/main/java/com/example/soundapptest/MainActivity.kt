package com.example.soundapptest

import android.Manifest.permission.RECORD_AUDIO
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.soundapptest.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.S)
    private var mediaRecorder: MediaRecorder? = null
    private var player : MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root) //TODO погуглить, в чем разница с setContentView(R.layout.activity_main)



        with(viewBinding) {
            buttonStart.setOnClickListener {
                if (checkMicrophoneAppearance()) requestPermission() else Toast.makeText(applicationContext, R.string.no_micro, Toast.LENGTH_SHORT).show()
                mediaRecorder = MediaRecorder(this@MainActivity)
                mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
                mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                mediaRecorder!!.setOutputFile(getFilePath())
                mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                mediaRecorder!!.prepare()
                mediaRecorder!!.start()

                Toast.makeText(this@MainActivity, "Recording is started", Toast.LENGTH_SHORT).show()
            }

            buttonStop.setOnClickListener {
                mediaRecorder!!.stop()
                mediaRecorder!!.release()
                mediaRecorder = null
                Toast.makeText(this@MainActivity, "Recording is stopped", Toast.LENGTH_SHORT).show()
            }

            buttonPlay.setOnClickListener {
                player = MediaPlayer()
                player!!.setDataSource(getFilePath())
                player!!.prepare()
                player!!.start()
                Toast.makeText(this@MainActivity, "Recording is playing", Toast.LENGTH_SHORT).show()
            }
        }



    }

    private fun checkMicrophoneAppearance(): Boolean {
        return (this.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission", "Granted")
            } else {
                Log.i("permission", "Denied")
            }
        }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("Permission", "Granted")
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                "RECORD_AUDIO") ->
                {
                    Snackbar.make(this.viewBinding.textView, "PErmission for mic required", Snackbar.LENGTH_INDEFINITE).setAction("DGSDGD") {
                        requestPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                    }
                }
            else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                }
        }
    }

    private fun getFilePath(): String {
        val contextWrapper = ContextWrapper(applicationContext)
        val filePath = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        return File(filePath, "TestRecordingFile.mp3").path
    }
}