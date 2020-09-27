package com.example.restaurantbook

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.example.restaurantbook.navigation.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.home -> {
                var fragment_home = fragment_home()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, fragment_home).commit()
                return true
            }
            R.id.category -> {
                var fragment_statistic = fragment_category()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, fragment_statistic).commit()
                return true
            }
            R.id.around-> {
                var fragment_around = fragment_around()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, fragment_around).commit()
                return true
            }
            R.id.favorite-> {
                var fragment_favorite = fragment_favorite()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, fragment_favorite).commit()
                return true
            }
            R.id.profile -> {
                var fragment_profile = fragment_profile()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, fragment_profile).commit()
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        bottom_navigation.selectedItemId = R.id.home

    }
}