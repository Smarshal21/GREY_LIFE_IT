package com.example.credit_risk_eval_badri_v01.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.adapters.ClientStatusAdapter
import com.example.credit_risk_eval_badri_v01.databinding.ActivityClientStatusScreenBinding
import com.example.credit_risk_eval_badri_v01.models.LoanDataModel
import com.example.credit_risk_eval_badri_v01.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClientStatusScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientStatusScreenBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var dataList : ArrayList<LoanDataModel>
    private lateinit var dialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientStatusScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableBottomNavView()
        getUserListFromDatabaseAndSetToRecyclerView()

    }


    private fun enableBottomNavView(){
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setSelectedItemId(R.id.statusScreen)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.documentsScreen -> {
                    startActivity(Intent(applicationContext, ClientDocumentsScreenActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.statusScreen -> true
                R.id.supportScreen -> {
                    startActivity(Intent(applicationContext, ClientSupportScreenActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }


    private fun getUserListFromDatabaseAndSetToRecyclerView(){
        database = FirebaseDatabase.getInstance()
        dataList = ArrayList()
        dialogBox("Fetching user status list","Please wait...")
        database.reference.child("loans")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dataList.clear()
                    for(snapshot1 in snapshot.children){
                        val data = snapshot1.getValue(LoanDataModel::class.java)
                        if(data!!.uid != FirebaseAuth.getInstance().uid){ // always true
                            dataList.add(data)
                        }
                    }

                    //update the RV adapter with the new userList if data changes
                    binding.userListRecyclerView.adapter = ClientStatusAdapter(this@ClientStatusScreenActivity,dataList)
                    dialog.dismiss()
                }

                override fun onCancelled(error: DatabaseError) {
                    dialog.dismiss()
                }

            })
    }


    private fun dialogBox(title:String,message:String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setTitle(title)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

}