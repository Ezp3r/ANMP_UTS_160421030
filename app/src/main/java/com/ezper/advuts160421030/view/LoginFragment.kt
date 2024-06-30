package com.ezper.advuts160421030.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ezper.advuts160421030.R
import com.ezper.advuts160421030.databinding.ActivityLoginBinding
import com.ezper.advuts160421030.databinding.FragmentLoginBinding
import com.ezper.advuts160421030.model.User
import com.ezper.advuts160421030.viewmodel.UserViewModel
import org.json.JSONObject

class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
//        return inflater.inflate(R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            val alert = AlertDialog.Builder(activity)
            alert.setTitle("Konfirmasi")
            alert.setMessage("Apakah anda ingin melakukan login dengan akun ini?")
            alert.setPositiveButton("Login", DialogInterface.OnClickListener { dialog, which ->
                viewModel.login(username, password)
            })
            alert.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
            })
            alert.create().show()
        }

        binding.btnCreateAcc.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragment2ToRegisterFragment2()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.userLD.observe(viewLifecycleOwner, Observer {
            val alert = AlertDialog.Builder(activity)
            if (it != null) {
                alert.setTitle("Informasi")
                alert.setMessage("Anda berhasil login. Selamat datang ${it.nama_depan} ${it.nama_belakang}")
                alert.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    val sharedPref = activity?.packageName
                    val shared: SharedPreferences = requireActivity().getSharedPreferences(sharedPref, Context.MODE_PRIVATE)
                    val editor = shared.edit()
                    editor.putInt("KEY_ID", it.uuid)
                    editor.apply()

                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                })
            } else {
                alert.setMessage("Login Gagal")
                alert.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                })
            }
            alert.create().show()
        })
    }

}