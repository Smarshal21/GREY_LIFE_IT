package com.example.credit_risk_eval_badri_v01.models

data class LoanDataModel(
    val name:String? = null,
    val uid:String? = null,
    val email:String? = null,
    val loanType:String? = null,
    val noOfDependents:String? = null,
    val education:String? = null,
    val sritscore:String?=null,
    val etIncomeeAnnum:String? = null,
    val etLoanAmount:String? = null,
    val etLoanTerm:String? = null,
    val etCIBILScore:String? = null,
    val etResedentialAssetsValie:String? = null,
    val etCommercialAssetsValue:String? = null,
    val etLuxuryAssetsValue:String? = null,
    val etBankAsssetsValue:String? = null,
    val mlOutput:String? = null,
)
