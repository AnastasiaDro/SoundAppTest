package com.example.soundapptest


import android.content.ContextWrapper
import android.content.pm.PackageManager
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


class MainActivity1 : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root) //TODO погуглить, в чем разница с setContentView(R.layout.activity_main)



        with(viewBinding) {
            buttonStart.setOnClickListener {
                if (checkMicrophoneAppearance()) requestPermission() else Toast.makeText(applicationContext, R.string.no_micro, Toast.LENGTH_SHORT).show()

                Toast.makeText(this@MainActivity1, "Recording is started", Toast.LENGTH_SHORT).show()
            }

            buttonStop.setOnClickListener {

                Toast.makeText(this@MainActivity1, "Recording is stopped", Toast.LENGTH_SHORT).show()
            }

            buttonPlay.setOnClickListener {

                Toast.makeText(this@MainActivity1, "Recording is playing", Toast.LENGTH_SHORT).show()
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