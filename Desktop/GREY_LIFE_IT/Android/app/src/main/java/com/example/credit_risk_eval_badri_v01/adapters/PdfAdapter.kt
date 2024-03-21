package com.example.credit_risk_eval_badri_v01.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.databinding.PdfItemBinding
import com.example.credit_risk_eval_badri_v01.models.FileinModel

class PdfAdapter(var context: Context, var list:ArrayList<FileinModel>) : RecyclerView.Adapter<PdfAdapter.PdfViewHolder>() {

    inner class PdfViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var binding : PdfItemBinding = PdfItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        return PdfViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pdf_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        val user = list[position]
        holder.binding.pdfName.text = user.filename

        //on click
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.type = "application/pdf"
            intent.data = Uri.parse(user.fileurl)
            context.startActivity(intent)

        }

    }
}