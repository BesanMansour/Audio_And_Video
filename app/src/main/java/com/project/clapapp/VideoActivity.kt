package com.project.clapapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri.parse
import android.os.Bundle
import android.widget.MediaController
import com.project.clapapp.databinding.ActivityVideoBinding
import java.net.URI

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.VideoView)

        val uri : Uri =
            parse("android.resource://"+packageName+"/"+R.raw.video)

        binding.VideoView.setMediaController(mediaController)
        binding.VideoView.setVideoURI(uri)
        binding.VideoView.requestFocus()
        binding.VideoView.start()

    }
}