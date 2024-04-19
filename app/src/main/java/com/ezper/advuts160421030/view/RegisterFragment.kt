package com.ezper.advuts160421030.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ezper.advuts160421030.R
import com.ezper.advuts160421030.databinding.FragmentLoginBinding
import com.ezper.advuts160421030.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding:FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener(){
            var username:String=binding.txtUsernameReg.text.toString()
            var email:String = binding.txtEmailReg.text.toString()
            var password:String=binding.txtPassReg.text.toString()
            var nama_depan:String=binding.txtNamaDepanReg.text.toString()
            var nama_belakang:String=binding.txtNamaBelReg.text.toString()
            var repass: String=binding.txtRePassReg.text.toString()
            val q = Volley.newRequestQueue(this.requireContext())
            val url_target = "http://10.0.2.2/news/register.php"
            if (password == repass) {
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url_target,
                    {
                        Log.d("apiresult", it)
                    },
                    {
                        Log.e("apierror", it.printStackTrace().toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["username"] = username
                        params["email"] = email
                        params["password"] = password
                        params["nama_depan"] = nama_depan
                        params["nama_belakang"] = nama_belakang
                        return params
                    }
                }
                q.add(stringRequest)

                Toast.makeText(this.requireContext(), "Successfully Registered", Toast.LENGTH_SHORT)
                    .show()
                val action = RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment2()
                Navigation.findNavController(it).navigate(action)
            }
            else {
                Toast.makeText(this.requireContext(), "Retype Password is wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}