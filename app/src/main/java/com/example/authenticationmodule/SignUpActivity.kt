package com.example.authenticationmodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.ott.util.getStringFromView
import com.example.ott.util.moveActivity
import com.example.ott.util.showToast
import com.example.userdata.databinding.ActivitySignUpBinding
import com.example.userdata.viewModel.M1ViewModel2

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.Next.setOnClickListener {
            val firstName = binding.editTextFirstName.text.toString()
            val secondName = binding.editTextSecondName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val age = binding.editTextAge.text.toString()

            if (firstName.isEmpty()) {
                showToast(this, "Please Enter First Name")
            } else if (secondName.isEmpty()) {
                showToast(this, "Please Enter Last Name")
            } else if (email.isEmpty()) {
                showToast(this, "Please Enter Email")
            } else if (age.isEmpty()) {
                showToast(this, "Please Enter Age")
            } else {
                val intent = Intent(this, UserPassActvity::class.java)

                // Adding key-value pairs to the Intent
                intent.putExtra("firstName",     getStringFromView(binding.editTextFirstName))
                intent.putExtra("secondName",        getStringFromView(binding.editTextSecondName))
                intent.putExtra("email",          getStringFromView(binding.editTextEmail))
                intent.putExtra("age",         getStringFromView(binding.editTextAge))
                intent.putExtra("number",             getStringFromView(binding.editTextNumber))
                startActivity(intent)

            }
        }

        }


    }

