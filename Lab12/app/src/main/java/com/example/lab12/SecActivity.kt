package com.example.lab12

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            val serviceIntent = Intent(this, MyService::class.java)
            startService(serviceIntent)

            Toast.makeText(this, "啟動 Service（3 秒後跳轉）", Toast.LENGTH_SHORT).show()

            // Lab 示範：按下按鈕後就關閉 MainActivity，避免使用者停留在起始頁面
            finish()
        }
    }
}
