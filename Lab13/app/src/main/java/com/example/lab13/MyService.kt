package com.example.lab13

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper

class MyService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private var channel: String = ""

    private var delayedTask: Runnable? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        channel = intent?.getStringExtra(MainActivity.EXTRA_CHANNEL).orEmpty()

        // 先送第一段訊息
        sendMsg(
            when (channel) {
                "music" -> "歡迎來到音樂頻道"
                "new" -> "歡迎來到新聞頻道"
                "sport" -> "歡迎來到體育頻道"
                else -> "頻道錯誤"
            }
        )

        // ✅ 取消上一個尚未執行的延遲任務（避免連按亂跳）
        delayedTask?.let { handler.removeCallbacks(it) }

        delayedTask = Runnable {
            sendMsg(
                when (channel) {
                    "music" -> "即將播放本月TOP10音樂"
                    "new" -> "即將為您提供獨家新聞"
                    "sport" -> "即將播報本週NBA賽事"
                    else -> "頻道錯誤"
                }
            )
            stopSelf(startId) // ✅ 這次工作結束就停
        }.also {
            handler.postDelayed(it, 3000)
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        delayedTask?.let { handler.removeCallbacks(it) }
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? = null

    private fun sendMsg(msg: String) {
        sendBroadcast(
            Intent(MainActivity.ACTION_MSG).putExtra(MainActivity.EXTRA_MSG, msg)
        )
    }
}
