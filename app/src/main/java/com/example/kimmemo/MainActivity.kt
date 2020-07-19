package com.example.kimmemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var memoDb : MemoDb? = null
    private var memoList = listOf<Memo>()
    private lateinit var memoAdapter: RcViewAdapter
    private var mBackWait:Long = 0

    val currentDateTime = Calendar.getInstance().time
    var dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(currentDateTime)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeView.text = dateFormat


        memoDb = MemoDb.getInstance(this)// Db에 접근을 가능하게 해줌
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
    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭
        if(System.currentTimeMillis() - mBackWait >=1500 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this,"한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show() //토스트 출력
        } else {
            moveTaskToBack(true) //finish후 다른 액티비티가 보여지는것을 방지
            finish() //액티비티 종료
            android.os.Process.killProcess(android.os.Process.myPid()) //앱, 프로세스까지 강제종료
        }
    }
}