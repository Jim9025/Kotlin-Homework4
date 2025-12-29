package com.example.lab12

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper

class MyService : Service() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()

        // 使用 Handler 延遲執行，避免手動 Thread 管理
        handler.postDelayed({
            val intent = Intent(this, SecActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)

            // 啟動完成後結束 Service，避免背景佔用資源
            stopSelf()
        }, 3000)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Service 被系統回收後不自動重啟
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除尚未執行的 callback，避免記憶體洩漏
        handler.removeCallbacksAndMessages(null)
    }

    override fun onBind(intent: Intent): IBinder? = null
}
