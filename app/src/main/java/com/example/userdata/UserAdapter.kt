package com.example.userdata

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.modelresp.UpdatedUserResp
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class  UserAdapter(private var userList: List<UpdatedUserResp.Result>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    fun saveDataToPreferences(context: Context) {
        val preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val userPreferences = M1ViewModel2Preferences(userList)
        val json = gson.toJson(userPreferences)

        preferences.edit().putString("userList", json).apply()
    }

    interface OnItemClickListener {
        fun onItemClick(userId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

      //  holder.textUserId.text = "${user.userId}"
        holder.textFollowerCount.text = "${user.followerCount}"
        holder.textProgress.text = "${user.progress}"

        // Check if user.progress is less than 0
        if (user.progress < 0) {
            // Set red text color
            holder.textProgress.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red_background_color))
        }else if (user.progress > 0) {
            holder.textProgress.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.darkgreen))
        }else {
            // Set default text color for other cases
            holder.textProgress.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }

        // Parse the createdAt value into a LocalDateTime
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
        val createdAtDateTime = LocalDateTime.parse(user.createdAt, formatter)

        // Format date and time
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        // Set the formatted values in the TextViews
        holder.dateTextView.text = createdAtDateTime.format(dateFormatter)
        holder.timeTextView.text = createdAtDateTime.format(timeFormatter)
        // Set click listener on the item
        holder.itemView.setOnClickListener {
            // Trigger the itemClickListener with the userId
            itemClickListener.onItemClick(user.userId)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateData(newList: List<UpdatedUserResp.Result>) {
        userList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       // val textUserId: TextView = itemView.findViewById(R.id.usernameTextView)
        val textFollowerCount: TextView = itemView.findViewById(R.id.followersTextView)
        val textProgress: TextView = itemView.findViewById(R.id.progressTextview)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)

    }
}

