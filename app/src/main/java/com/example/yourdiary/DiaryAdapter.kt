package com.example.yourdiary

import android.content.Intent
import android.provider.BaseColumns._ID
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yourdiary.data.DatabaseManager.DiaryEntry.TABLE_NAME
import com.example.yourdiary.data.Diary
import com.example.yourdiary.data.DiaryDBHelper
import kotlinx.android.synthetic.main.recycler_diary_item.view.*

class DiaryAdapter(private var diaryList : MutableList<Diary>) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): DiaryAdapter.DiaryViewHolder {
        val context = viewGroup.context
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false
        val view = inflater.inflate(R.layout.recycler_diary_item,viewGroup,shouldAttachToParentImmediately)

        view.delete_button.setOnClickListener{

            val mDBHelper = DiaryDBHelper(view.context)
            val db = mDBHelper.writableDatabase

            val selection = "$_ID = ?"
            val selectionArgs = arrayOf("${diaryList[position].id}")

            db.delete(TABLE_NAME,selection,selectionArgs)

            diaryList.removeAt(position)

            notifyDataSetChanged()
        }

        return DiaryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    override fun onBindViewHolder(holder: DiaryAdapter.DiaryViewHolder, position: Int) {
        val item = diaryList[position]
        holder.bindDiary(item)
    }


    class DiaryViewHolder(v : View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view : View
        private lateinit var diary : Diary
        private var date : TextView
        private var title : TextView

        override fun onClick(v: View?) {
            val context = itemView.context
            val intent = Intent(context,NewDiaryActivity::class.java)
            intent.putExtra("idOfRow",diary.id)
            context.startActivity(intent)
        }

        init {
            view = v
            title = v.findViewById(R.id.title_recycler_item)
            date = v.findViewById(R.id.date_recycler_item)
            v.setOnClickListener(this)
        }
        fun bindDiary(diary: Diary) {
            this.diary = diary
            date.text = diary.date
            title.text = diary.title
        }
    }
}