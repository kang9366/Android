package com.example.restaurantbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.restaurantbook.model.UserInformation_signUp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_get_information.*

class GetInformationActivity : AppCompatActivity() {
    var firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_information)
        var user_number = spinner_nubmer.selectedItem.toString()
        var user_gender = spinner_gender.selectedItem.toString()
        var user_major = spinner_major.selectedItem.toString()
        val information = UserInformation_signUp("강우구")
        submit.setOnClickListener {
            firestore.collection("user_info").document("user12").set(information)
            moveToMain()
        }
    }

    fun moveToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}