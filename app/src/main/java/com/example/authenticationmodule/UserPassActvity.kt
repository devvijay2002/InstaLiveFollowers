package com.example.authenticationmodule


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ott.util.getStringFromView
import com.example.ott.util.showToast
import com.example.userdata.MainActivity
import com.example.userdata.databinding.ActivityUserPassActvityBinding
import com.example.userdata.util.ErrorUtil
import com.example.userdata.util.ProgressDialogUtils
import com.example.userdata.viewModel.M1ViewModel2

class UserPassActvity : AppCompatActivity() {
    lateinit var binding: ActivityUserPassActvityBinding
    private lateinit var m1ViewModel: M1ViewModel2

    private var firstName: String? = null
    private var secondName: String? = null
    private var email: String? = null
    private var age: Int = 0
    private var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPassActvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve intent extras inside onCreate
        intent.extras?.let {
            firstName = it.getString("firstName")
            secondName = it.getString("secondName")
            email = it.getString("email")
            age = it.getInt("age", 0)
            number = it.getInt("number", 0)
        }

        initViews()
        observeUserData()
        binding.Submit.setOnClickListener {
            val username = binding.etUserName.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty()) {
                showToast(this, "Please Enter UserName")
            } else if (password.isEmpty()) {
                showToast(this, "Please Enter Password")
            } else {
                hitSignUp()
            }



        }
    }

    private fun hitSignUp() {
        if (firstName != null && secondName != null && email != null) {
            m1ViewModel.hitSignUp(
                getStringFromView(binding.etUserName),
                getStringFromView(binding.etPassword),
                firstName!!,
                secondName!!,
                email!!,
                age,
                number
            )
        }
    }

    private fun initViews() {
        m1ViewModel = ViewModelProvider(this)[M1ViewModel2::class.java]
    }


    private fun observeUserData() {
        m1ViewModel.SignUpResp.observe(this) { userData ->
            if (userData.isSuccess) {

                val intent = Intent(this, LoginActivity::class.java)

      /*          // Adding key-value pairs to the Intent
                intent.putExtra("firstName",     getStringFromView(binding.editTextFirstName))
                intent.putExtra("secondName",        getStringFromView(binding.editTextSecondName))
                intent.putExtra("email",          getStringFromView(binding.editTextEmail))
                intent.putExtra("age",         getStringFromView(binding.editTextAge))
                intent.putExtra("number",             getStringFromView(binding.editTextNumber))*/
                startActivity(intent)


            } else {
                showToast(this,userData.message)
            }
        }

        m1ViewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }

        m1ViewModel.progress.observe(this) {
            if (it) ProgressDialogUtils.getInstance().showProgress(this, false)
            else ProgressDialogUtils.getInstance().hideProgress()
        }
    }
}
