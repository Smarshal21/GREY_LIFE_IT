package com.example.credit_risk_eval_badri_v01.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.credit_risk_eval_badri_v01.R
import com.example.credit_risk_eval_badri_v01.adapters.PersonalityAssessmentAdapter
import com.example.credit_risk_eval_badri_v01.models.PersonalityAssessmentQuestionModel
import com.example.credit_risk_eval_badri_v01.databinding.ActivityPersonalityAssessmentBinding
import com.example.credit_risk_eval_badri_v01.fragments.AssessmentDefaultFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class PersonalityAssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalityAssessmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsList: ArrayList<PersonalityAssessmentQuestionModel>
    private lateinit var questionsAdapter: PersonalityAssessmentAdapter
    lateinit var score:String

    val questions = arrayOf(
        arrayOf(
            "What is your utility bill payment history?",
            "Always on time",
            "Sometimes late",
            "Often late",
            "Never Paid",
            "null"
        ),
        arrayOf(
            "What is your mobile phone bill payment history?",
            "Always on time",
            "Sometimes late",
            "Often late",
            "Never Paid",
            "null"
        ),
        arrayOf(
            "Do you have a social media presence? If so, what platforms do you use and how often?",
            "Active on multiple platforms and post often",
            "Active on one platform and post often",
            "Active on one platform and post occasionally",
            "No social media presence",
            "null"
        ),
        arrayOf(
            "Do you have a bank account? If so, how long have you had it and what is your account balance?",
            "Have a bank account for over 5 years and have a positive balance",
            "Have a bank account for less than 5 years but have a positive balance",
            "Have a bank account but have a negative balance",
            "No bank account",
            "null"
        ),
        arrayOf(
            "Do you own a home or car? If so, how much equity do you have?",
            "Own a home or car with more than 20% equity",
            "Own a home or car with less than 20% equity",
            "Own a home or car but have no equity",
            "Do not own a home or car",
            "null"
        ),
        arrayOf(
            "Do you have a job? If so, how long have you been employed?",
            "Employed for more than 2 years",
            "Employed for 6 months to 2 years",
            "Employed for less tha 6 months",
            "Other (unemployed, student, retiree, etc.)",
            "null"
        ),
        arrayOf(
            "Do you have any outstanding debt? If so, what is the total amount?",
            "No outstanding debt",
            "Less than \$10,000 in outstanding debt:",
            "More than \$10,000 in outstanding debt",
            "I don't know",
            "null"
        ),
        arrayOf(
            "Have you ever filed for bankruptcy?",
            "No",
            "",
            "",
            "Yes",
            "null"
        ),
        arrayOf(
            "What is your educational background?",
            "College degree or higher",
            "High school diploma or equivalent",
            "Some college",
            "No high school diploma",
            "null"
        ),
        arrayOf(
            "What is your employment history?",
            "Stable employment history with no layoffs or firings",
            "Some employment history with some layoffs or firings:",
            "Short employment history or multiple job changes",
            "No employment history",
            "null"
        ),
        arrayOf(
            "What is your income?",
            "Over \$50,000",
            "\$30,000-\$50,000",
            "Less than \$30,000",
            "I don't know",
            "null"
        ),

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalityAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableRecyclerView()
        defaultFragmentDisplayed()
        nextBtn()
    }


    private fun enableRecyclerView(){
        recyclerView = binding.recyclerView
        questionsList = ArrayList()
        questionsList.add(PersonalityAssessmentQuestionModel("1"))
        questionsList.add(PersonalityAssessmentQuestionModel("2"))
        questionsList.add(PersonalityAssessmentQuestionModel("3"))
        questionsList.add(PersonalityAssessmentQuestionModel("4"))
        questionsList.add(PersonalityAssessmentQuestionModel("5"))
        questionsList.add(PersonalityAssessmentQuestionModel("6"))
        questionsList.add(PersonalityAssessmentQuestionModel("7"))
        questionsList.add(PersonalityAssessmentQuestionModel("8"))
        questionsList.add(PersonalityAssessmentQuestionModel("9"))
        questionsList.add(PersonalityAssessmentQuestionModel("10"))
        questionsList.add(PersonalityAssessmentQuestionModel("11"))


        questionsAdapter = PersonalityAssessmentAdapter(questionsList,this)
        recyclerView.adapter = questionsAdapter

    }

    private fun nextBtn(){

        binding.btnNext.setOnClickListener {
            var flag:Boolean = false
            for(i in 0..9){
                if(questions[i][5]=="null"){
                    flag = true
                    break
                }
            }
            if(flag){
//                Toast.makeText(this,"incomplete questions",Toast.LENGTH_SHORT).show()
                createSnackBar(binding.root, "incomplete questions","Try Again")
                binding.score.text = calculateAssessmentScore().toString()
            }
            else{
                Toast.makeText(this,"completed questions",Toast.LENGTH_SHORT).show()
                score= calculateAssessmentScore().toString()

                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("testScore", score)
                editor.apply()

                val intent = Intent(this,HomeScreenActivity::class.java)
//                intent.putExtra("testScore",score)
                startActivity(intent)


            }
        }

    }


    private fun calculateAssessmentScore():Int{
        var ans:Int= 0
        for(i in 0..questions.size-1){
            val x = questions[i][5]
            if(x=="0"){
                ans+=5
            }
            else if(x=="1"){
                ans+=3
            }
            else if(x=="2"){
                ans+=1
            }
            else if(x=="3"){
                ans+=0
            }
            else{
                ans+=0
            }
        }
        return ans
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

    private fun defaultFragmentDisplayed(){
        val fragmentContainer = binding.fragmentContainerView
        // Check if the fragment is already added to avoid adding it multiple times
        val defaultFragment = AssessmentDefaultFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(fragmentContainer.id, defaultFragment)
        transaction.commit()

    }




}