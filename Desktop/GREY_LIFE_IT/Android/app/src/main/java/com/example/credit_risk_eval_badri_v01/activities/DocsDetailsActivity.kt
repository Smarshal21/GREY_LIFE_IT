package com.example.credit_risk_eval_badri_v01.activities

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.credit_risk_eval_badri_v01.adapters.ChatAdapter
import com.example.credit_risk_eval_badri_v01.adapters.MessageAdapter
import com.example.credit_risk_eval_badri_v01.adapters.PdfAdapter
import com.example.credit_risk_eval_badri_v01.databinding.ActivityDocsDetailsBinding
import com.example.credit_risk_eval_badri_v01.interfaces.MyBlockchainApi
import com.example.credit_risk_eval_badri_v01.models.FileinModel
import com.example.credit_risk_eval_badri_v01.models.LoanDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DocsDetailsActivity : AppCompatActivity() {

    private lateinit var dialog:AlertDialog
    private lateinit var pdfList:ArrayList<FileinModel>

    private lateinit var binding:ActivityDocsDetailsBinding
    private lateinit var uid: String
    private lateinit var database: FirebaseDatabase
    private lateinit var myApi: MyBlockchainApi
    val USERNAME = "u0nbfzswwp"
    val PASSWORD = "7kw_tDTpsWWwyeOtSdJmOfj6179YXiiewyQN4WU7CGA"
//    val BASE2_URL = "https://u0ft62dsi9-u0bftvrkqx-rpc.us0aws.kaleido.io//gateways/testinggreylife/0xea3238eb802619629107e6e5f0fd00be0aa132bb/"
    val BASE2_URL = "https://u0ft62dsi9-u0oicgb1o0-connect.us0-aws.kaleido.io/gateways/testinggreylife/0xea3238eb802619629107e6e5f0fd00be0aa132bb/"
//    val kldFromValue2 = "0x0c7d6a7a583b790be7635bef63c9a65327d415d5"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uid = intent.getStringExtra("uid")!!
        database = FirebaseDatabase.getInstance()


        loadDataFormDatabase()

        displayPdfs()
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

    private fun getData() {
//        dialogBox("Fetching data form Blockchain","Please wait...")
//        RetrofitCreate()
//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                val response = myApi.getData(kldFromValue2).execute()
//                if (response.isSuccessful) {
//                    val responseData = response.body()
//                    val output:Array<String> = responseData?.output!!
//                    GlobalScope.launch(Dispatchers.Main) {
//                        runOnUiThread {
//                            binding.tvbc1.text = output[0]
//                            binding.tvbc2.text = output[1]
//                            binding.tvbc3.text = output[2]
//                            binding.tvbc4.text = output[3]
//                            binding.tvbc5.text = output[4]
//                            dialog.dismiss()
//                        }
//                    }
//                } else {
//                    GlobalScope.launch(Dispatchers.Main) {
//
//                        runOnUiThread {
//                            binding.tvTesting.text = ("Data retrieval failed")
//                            dialog.dismiss()
//                        }
//                    }
//                }
////                dialog.dismiss()
//            } catch (e: Exception) {
//                GlobalScope.launch(Dispatchers.Main) {
//                    runOnUiThread {
//                        binding.tvTesting.text = ("Error: ${e.message}")
//                        dialog.dismiss()
//                    }
//                }
//            }
//        }
//        dialog.dismiss()
    }

    private fun dialogBox(title:String,message:String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setTitle(title)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    private fun loadDataFormDatabase(){
        database.reference.child("loans")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dialogBox("Fetching data from database","please wait...")
                    for(snapshot1 in snapshot.children){
                        val data = snapshot1.getValue(LoanDataModel::class.java)
                        if(data!!.uid == uid){
                            binding.tv10.text = data.name
                            binding.tv11.text = data.uid
                            binding.tv12.text = data.loanType
                            binding.tv1.text = data.noOfDependents
                            binding.tv9.text = data.education
                            binding.tv15.text = data.sritscore
                            binding.tv2.text = data.etIncomeeAnnum
                            binding.tv16.text = data.etLoanAmount
                            binding.tv3.text = data.etLoanTerm
                            binding.tv4.text = data.etCIBILScore
                            binding.tv5.text = data.etResedentialAssetsValie
                            binding.tv6.text = data.etCommercialAssetsValue
                            binding.tv7.text = data.etLuxuryAssetsValue
                            binding.tv8.text = data.etBankAsssetsValue
                            binding.tv13.text = data.mlOutput
                            binding.tv14.text = data.email
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

    private fun displayPdfs() {
        pdfList = ArrayList()
        database.reference.child("pdfs")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    pdfList.clear()
                    for(snapshot1 in snapshot.children){
                        for(data in snapshot1.children){
                            val user = data.getValue(FileinModel::class.java)
                            if(user!!.uid == uid)
                            pdfList.add(user!!)
                        }
                    }


                    binding.tvPdf1.text = pdfList[0].filename
                    binding.tvPdf2.text = pdfList[1].filename
                    binding.tvUrl1.text = pdfList[0].fileurl
                    binding.tvUrl2.text = pdfList[1].fileurl
                    binding.recyclerView.adapter = PdfAdapter(this@DocsDetailsActivity,pdfList)

                }

                override fun onCancelled(error: DatabaseError) {
                    dialog.dismiss()
                }
            })
    }




}