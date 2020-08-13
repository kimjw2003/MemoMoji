package com.example.memoMoji.memoView

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.memoMoji.R
import com.example.memoMoji.db.Memo
import com.example.memoMoji.db.MemoDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.edit_title

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

            if(edit_title.text.isNullOrBlank()){
                showDialog()
            }
            else {
                val addThread = Thread(addRunnable)
                addThread.start()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }

    override fun onDestroy() {
        MemoDb.destroyInstance()
        super.onDestroy()
    }

    private fun showDialog(){
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("경고")
            .setMessage("제목이 입력되지 않았습니다!")
            .setPositiveButton("확인", null)
            .create()

        alertDialog.show()
    }
}