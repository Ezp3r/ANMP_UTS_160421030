package com.ezper.advuts160421030.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ezper.advuts160421030.R
import com.ezper.advuts160421030.databinding.FragmentCreateBinding
import com.ezper.advuts160421030.databinding.FragmentRegisterBinding
import com.ezper.advuts160421030.model.News
import com.ezper.advuts160421030.model.User
import com.ezper.advuts160421030.viewmodel.DetailNewsViewModel
import com.ezper.advuts160421030.viewmodel.UserViewModel

class CreateFragment : Fragment(), UpdateUserClick {
    private lateinit var binding: FragmentCreateBinding
    private lateinit var viewModel: DetailNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_register, container, false)
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailNewsViewModel::class.java)

        binding.btnCreate.setOnClickListener {
            val judul = binding.txtJudul.text.toString()
            val pengarang = binding.txtPenganrang.text.toString()
            val url = binding.txtUrl.text.toString()
            val deskripsi = binding.txtDeskripsi.text.toString()
            val isi = binding.txtIsiCreate.text.toString()

            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Konfirmasi")
            dialog.setMessage("Apakah anda ingin create news?")
            dialog.setPositiveButton("Create", DialogInterface.OnClickListener { dialog, which ->
                var news = News(
                    judul,
                    url,
                    deskripsi,
                    pengarang,
                    isi
                )
                val list = listOf(news)
                viewModel.addNews(list)
                val alert = AlertDialog.Builder(activity)
                alert.setTitle("Informasi")
                alert.setMessage("Berhasil create news.")
                alert.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                })
                alert.create().show()
            })
            dialog.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
            })
            dialog.create().show()
        }
    }

    override fun updateUser(v: View, obj: User) {
        TODO("Not yet implemented")
    }
}