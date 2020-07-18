package com.example.kimmemo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Toast
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

        watch_edit_content.setMovementMethod(ScrollingMovementMethod()) //텍스트뷰 내에서 스크롤가능하게 해줌

        watch_edit_title.text = title
        watch_edit_content.text = contents

        val delRunnable = Runnable { // delRunnable 쓰레드 제작
            memoDb?.MemoDao()?.delete(title+"", contents+"") //OnClickListener에서 delRunnable쓰레드로 이동
        }

        Del_button.setOnClickListener{//Delete버튼 클릭시
            Thread(delRunnable).start() // delRunnable쓰레드 시작
            var intent = Intent(this, MainActivity::class.java) //삭제 후 메인으로 이동
            startActivity(intent)
            finish()
        }
        copy_Button.setOnClickListener{
            clipBoard()
        }
    }
    fun clipBoard(){

        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE)as ClipboardManager
        val clipData = ClipData.newPlainText("label", watch_edit_content.text.toString())
        clipboardManager!!.setPrimaryClip(clipData)

        Toast.makeText(this, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
    }
}