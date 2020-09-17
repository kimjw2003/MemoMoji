package com.example.memoMoji.db

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memoMoji.memoView.DetailActivity
import com.example.memoMoji.R

class RcViewAdapter(private val context: Context, private val memos: List<Memo>) :
    RecyclerView.Adapter<RcViewAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView?.findViewById<TextView>(R.id.item_title_text)
        val time = itemView?.findViewById<TextView>(R.id.item_time)

        fun bind(memo: Memo) {
            title?.text = memo.title
            time?.text = memo.time
            itemView.setOnClickListener {
                val i = Intent(itemView.context, DetailActivity::class.java)
                i.putExtra("title", memo.title)
                i.putExtra("content", memo.content)
                itemView.context.startActivity(i)
            }
        }
    }

    override fun getItemCount(): Int {
        return memos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_memo, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(memos[position])
    }

}