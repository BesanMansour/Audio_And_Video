package com.project.clapapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import com.project.clapapp.databinding.ActivitySoundBinding

class SoundActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySoundBinding
    private var mediaPlayer: MediaPlayer ?= null
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(Looper.getMainLooper())

        binding.MainImgVideo.setOnClickListener {
            startActivity(Intent(this,VideoActivity::class.java))
        }

        binding.MainFloatingPlay.setOnClickListener {
            if (mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this,R.raw.clapping)
                initializeSeekBar()
            }
            mediaPlayer?.start()
        }

        binding.MainFloatingPause.setOnClickListener {
            mediaPlayer?.pause()
        }

        binding.MainFloatingStop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            binding.MainSeekBar.progress = 0
            binding.MainTvDue.text = "0 sec"
            binding.MainTvPlayer.text = "0 sec"

        }

    }
    fun initializeSeekBar(){
        binding.MainSeekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            // Notification that the progress level has changed
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
            }

            // Notification that the user has started a touch gesture
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            //Notification that the user has finished a touch gesture
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.MainSeekBar.max = mediaPlayer!!.duration
        runnable = Runnable {
            binding.MainSeekBar.progress = mediaPlayer!!.currentPosition

            val palyedTime = mediaPlayer!!.currentPosition/1000
            binding.MainTvPlayer.text = "$palyedTime sec"

            val duration = mediaPlayer!!.duration/1000
            val dueTime = duration-palyedTime
            binding.MainTvDue.text = "$dueTime sec"

            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
    }
}