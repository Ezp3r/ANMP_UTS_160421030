package com.ezper.advuts160421030.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ezper.advuts160421030.R
import com.ezper.advuts160421030.databinding.FragmentLoginBinding
import com.ezper.advuts160421030.databinding.FragmentProfileBinding
import java.time.LocalDateTime

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedFile = "com.ezper.advuts160421030"
        var shared: SharedPreferences = this.requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = shared.edit()
        binding.txtChangeFrontName.setText(shared.getString("nama_depan",""))
        binding.txtChangeBackName.setText(shared.getString("nama_belakang",""))
        binding.btnChange.setOnClickListener {
            val q = Volley.newRequestQueue(this.requireContext())
            val url_target = "http://10.0.2.2/news/update_user.php"
            val stringRequest = object: StringRequest(
                Request.Method.POST, url_target,
                {
                    Log.d("apiresult", it)
                    Toast.makeText(this.requireContext(), "Change Successful", Toast.LENGTH_SHORT).show()

                    //RESET
                    var editor: SharedPreferences.Editor=shared.edit()
                    editor.putString("nama_depan", binding.txtChangeFrontName.text.toString())
                    editor.putString("nama_belakang", binding.txtChangeBackName.text.toString())
                    editor.apply()

                },
                {
                    Log.e("apierror", it.printStackTrace().toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["nama_depan"] = binding.txtChangeFrontName.text.toString()
                    params["nama_belakang"] = binding.txtChangeBackName.text.toString()
                    params["password"] = binding.txtChangePassword.text.toString()
                    params["username"] = shared.getString("username","").toString()
                    return params
                }
            }
            q.add(stringRequest)
        }
        binding.btnLogout.setOnClickListener {
            var sharedFile = "com.ezper.advuts160421030"
            var shared: SharedPreferences = this.requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = shared.edit()
            editor.putString("username", "")
            editor.putString("nama_depan", "")
            editor.putString("nama_belakang", "")
            val intent = Intent(this.requireContext(), LoginActivity::class.java)
            editor.apply()
            startActivity(intent)
            this.requireActivity().finish()
        }
    }

}