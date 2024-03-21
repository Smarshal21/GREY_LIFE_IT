package com.example.credit_risk_eval_badri_v01.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.activities.ClientChatScreenActivity
import com.example.credit_risk_eval_badri_v01.activities.DocsDetailsActivity
import com.example.credit_risk_eval_badri_v01.databinding.ClientChatUserItemLayoutBinding
import com.example.credit_risk_eval_badri_v01.models.UserModel

class DocsAdapter(var context: Context, var list:ArrayList<UserModel>) : RecyclerView.Adapter<DocsAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var binding : ClientChatUserItemLayoutBinding = ClientChatUserItemLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.client_chat_user_item_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val user = list[position]
        //set the profile
        //Glide.with(context).load(user.imageUrl).into(holder.binding.userImage)
        holder.binding.userName.text = user.name
        holder.binding.userId.text = user.uid
        holder.binding.userEmail.text = user.email

        //on click
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DocsDetailsActivity::class.java)
            intent.putExtra("uid",user.uid)
            context.startActivity(intent)
        }

    }
}