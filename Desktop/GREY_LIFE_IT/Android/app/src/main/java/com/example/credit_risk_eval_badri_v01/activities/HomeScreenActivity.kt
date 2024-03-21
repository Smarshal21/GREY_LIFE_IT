package com.example.credit_risk_eval_badri_v01.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.credit_risk_eval_badri_v01.MainActivity
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.adapters.LoanTypeAdapter
import com.example.credit_risk_eval_badri_v01.models.LoanTypeModel
import com.example.credit_risk_eval_badri_v01.databinding.ActivityHomeScreenBinding
import com.example.credit_risk_eval_badri_v01.fragments.AssessmentDefaultFragment
import com.example.credit_risk_eval_badri_v01.fragments.LoanDefaultFragment
import com.example.credit_risk_eval_badri_v01.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var loanTypeAdapter: LoanTypeAdapter
    private lateinit var loanTypeList: ArrayList<LoanTypeModel>
    private lateinit var dialog:AlertDialog
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        defaultFragmentDisplayed()
        enableBottomNavView()
        enableRecyclerView()
        getNameFromDatabase()

        binding.btnLogout.setOnClickListener {
            logout()
        }

        binding.srit.setOnClickListener {
            startActivity(Intent(this, PersonalityAssessmentActivity::class.java))
        }


//        score = intent.getStringExtra("testScore")!!
//        binding.tvTestScore.text = score

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val score = sharedPreferences.getString("testScore","")
//        binding.tvTestScore.text = score
        if(score!!.toInt()>=29.5){
            binding.tvTestScore.text = score!! +" good"
        }
        else{
            binding.tvTestScore.text = score!!+ " bad"
        }
    }


    private fun enableRecyclerView(){
        recyclerView = binding.recyclerView
        loanTypeList = ArrayList()
        loanTypeList.add(LoanTypeModel("Tractor", R.drawable.ic_tractor))
        loanTypeList.add(LoanTypeModel("2-Wheeler", R.drawable.ic_scooter))
        loanTypeList.add(LoanTypeModel("Used Cars", R.drawable.ic_car))
        loanTypeList.add(LoanTypeModel("3-Wheeler", R.drawable.ic_auto))
        loanTypeAdapter = LoanTypeAdapter(loanTypeList,this)
        recyclerView.adapter = loanTypeAdapter

    }

    private fun enableBottomNavView(){
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setSelectedItemId(R.id.homeScreen)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.statusScreen -> {
                    startActivity(Intent(applicationContext, StatusScreenActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.homeScreen -> true
                R.id.supportScreen -> {
                    startActivity(Intent(applicationContext, SupportScreenActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.notificationsScreen -> {
                    startActivity(Intent(applicationContext, NotificationsScreenActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    private fun getNameFromDatabase(){
        database = FirebaseDatabase.getInstance()
        dialogBox("Retrieving Data from FB Realtime DB","Please Wait ...")
        database.reference.child("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(snapshot1 in snapshot.children){
                        val user = snapshot1.getValue(UserModel::class.java)
                        if(user!!.uid == FirebaseAuth.getInstance().uid){
                            binding.tvUsername.text = user.name
                            dialog.dismiss()
                            break
                        }
                    }

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

    private fun defaultFragmentDisplayed(){
        val fragmentContainer = binding.fragmentContainerView
        // Check if the fragment is already added to avoid adding it multiple times
        val defaultFragment = LoanDefaultFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(fragmentContainer.id, defaultFragment)
        transaction.commit()
    }


}