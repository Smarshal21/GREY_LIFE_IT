package com.example.credit_risk_eval_badri_v01.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.databinding.ReceiverChatMsgLayoutBinding
import com.example.credit_risk_eval_badri_v01.databinding.SenderChatMsgLayoutBinding
import com.example.credit_risk_eval_badri_v01.models.MessageModel
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(var context: Context, var list:ArrayList<MessageModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ITEM_SENT = 1
    var ITEM_RECEIVE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ITEM_SENT) {
            SenderViewHolder(LayoutInflater.from(context).inflate(R.layout.sender_chat_msg_layout,parent,false))
        }
        else{
            ReceiverViewHolder(LayoutInflater.from(context).inflate(R.layout.receiver_chat_msg_layout,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]
        if(holder.itemViewType == ITEM_SENT){
            val viewHolder = holder as SenderViewHolder
            viewHolder.binding.tvMessage.text = message.message
        }
        else{
            val viewHolder = holder as ReceiverViewHolder
            viewHolder.binding.tvMessage.text = message.message
        }
    }


    inner class SenderViewHolder(view: View): RecyclerView.ViewHolder(view){
        var binding = SenderChatMsgLayoutBinding.bind(view)
    }

    inner class ReceiverViewHolder(view: View): RecyclerView.ViewHolder(view){
        var binding = ReceiverChatMsgLayoutBinding.bind(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseAuth.getInstance().uid == list[position].senderId) ITEM_SENT else ITEM_RECEIVE
    }

}