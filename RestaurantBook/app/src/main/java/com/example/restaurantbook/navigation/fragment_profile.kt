package com.example.restaurantbook.navigation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.restaurantbook.LoginActivity
import com.example.restaurantbook.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.view.*

class fragment_profile : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_profile, container, false)
        view.logout.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val dialogView = layoutInflater.inflate(R.layout.alert_popup, null)
            builder.setView(dialogView).setNegativeButton("로그아웃"){ dialogInterface, i ->
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(context, "로그아웃되었습니다", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, LoginActivity::class.java))
            }.setPositiveButton("취소", null).show()
        }
        return view
    }
}