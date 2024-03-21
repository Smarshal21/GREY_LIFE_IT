package com.example.credit_risk_eval_badri_v01.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.databinding.ActivityClientSupportScreenBinding
import com.example.credit_risk_eval_badri_v01.adapters.ChatAdapter
import com.example.credit_risk_eval_badri_v01.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClientSupportScreenActivity : AppCompatActivity() {

    private lateinit var binding:ActivityClientSupportScreenBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var userList : ArrayList<UserModel>
    private lateinit var dialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientSupportScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableBottomNavView()
        getUserListFromDatabaseAndSetToRecyclerView()


    }

    private fun enableBottomNavView(){
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setSelectedItemId(R.id.supportScreen)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.documentsScreen -> {
                    startActivity(Intent(applicationContext, ClientDocumentsScreenActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.supportScreen -> true
                R.id.statusScreen -> {
                    startActivity(Intent(applicationContext, ClientStatusScreenActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    private fun getUserListFromDatabaseAndSetToRecyclerView(){
        dialogBox("Fetching borrower list","Please wait...")
        database = FirebaseDatabase.getInstance()
        userList = ArrayList()

        database.reference.child("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for(snapshot1 in snapshot.children){
                        val user = snapshot1.getValue(UserModel::class.java)
                        if(user!!.uid != FirebaseAuth.getInstance().uid){
                            userList.add(user)
                        }
                    }

                    //update the RV adapter with the new userList if data changes
                    binding.userListRecyclerView.adapter = ChatAdapter(this@ClientSupportScreenActivity,userList)
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