package com.example.credit_risk_eval_badri_v01.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.credit_risk_eval_badri_v01.MainActivity
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.adapters.DocsAdapter
import com.example.credit_risk_eval_badri_v01.databinding.ActivityClientDocumentsScreenBinding
import com.example.credit_risk_eval_badri_v01.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ClientDocumentsScreenActivity : AppCompatActivity() {

    private lateinit var binding:ActivityClientDocumentsScreenBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var userList : ArrayList<UserModel>
    private lateinit var dialog: AlertDialog
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientDocumentsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableBottomNavView()
        getUserListFromDatabaseAndSetToRecyclerView()

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun enableBottomNavView(){
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setSelectedItemId(R.id.documentsScreen)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.statusScreen -> {
                    startActivity(Intent(applicationContext, ClientStatusScreenActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.documentsScreen -> true
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
        userList = ArrayList()
        dialogBox("Fetching borrowers list","Please Wait ...")
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
                    binding.userListRecyclerView.adapter = DocsAdapter(this@ClientDocumentsScreenActivity,userList)
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

    private fun logout(){
        auth = Firebase.auth
        Toast.makeText(applicationContext,"Logged out", Toast.LENGTH_SHORT).show()
        auth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}