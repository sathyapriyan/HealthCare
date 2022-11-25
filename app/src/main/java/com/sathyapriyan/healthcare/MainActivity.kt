package com.sathyapriyan.healthcare

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.sathyapriyan.healthcare.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivity"

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        result.entries.forEach {
            Log.d(TAG, "${it.key} = ${it.value}")

            if (it.key == Manifest.permission.RECORD_AUDIO && it.value) {
                requestCameraPermission()
            }

            if (it.key == Manifest.permission.CAMERA && it.value) {
                Toast.makeText(
                    this,
                    "All Permissions granted",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        requestPermissionLauncher.launch(arrayOf(Manifest.permission.RECORD_AUDIO))

    }

    private fun requestCameraPermission() {

        requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))

    }

    override fun onBackPressed(){
    }
}