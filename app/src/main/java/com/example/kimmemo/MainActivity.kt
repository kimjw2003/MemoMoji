package com.example.kimmemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var memoDb : MemoDb? = null
    private var memoList = listOf<Memo>()
    lateinit var memoAdapter: RcViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memoDb = MemoDb.getInstance(this)
        memoAdapter = RcViewAdapter(this, memoList)

        val r = Runnable {
            try{
                Log.d("TAG", "Hello")

                memoList = memoDb?.MemoDao()?.getAll()!!
                memoAdapter = RcViewAdapter(this, memoList)
                memoAdapter.notifyDataSetChanged()

                runOnUiThread { // 일반 쓰레드 -> Main쓰레드가 처리하게 만듬
                    rcView.adapter = memoAdapter
                    rcView.layoutManager = LinearLayoutManager(this)
                    rcView.setHasFixedSize(true) // 아이템이 추가될때마다 사이즈 변형 여부
                }
            }catch(e: Exception){
                Log.d("tag", "Error - $e")
            }
        }
        Log.d("TAG", "실행")
        val thread = Thread(r)
        thread.start()

        write_text_btn.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)

        }
    }
    override fun onDestroy(){
        MemoDb.destroyInstance()
        memoDb = null
        super.onDestroy()
    }
}