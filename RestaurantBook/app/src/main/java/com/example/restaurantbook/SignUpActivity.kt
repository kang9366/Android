package com.example.restaurantbook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantbook.model.UserInformation_signUp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    //declear firestore

    var auth : FirebaseAuth? = null

    private val firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        //user information
        val userEmail = input_email.text.toString()
        val userPassword = input_password.text.toString()
        val userName = input_name.text.toString()
        val userBirth = input_birth.text.toString()
        val userTel = input_tel.text.toString()

        val userInformation = UserInformation_signUp(input_name.text.toString(),
            input_email.text.toString(), input_password.text.toString(), input_tel.text.toString(), input_birth.text.toString()
        )
        Log.d("information", input_birth.text.toString())
        //button click event
        btn_back.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        apply.setOnClickListener {
            if(input_email.text.toString() == ""){
                Toast.makeText(this@SignUpActivity, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
            }else if(input_password.text.toString() == ""){
                Toast.makeText(this@SignUpActivity, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }else if(input_name.text.toString() == ""){
                Toast.makeText(this@SignUpActivity, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            }else if(input_birth.text.toString() == ""){
                Toast.makeText(this@SignUpActivity, "생년월일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }else if(input_birth.text.toString().length < 8){
                Toast.makeText(this@SignUpActivity, "생년월일은 8자리로 입력해주세요", Toast.LENGTH_SHORT).show()
            }else{
                creatAccount()
                firestore.collection("user").document("user3").set(input_name.text.toString())
                println(userBirth)
            }
        }

        button2.setOnClickListener{
            textview2.setText(userEmail)
            Log.d("email", input_email.text.toString())
        }
    }

    fun creatAccount(){
        auth!!.createUserWithEmailAndPassword(input_email.text.toString(), input_password.text.toString()).addOnCompleteListener{ task ->
            if(task.isSuccessful && input_password.text.toString() == check_password.text.toString()){
                //creating a user account
                Toast.makeText(this, "회원가입 성공" + input_password.text.toString(), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }else if(input_password.text.toString() != check_password.text.toString()){
                //show the error message
                Toast.makeText(this@SignUpActivity, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}