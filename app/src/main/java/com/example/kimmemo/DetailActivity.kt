package com.example.kimmemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.Calendar.getInstance

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var memoDb : MemoDb? = null
        memoDb = MemoDb.getInstance(this)

        val contents = intent.getStringExtra("content")
        val title = intent.getStringExtra("title")

        watch_edit_title.text = title
        watch_edit_content.text = contents

        val delRunnable = Runnable { // delRunnable 쓰레드 제작
            memoDb?.MemoDao()?.delete(title+"", contents+"") //OnClickListener에서 delRunnable쓰레드로 이동
        }

        Del_button.setOnClickListener{
            Log.d("TAG", "title : $title")
            Log.d("TAG", "contents :$contents")
            Thread(delRunnable).start() // 쓰레드 시작
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}