package com.example.notesapplication.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.MainActivity.Companion.COLOR
import com.example.notesapplication.R
import com.example.notesapplication.databinding.AddNoteBinding
import com.example.notesapplication.fragments.FragmentPage.Companion.noteViewModel
import com.example.notesapplication.operations.AddNote

@Suppress("DEPRECATION")
class ColorAdapter(val binding: AddNoteBinding, private val activity: AddNote) : RecyclerView.Adapter<MyViewHolder>() {
    private val colorList = listOf(
        R.drawable.c0,
        R.drawable.c1,
        R.drawable.c2,
        R.drawable.c3,
        R.drawable.c4,
        R.drawable.c5,
        R.drawable.c6,
        R.drawable.c7,
        R.drawable.c8,
        R.drawable.c9
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.color_list,parent,false)
        return MyViewHolder(inflater)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(colorList[position])

        holder.itemView.setOnClickListener {

            noteViewModel.colorIndex = position
            binding.noteFrame.setBackgroundColor(Color.parseColor(COLOR[position]))

            setStatusBar(COLOR,position)
        }

    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setStatusBar(colorCode: MutableList<String>, index: Int) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val color = Color.parseColor(colorCode[index].replace("#", "#66"))
        window.statusBarColor = color
    }

}

class MyViewHolder(val view : View) :  RecyclerView.ViewHolder(view){
    fun bind(color: Int) {
        view.findViewById<ImageView>(R.id.color).setImageResource(color)


    }
}