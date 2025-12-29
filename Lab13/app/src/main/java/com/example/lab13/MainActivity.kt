package com.example.lab13

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ACTION_MUSIC = "music"
        private const val ACTION_NEW = "new"
        private const val ACTION_SPORT = "sport"
        private const val EXTRA_MSG = "msg"
        private const val EXTRA_CHANNEL = "channel"
    }

    private lateinit var tvMsg: TextView
    private var receiverRegistered = false

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            tvMsg.text = intent.getStringExtra(EXTRA_MSG).orEmpty()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvMsg = findViewById(R.id.tvMsg)

        findViewById<Button>(R.id.btnMusic).setOnClickListener { startChannel(ACTION_MUSIC) }
        findViewById<Button>(R.id.btnNew).setOnClickListener { startChannel(ACTION_NEW) }
        findViewById<Button>(R.id.btnSport).setOnClickListener { startChannel(ACTION_SPORT) }
    }

    override fun onStart() {
        super.onStart()
        if (!receiverRegistered) {
            val filter = IntentFilter().apply {
                addAction(ACTION_MUSIC)
                addAction(ACTION_NEW)
                addAction(ACTION_SPORT)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // App 內部用：NOT_EXPORTED 較安全
                registerReceiver(receiver, filter, RECEIVER_NOT_EXPORTED)
            } else {
                registerReceiver(receiver, filter)
            }
            receiverRegistered = true
        }
    }

    override fun onStop() {
        if (receiverRegistered) {
            unregisterReceiver(receiver)
            receiverRegistered = false
        }
        super.onStop()
    }

    private fun startChannel(channel: String) {
        val i = Intent(this, MyService::class.java).apply {
            putExtra(EXTRA_CHANNEL, channel)
        }
        startService(i)
    }
}
