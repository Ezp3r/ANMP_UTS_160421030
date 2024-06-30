package com.ezper.advuts160421030.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ezper.advuts160421030.R
import com.ezper.advuts160421030.databinding.FragmentLoginBinding
import com.ezper.advuts160421030.databinding.FragmentRegisterBinding
import com.ezper.advuts160421030.model.User
import com.ezper.advuts160421030.viewmodel.UserViewModel


class RegisterFragment : Fragment() {
    private lateinit var binding:FragmentRegisterBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_register, container, false)
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            val username = binding.txtUsernameReg.text.toString()
            val nama_depan = binding.txtNamaDepanReg.text.toString()
            val nama_belakang = binding.txtNamaBelReg.text.toString()
            val email = binding.txtEmailReg.text.toString()
            val password = binding.txtPassReg.text.toString()
            val konfPassw = binding.txtRePassReg.text.toString()

            val dialog = AlertDialog.Builder(activity)
            if (password == konfPassw) {
                dialog.setTitle("Konfirmasi")
                dialog.setMessage("Apakah anda ingin melakukan registrasi?")
                dialog.setPositiveButton("Register", DialogInterface.OnClickListener { dialog, which ->
                    var user = User(
                        username,
                        nama_depan,
                        nama_belakang,
                        password,
                        email
                    )
                    val list = listOf(user)
                    viewModel.register(list)
                    val alert = AlertDialog.Builder(activity)
                    alert.setTitle("Informasi")
                    alert.setMessage("Berhasil mendaftarkan user.\nLogin dengan username dan password yang sudah didaftarkan.")
                    alert.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        val action = RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment2()
                        Navigation.findNavController(it).navigate(action)
                    })
                    alert.create().show()
                })
                dialog.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
                })
                dialog.create().show()
            } else {
                dialog.setTitle("Informasi")
                dialog.setMessage("Gagal mendaftarkan user.\nCek apakah password dengan konfirmasinya sama")
                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                })
                dialog.create().show()
            }
        }
    }

}