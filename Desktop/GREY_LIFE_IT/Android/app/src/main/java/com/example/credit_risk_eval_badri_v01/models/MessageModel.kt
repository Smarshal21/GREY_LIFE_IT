package com.example.credit_risk_eval_badri_v01.models

data class MessageModel(
    var message:String? = "",
    var senderId:String? = "",
    var timeStamp:Long? = 0
){
    //time stamp is not necessary
}