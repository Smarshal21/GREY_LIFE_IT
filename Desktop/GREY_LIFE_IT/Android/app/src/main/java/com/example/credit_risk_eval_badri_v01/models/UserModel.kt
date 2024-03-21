package com.example.credit_risk_eval_badri_v01.models

data class UserModel(
    val uid : String? = "",
    val name:String? = "",
    val email:String? = "",
    val lender:String? = "",
    val links:Int? = 0,
    val partner:String? = ""
)
