package com.ezper.advuts160421030.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ezper.advuts160421030.R
import com.ezper.advuts160421030.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var navController: NavController

    companion object {
        fun getSharedPref(activity: Activity): Int {
            val shared = activity.packageName
            val sharedPref: SharedPreferences = activity.getSharedPreferences(shared, Context.MODE_PRIVATE)
            val res = sharedPref.getInt("KEY_ID", 0)
            Log.d("cek", res.toString())

            return res
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        navController = (supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment).navController
        val toolbar = Toolbar(applicationContext)
//        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController)

        if (getSharedPref(this) != 0) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}