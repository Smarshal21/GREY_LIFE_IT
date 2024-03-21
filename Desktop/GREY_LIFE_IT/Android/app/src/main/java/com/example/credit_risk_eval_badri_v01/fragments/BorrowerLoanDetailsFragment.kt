package com.example.credit_risk_eval_badri_v01.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Identity
import android.provider.OpenableColumns
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.interfaces.MyBlockchainApi
import com.example.credit_risk_eval_badri_v01.interfaces.MyMLApi
import com.example.credit_risk_eval_badri_v01.models.FileinModel
import com.example.credit_risk_eval_badri_v01.models.LoanDataModel
import com.example.credit_risk_eval_badri_v01.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class BorrowerLoanDetailsFragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var apiService: MyMLApi
    private lateinit var dialog:AlertDialog

    private lateinit var et1:EditText
    private lateinit var et2:EditText
    private lateinit var et3:EditText
    private lateinit var et4:EditText
    private lateinit var et5:EditText
    private lateinit var et6:EditText
    private lateinit var et7:EditText
    private lateinit var et8:EditText
    private lateinit var et9:EditText
    private lateinit var et10:EditText
    private lateinit var userName:String
    private lateinit var uid:String
    private lateinit var email:String
    private lateinit var sritScore:String
    private lateinit var loanType:String
    private lateinit var mlOutput:String



    private lateinit var etSelectPdf1:EditText
    private lateinit var etSelectPdf2:EditText
    private lateinit var etSelectPdf3:EditText
    private lateinit var etSelectPdf4:EditText

    val USERNAME = "u0nbfzswwp"
    val PASSWORD = "7kw_tDTpsWWwyeOtSdJmOfj6179YXiiewyQN4WU7CGA"
    val BASE2_URL = "https://u0ft62dsi9-u0oicgb1o0-connect.us0-aws.kaleido.io/gateways/testinggreylife/0xea3238eb802619629107e6e5f0fd00be0aa132bb/"
    val kldFromValue2 = "0x0c7d6a7a583b790be7635bef63c9a65327d415d5"
    private lateinit var myApi: MyBlockchainApi


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_borrower_loan_details, container, false)
        et1= view.findViewById(R.id.etNoOfDependents)
        et2 = view.findViewById(R.id.etEducation)
        et3 = view.findViewById(R.id.etIncomeAnnum)
        et4 = view.findViewById(R.id.etLoanAmount)
        et5 = view.findViewById(R.id.etLoanTerm)
        et6 = view.findViewById(R.id.etCIBILScore)
        et7 = view.findViewById(R.id.etResedentialAssetsValie)
        et8 = view.findViewById(R.id.etCommercialAssetsValue)
        et9 = view.findViewById(R.id.etLuxuryAssetsValue)
        et10 = view.findViewById(R.id.etBankAsssetsValue)
        val btnNext:Button = view.findViewById(R.id.btnSubmit)
        val tvLoanType:TextView = view.findViewById(R.id.tvLoanType)
        etSelectPdf1 = view.findViewById(R.id.etSelectPdf1)
        etSelectPdf2 = view.findViewById(R.id.etSelectPdf2)
        etSelectPdf3 = view.findViewById(R.id.etSelectPdf3)
        etSelectPdf4 = view.findViewById(R.id.etSelectPdf4)
        //--------------------
        initializeData()
        //---------------------
        etSelectPdf1.isFocusable = false
        etSelectPdf1.isFocusableInTouchMode = false
        etSelectPdf1.inputType = InputType.TYPE_NULL

        etSelectPdf2.isFocusable = false
        etSelectPdf2.isFocusableInTouchMode = false
        etSelectPdf2.inputType = InputType.TYPE_NULL

        etSelectPdf3.isFocusable = false
        etSelectPdf3.isFocusableInTouchMode = false
        etSelectPdf3.inputType = InputType.TYPE_NULL

        etSelectPdf4.isFocusable = false
        etSelectPdf4.isFocusableInTouchMode = false
        etSelectPdf4.inputType = InputType.TYPE_NULL

//        val color = ContextCompat.getColor(requireContext(), R.color.myYellow)
//        if(etSelectPdf1.text.toString() != ""){
//            etSelectPdf1.setBackgroundColor(color)
//            etSelectPdf1.setTextColor(color)
//        }
//
//        if(etSelectPdf2.text.toString() != null){
//            etSelectPdf2.setBackgroundColor(color)
//            etSelectPdf2.setTextColor(color)
//        }
//
//        if(etSelectPdf3.text.toString() != null){
//            etSelectPdf3.setBackgroundColor(color)
//            etSelectPdf3.setTextColor(color)
//        }
//
//        if(etSelectPdf4.text.toString() != null){
//            etSelectPdf4.setBackgroundColor(color)
//            etSelectPdf4.setTextColor(color)
//        }
        //------------
        etSelectPdf1.setOnClickListener {
            selectPDF(101)
        }
        etSelectPdf2.setOnClickListener {
            selectPDF(102)

        }
        etSelectPdf3.setOnClickListener {
            selectPDF(103)

        }
        etSelectPdf4.setOnClickListener {
            selectPDF(104)

        }
        //------------

        tvLoanType.text = loanType
        btnNext.setOnClickListener {
            if(et1.text.isNullOrEmpty()|| et2.text.isNullOrEmpty()|| et3.text.isNullOrEmpty()|| et4.text.isNullOrEmpty()|| et5.text.isNullOrEmpty()|| et6.text.isNullOrEmpty()|| et7.text.isNullOrEmpty()|| et8.text.isNullOrEmpty()|| et9.text.isNullOrEmpty()|| et10.text.isNullOrEmpty() ||
                etSelectPdf1.text.isNullOrEmpty() || etSelectPdf2.text.isNullOrEmpty() || etSelectPdf3.text.isNullOrEmpty() || etSelectPdf4.text.isNullOrEmpty()
            ){
                Toast.makeText(requireContext(),"fill all details",Toast.LENGTH_SHORT).show()
            }
            else{
                getOutputFromML()
//                saveLoanDetailsInDatabase()
//                postData()
            }
        }
        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            when (requestCode) {
                101 -> {
                    // Handle requestCode 101 (pdf1)
                    val uri = data.data
                    val uriString = uri.toString()
                    val myFile = File(uriString)
                    val path = myFile.absolutePath
                    var displayName: String? = null
                    if (uriString.startsWith("content://")) {
                        var cursor: Cursor? = null
                        try {
                            cursor = requireContext().contentResolver.query(
                                uri!!,
                                null,
                                null,
                                null,
                                null
                            )
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName =
                                    cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            }
                        } finally {
                            cursor?.close()
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.name
                    }
                    etSelectPdf1.setText(displayName)
                    uploadPDF(data.data!!, "pdf1",etSelectPdf1)
                }

                102 -> {
                    // Handle requestCode 102 (pdf2)
                    val uri = data.data
                    val uriString = uri.toString()
                    val myFile = File(uriString)
                    val path = myFile.absolutePath
                    var displayName: String? = null
                    if (uriString.startsWith("content://")) {
                        var cursor: Cursor? = null
                        try {
                            cursor = requireContext().contentResolver.query(
                                uri!!,
                                null,
                                null,
                                null,
                                null
                            )
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName =
                                    cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            }
                        } finally {
                            cursor?.close()
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.name
                    }
                    etSelectPdf2.setText(displayName)
                    uploadPDF(data.data!!, "pdf2",etSelectPdf2)
                }

                103 -> {
                    // Handle requestCode 102 (pdf2)
                    val uri = data.data
                    val uriString = uri.toString()
                    val myFile = File(uriString)
                    val path = myFile.absolutePath
                    var displayName: String? = null
                    if (uriString.startsWith("content://")) {
                        var cursor: Cursor? = null
                        try {
                            cursor = requireContext().contentResolver.query(
                                uri!!,
                                null,
                                null,
                                null,
                                null
                            )
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName =
                                    cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            }
                        } finally {
                            cursor?.close()
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.name
                    }
                    etSelectPdf3.setText(displayName)
                    uploadPDF(data.data!!, "pdf3",etSelectPdf3)
                }

                104 -> {
                    // Handle requestCode 102 (pdf2)
                    val uri = data.data
                    val uriString = uri.toString()
                    val myFile = File(uriString)
                    val path = myFile.absolutePath
                    var displayName: String? = null
                    if (uriString.startsWith("content://")) {
                        var cursor: Cursor? = null
                        try {
                            cursor = requireContext().contentResolver.query(
                                uri!!,
                                null,
                                null,
                                null,
                                null
                            )
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName =
                                    cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            }
                        } finally {
                            cursor?.close()
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.name
                    }
                    etSelectPdf4.setText(displayName)
                    uploadPDF(data.data!!, "pdf4",etSelectPdf4)
                }
            }
        }
    }


    private fun uploadPDF(data: Uri, docType:String,et:EditText) {
        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().getReference("pdfs")

        val pd = ProgressDialog(requireContext())
        pd.setTitle("File Uploading..")
        pd.show()

        val reference = storageReference.child("uploads/" + System.currentTimeMillis() + ".pdf")
        // store in upload folder of the Firebase storage
        // store in upload folder of the Firebase storage
        reference.putFile(data)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl
                while (!uriTask.isComplete);
                val uri = uriTask.result
                val fileinModel =
                    FileinModel(
                        et.getText().toString(),
                        uri.toString(),
                        FirebaseAuth.getInstance().uid.toString()
                    ) //get the views from the model class
                databaseReference
                    .child(FirebaseAuth.getInstance().uid.toString())
//                    .child(databaseReference.push().getKey()!!)
                    .child(docType)
                    .setValue(fileinModel) // push the value into the realtime database
                Toast.makeText(
                    requireContext(),
                    "File Uploaded Successfully!!",
                    Toast.LENGTH_SHORT
                ).show()
                pd.dismiss()
            }.addOnProgressListener { snapshot ->
                val percent = (100 * snapshot.bytesTransferred / snapshot.totalByteCount).toFloat()
                pd.setMessage("Uploaded : " + percent.toInt() + "%")
            }
    }

    private fun selectPDF(requestCode: Int){
//        if(requestCode == 101){
//            val color = ContextCompat.getColor(requireContext(), R.color.myYellow)
//            etSelectPdf1.setBackgroundColor(color)
//            etSelectPdf1.setTextColor(color)
//        }
//        else if(requestCode == 102){
//            val color = ContextCompat.getColor(requireContext(), R.color.myYellow)
//            etSelectPdf2.setBackgroundColor(color)
//            etSelectPdf2.setTextColor(color)
//        }
//        else if(requestCode == 103){
//            val color = ContextCompat.getColor(requireContext(), R.color.myYellow)
//            etSelectPdf3.setBackgroundColor(color)
//            etSelectPdf3.setTextColor(color)
//        }
//        else if(requestCode == 104){
//            val color = ContextCompat.getColor(requireContext(), R.color.myYellow)
//            etSelectPdf4.setBackgroundColor(color)
//            etSelectPdf4.setTextColor(color)
//        }
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Pdf files"), requestCode)
    }

    private fun initializeData(){
        loanType = arguments?.getString("loanType")!!
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val data = sharedPreferences.getString("testScore","")!!.toInt()
        if(data>=29.5) sritScore = "1"
        else sritScore = "0"

        database = FirebaseDatabase.getInstance()
        uid= FirebaseAuth.getInstance().uid.toString()
        database.reference.child("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(snapshot1 in snapshot.children){
                        val user = snapshot1.getValue(UserModel::class.java)
                        if(user!!.uid == uid){
                            userName = user.name!!
                            email = user.email!!
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun saveLoanDetailsInDatabase(mlOutput:String){
        dialogBox("Saving Data in Database","Please wait")
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val scoreReceived = sharedPreferences.getString("testScore","")!!
        var loanData = LoanDataModel(userName, uid, email, loanType, et1.text.toString(), et2.text.toString(), scoreReceived, et3.text.toString(), et4.text.toString(), et5.text.toString(), et6.text.toString(), et7.text.toString(), et8.text.toString(), et9.text.toString(), et10.text.toString(), mlOutput)
        database.reference.child("loans")
            .child(uid)
            .setValue(loanData)
            .addOnSuccessListener {
                dialog.dismiss()
            }
            .addOnFailureListener {
                dialog.dismiss()
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
            )
            .build()

        myApi = retrofit.create(MyBlockchainApi::class.java)
    }

    private fun postData(mlOutput:String) {
        RetrofitCreate()
        val inputData:List<String> = listOf(
            loanType,
            et6.text.toString(),
            sritScore,
            mlOutput,
            uid
        )
        val requestData = MyBlockchainApi.RequestData(inputData)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = myApi.PostData(kldFromValue2, requestData).execute()

                if (response.isSuccessful) {
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "blockchain transaction successful", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "blockchain transaction failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getOutputFromML(){
//        mlOutput = "Ml output by Badri"
        val retrofit = Retrofit.Builder()
            .baseUrl("https://greylifeapi-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(MyMLApi::class.java)
        val request = MyMLApi.MyRequest(
            listOf(
                et1.text.toString(),
                et3.text.toString(),
                et4.text.toString(),
                et5.text.toString(),
                et6.text.toString(),
                et7.text.toString(),
                et8.text.toString(),
                et9.text.toString(),
                et10.text.toString(),
                sritScore
            )
        )

        apiService.postData(request).enqueue(object : Callback<MyMLApi.MyResponse> {
            override fun onResponse(
                call: Call<MyMLApi.MyResponse>,
                response: Response<MyMLApi.MyResponse>
            ) {
                if (response.isSuccessful) {
                    val myResponse = response.body()
                    if (myResponse != null) {
//                        resultTextView.text = "Prediction: ${myResponse.prediction}"
                        val statusFromML = myResponse.prediction.toString()
                        if(statusFromML == "1"){
                            mlOutput = "Accepted"
                            saveLoanDetailsInDatabase(mlOutput)
                            postData(mlOutput)
                        }
                        else if(statusFromML == "0"){
                            mlOutput = "Declined"
                            saveLoanDetailsInDatabase(mlOutput)
                            postData(mlOutput)
                        }
                        else{
                            mlOutput = "Error from Ml"
                            saveLoanDetailsInDatabase(mlOutput)
                            postData(mlOutput)
                        }
                    }

                } else {
//                    resultTextView.text = "Error: ${response.code()}"
                    mlOutput = "Error from Ml"
                    saveLoanDetailsInDatabase(mlOutput)
                    postData(mlOutput)
                }
            }

            override fun onFailure(call: Call<MyMLApi.MyResponse>, t: Throwable) {
//                resultTextView.text = "Network Error: ${t.message}"
                mlOutput = "Error from Ml"
                saveLoanDetailsInDatabase(mlOutput)
                postData(mlOutput)
            }
        })
    }

    private fun dialogBox(title:String,message:String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
        builder.setTitle(title)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

}