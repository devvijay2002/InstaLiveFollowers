package com.example.authenticationmodule

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.ott.util.getStringFromView
import com.example.ott.util.moveActivity
import com.example.ott.util.showToast
import com.example.userdata.MainActivity
import com.example.userdata.R
import com.example.userdata.databinding.ActivityLoginBinding
import com.example.userdata.util.ErrorUtil
import com.example.userdata.util.MyPreferences
import com.example.userdata.util.ProgressDialogUtils
import com.example.userdata.viewModel.M1ViewModel2

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var m1ViewModel2: M1ViewModel2

    lateinit var context: Context
    lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        context = this
        myPreferences = MyPreferences(context)
        initView()
        observeUserData()
        binding.signupTextview.setOnClickListener {
        moveActivity(SignUpActivity())
}
        binding.Login.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (username.isEmpty()) {
                showToast(this, "Please Enter UserName")
            } else if (password.isEmpty()) {
                showToast(this, "Please Enter Password")
            } else {
                hitLogin()
                myPreferences.saveString("InstaId", username)

            }
        } }

    private fun hitLogin(){
        m1ViewModel2.hitLogin(
            getStringFromView(binding.editTextUsername),
        getStringFromView(binding.editTextPassword))
    }

   private fun initView(){
       m1ViewModel2= ViewModelProvider(this).get(M1ViewModel2::class.java)
   }

    private fun observeUserData(){
        m1ViewModel2.LoginResp.observe(this){ loginData ->
            if (loginData.isSuccess){

                val intent = Intent(this, MainActivity::class.java)
                // Adding key-value pairs to the Intent
                intent.putExtra("userName",     getStringFromView(binding.editTextUsername))
                intent.putExtra("Password",        getStringFromView(binding.editTextPassword))

                startActivity(intent)
                myPreferences.saveString("isLogin","true")

            }else{
                showToast(this,loginData.message)
                myPreferences.saveString("isLogin","false")
            }

        }

        m1ViewModel2.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }

        m1ViewModel2.progress.observe(this) {
            if (it) ProgressDialogUtils.getInstance().showProgress(this, false)
            else ProgressDialogUtils.getInstance().hideProgress()
        }
    }
}