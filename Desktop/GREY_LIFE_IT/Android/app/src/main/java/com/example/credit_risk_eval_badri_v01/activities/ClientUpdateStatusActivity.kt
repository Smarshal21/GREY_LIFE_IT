package com.example.credit_risk_eval_badri_v01.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.databinding.ActivityClientUpdateStatusBinding
import com.example.credit_risk_eval_badri_v01.interfaces.MyBlockchainApi
import com.example.credit_risk_eval_badri_v01.models.LoanDataModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientUpdateStatusActivity : AppCompatActivity() {

    private lateinit var binding:ActivityClientUpdateStatusBinding
    private lateinit var database: FirebaseDatabase

    val USERNAME = "u0nbfzswwp"
    val PASSWORD = "7kw_tDTpsWWwyeOtSdJmOfj6179YXiiewyQN4WU7CGA"
    val BASE2_URL = "https://u0ft62dsi9-u0oicgb1o0-connect.us0-aws.kaleido.io/gateways/testinggreylife/0xea3238eb802619629107e6e5f0fd00be0aa132bb/"
    val kldFromValue2 = "0x0c7d6a7a583b790be7635bef63c9a65327d415d5"
    private lateinit var myApi: MyBlockchainApi


    private lateinit var name:String
    private lateinit var uid:String
    private lateinit var email:String
    private lateinit var loanType:String
    private lateinit var noOfDependents:String
    private lateinit var education:String
    private lateinit var sritscore:String
    private lateinit var etIncomeeAnnum:String
    private lateinit var etLoanAmount:String
    private lateinit var etLoanTerm:String
    private lateinit var etCIBILScore:String
    private lateinit var etResedentialAssetsValie:String
    private lateinit var etCommercialAssetsValue:String
    private lateinit var etLuxuryAssetsValue:String
    private lateinit var etBankAsssetsValue:String
    private lateinit var mlOutput:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientUpdateStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayCurrentResultFromDatabase()
        binding.btnUpdate.setOnClickListener {
            if(binding.etUpdateResult.text.isNullOrEmpty()){

            }
            else{
                mlOutput = binding.etUpdateResult.text.toString()
                updateLLoanDataInDatabase()
                postData()
            }
        }

    }

    private fun displayCurrentResultFromDatabase(){
        database = FirebaseDatabase.getInstance()
        uid = intent.getStringExtra("uid")!!
        database.reference.child("loans")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(snapshot1 in snapshot.children){
                        val data = snapshot1.getValue(LoanDataModel::class.java)
                        if(data!!.uid == uid){
                            name = data.name!!
                            email = data.email!!
                            loanType = data.loanType!!
                            noOfDependents = data.noOfDependents!!
                            education = data.education!!
                            sritscore = data.sritscore!!
                            etIncomeeAnnum = data.etIncomeeAnnum!!
                            etCIBILScore = data.etCIBILScore!!
                            etBankAsssetsValue = data.etBankAsssetsValue!!
                            etLoanTerm = data.etLoanTerm!!
                            etResedentialAssetsValie = data.etResedentialAssetsValie!!
                            etLuxuryAssetsValue = data.etLuxuryAssetsValue!!
                            etLoanAmount = data.etLoanAmount!!
                            etCommercialAssetsValue = data.etCommercialAssetsValue!!
                            mlOutput = data.mlOutput!!

                            binding.tvCurrentResult.text =mlOutput
                            if(mlOutput=="Accepted"){
                                binding.tvCurrentResult.setBackgroundColor(ContextCompat.getColor(this@ClientUpdateStatusActivity, R.color.myGreen))
                            }
                            else if(mlOutput == "Declined"){
                                binding.tvCurrentResult.setBackgroundColor(ContextCompat.getColor(this@ClientUpdateStatusActivity, R.color.myRed))
                            }
                            else{
                                binding.tvCurrentResult.setBackgroundColor(ContextCompat.getColor(this@ClientUpdateStatusActivity, R.color.myGrey))
                            }
                            break
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun updateLLoanDataInDatabase(){
        if(binding.etUpdateResult.text.isNullOrEmpty()){
            //do nothing
        }
        else{
            var loanData = LoanDataModel(
                name,
                uid,
                email,
                loanType,
                noOfDependents,
                education,
                sritscore,
                etIncomeeAnnum,
                etLoanAmount,
                etLoanTerm,
                etCIBILScore,
                etResedentialAssetsValie,
                etCommercialAssetsValue,
                etLuxuryAssetsValue,
                etBankAsssetsValue,
                binding.etUpdateResult.text.toString()
            )
//            dialogBox("Updating data in Database","Please wait...")
            database.reference.child("loans")
                .child(uid)
                .setValue(loanData)
                .addOnSuccessListener {
//                                startActivity(Intent(this@SignupActivity, MainActivity::class.java))
//                                finish()
//                    dialog.dismiss()
                }
                .addOnFailureListener {
//                createSnackBar(binding.root,"failed to upload data to Realtime DB","Try Again")
//                    dialog.dismiss()
                }

        }

    }

    private fun RetrofitCreate() {
        val credentials = Credentials.basic(
            USERNAME,
            PASSWORD
        )
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE2_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                //-----------------------
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                            .header("Authorization", credentials)
                            .build()
                        chain.proceed(newRequest)
                    }
                    .build()
                //------------------------
            )
            .build()

        myApi = retrofit.create(MyBlockchainApi::class.java)
    }

    private fun postData() {
        RetrofitCreate()
        getOutputFromML()
        val inputData:List<String> = listOf(
            loanType,
            etCIBILScore,
            sritscore,
            mlOutput,
            uid
        )
        val requestData = MyBlockchainApi.RequestData(inputData)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = myApi.PostData(kldFromValue2, requestData).execute()
//                dialogBox("Adding updated data to the blockchain","Please wait...")
                if (response.isSuccessful) {
                    GlobalScope.launch(Dispatchers.Main) {
//                        dialog.dismiss()
                        Toast.makeText(this@ClientUpdateStatusActivity, "new data added to blockchain", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    GlobalScope.launch(Dispatchers.Main) {
//                        dialog.dismiss()
                        Toast.makeText(this@ClientUpdateStatusActivity, "Failed to add data to blockchain", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {
                GlobalScope.launch(Dispatchers.Main) {
//                    dialog.dismiss()
                    Toast.makeText(this@ClientUpdateStatusActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getOutputFromML(){
        mlOutput = binding.etUpdateResult.text.toString()
    }


}