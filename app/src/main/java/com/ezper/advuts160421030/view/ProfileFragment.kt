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
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ezper.advuts160421030.R
import com.ezper.advuts160421030.databinding.FragmentLoginBinding
import com.ezper.advuts160421030.databinding.FragmentProfileBinding
import com.ezper.advuts160421030.model.User
import com.ezper.advuts160421030.viewmodel.UserViewModel
import java.time.LocalDateTime

class ProfileFragment : Fragment(), UpdateUserClick {
    private lateinit var binding:FragmentProfileBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (LoginActivity.getSharedPref(requireActivity()) != 0) {
            viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
            val shared = activity?.packageName
            val sharedPref: SharedPreferences = requireActivity().getSharedPreferences(shared, Context.MODE_PRIVATE)
            val id = sharedPref.getInt("KEY_ID", 0)
            viewModel.selectUser(id)
            binding.updateListener = this
            binding.btnLogout.setOnClickListener {
                MainActivity.logout(requireActivity())
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            observeViewModel()
        }

    }

    fun observeViewModel() {
        viewModel.userLD.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.user = it
            }
        })
    }

    override fun updateUser(v: View, obj: User) {
        val alert = AlertDialog.Builder(activity)
        alert.setTitle("Konfirmasi")
        alert.setMessage("Apakah anda yakin untuk mengubah data akun ini?")
        alert.setPositiveButton("Ubah", DialogInterface.OnClickListener { dialog, which ->
            viewModel.update(
                obj.nama_depan,
                obj.nama_belakang,
                obj.password,
                obj.uuid)
            alert.setTitle("Informasi")
            alert.setMessage("Berhasil ubah data")
            alert.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->  })
            alert.create().show()
        })
        alert.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
        })
        alert.create().show()
    }
}
