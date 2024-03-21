package com.example.credit_risk_eval_badri_v01.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.credit_risk_eval_badri_v01.MainActivity
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.databinding.ActivityLoginBinding
import com.example.credit_risk_eval_badri_v01.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            btnLoginClick()
        }

        binding.tvSignup.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
//        dialogBox("Retrieving Lender? Status Data from FB Realtime DB","Please Wait ...")
//        private lateinit var dialog: AlertDialog
//        private fun dialogBox(title:String,message:String){
//            val builder = AlertDialog.Builder(this)
//            builder.setMessage(message)
//            builder.setTitle(title)
//            builder.setCancelable(false)
//            dialog = builder.create()
//            dialog.show()
//        }


    }

    private fun btnLoginClick(){
        dialogBox("Logging in","Please Wait ...")
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        if(email.isNotEmpty() && pass.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                if(it.isSuccessful){
                    dialog.dismiss()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    dialog.dismiss()
                    createSnackBar(binding.root, "Error "+it.exception.toString(),"Try Again")
                }
            }
        }
        else{
            dialog.dismiss()
            createSnackBar(binding.root, "empty fields not allowed !","Try Again")
        }
    }

    private fun createSnackBar(view: View, text: String, actionText:String){
        Snackbar.make(view,text, Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .setBackgroundTint(Color.parseColor("#FF9494"))
            .setTextColor(Color.parseColor("#EE4B28"))
            .setActionTextColor(Color.parseColor("#000000"))
            .setAction(actionText){
//                Toast.makeText(this,"snackbar button pressed",Toast.LENGTH_SHORT).show()
            }
            .show()
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