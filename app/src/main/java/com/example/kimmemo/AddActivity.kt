package com.example.kimmemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.edit_title
import kotlinx.android.synthetic.main.activity_detail.*

class AddActivity : AppCompatActivity() {
    private var memoDB : MemoDb? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        memoDB = MemoDb.getInstance(this)


        
        val addRunnable = Runnable {
            val newMemo = Memo()
            newMemo.title = edit_title.text.toString()
            newMemo.content = edit_content.text.toString()
            memoDB?.MemoDao()?.insert(newMemo)
        }

        add_text_btn.setOnClickListener {
            val addThread = Thread(addRunnable)
            addThread.start()

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onDestroy() {
        MemoDb.destroyInstance()
        super.onDestroy()
    }
}