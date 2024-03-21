package com.example.credit_risk_eval_badri_v01.adapters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.activities.HomeScreenActivity
import com.example.credit_risk_eval_badri_v01.models.LoanTypeModel
import com.example.credit_risk_eval_badri_v01.fragments.BorrowerLoanDetailsFragment

class LoanTypeAdapter(private val loanTypeList:ArrayList<LoanTypeModel>, private val activity: HomeScreenActivity):
    RecyclerView.Adapter<LoanTypeAdapter.LoanTypeViewHolder>() {

    class LoanTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvLoanType: TextView = itemView.findViewById(R.id.tvLoanType)
        val ivLoanType: ImageView = itemView.findViewById(R.id.ivLoanType)
        val layout:LinearLayout = itemView.findViewById(R.id.layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanTypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_loan_type_item,parent,false)
        return LoanTypeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return loanTypeList.size
    }

    var index = -1
    override fun onBindViewHolder(holder: LoanTypeViewHolder, position: Int) {
        val loanType = loanTypeList[position]
        holder.tvLoanType.text = loanType.loanType
        holder.ivLoanType.setImageResource(loanType.loanIcon)
        holder.itemView.setOnClickListener (object:View.OnClickListener{
            override fun onClick(p0: View?) {
                val activity = p0!!.context as AppCompatActivity
                val borrowerLoanDetailsFragment = BorrowerLoanDetailsFragment()

                //try
                val bundle = Bundle()
                bundle.putString("loanType", loanType.loanType)
                borrowerLoanDetailsFragment.arguments = bundle

//                activity.supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainerView,borrowerLoanDetailsFragment)
//                    .addToBackStack(null)
//                    .commit()

                val fragmentManager = activity.supportFragmentManager
                fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, borrowerLoanDetailsFragment)
                transaction.commit()

                index = position
                notifyDataSetChanged()
            }

        })

        if(index == position){
            holder.tvLoanType.setTextColor(Color.BLACK)
            holder.layout.setBackgroundColor(Color.parseColor("#FDD962"))

            val color = ContextCompat.getColor(holder.itemView.context, R.color.black)
            holder.ivLoanType.setColorFilter(color)

        }
        else{
            holder.tvLoanType.setTextColor(Color.parseColor("#C6C7C9"))
            holder.layout.setBackgroundColor(Color.parseColor("#233238"))

            val color = ContextCompat.getColor(holder.itemView.context, R.color.myWhite)
            holder.ivLoanType.setColorFilter(color)

        }

    }

}