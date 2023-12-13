package com.example.userdata

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.authenticationmodule.LoginActivity
import com.example.modelresp.UpdatedUserResp
import com.example.ott.util.moveActivity
import com.example.ott.util.showToast
import com.example.userdata.databinding.ActivityMainBinding
import com.example.userdata.util.ErrorUtil
import com.example.userdata.util.MyPreferences
import com.example.userdata.util.ProgressDialogUtils
import com.example.userdata.viewModel.M1ViewModel
import com.example.userdata.viewModel.M1ViewModel2
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var m1ViewModel: M1ViewModel
    private lateinit var m1ViewModel2: M1ViewModel2
    private lateinit var userAdapter: UserAdapter

    lateinit var context: Context
    lateinit var myPreferences: MyPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize myPreferences here
        context = this
        myPreferences = MyPreferences(context)

        val instaId = myPreferences.getString("InstaId", "")
        binding.InstaId.text = "Hi, "+ instaId

        binding.userFollowers.visibility = View.VISIBLE


        binding.animationRefresh.visibility = View.GONE
        binding.animationRefresh.cancelAnimation()

        initViews()
        initRecyclerView()
        //fullScreen2()
     // hitUpdatedUserData()
        observeUserData()
        val m1ViewModel2Data = loadM1ViewModel2DataFromPreferences()
        userAdapter.updateData(m1ViewModel2Data)

        observeM1ViewModel2()
        observePostData()

        binding.refresh.setOnClickListener {
           binding.animationRefresh.visibility = View.VISIBLE
binding.animationRefresh.playAnimation()
    hitUpdatedUserData()

        }

        binding.cardView2.visibility = View.GONE




        binding.recyclerView.adapter = userAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.isNestedScrollingEnabled = true
        binding.userFollowers.visibility = View.VISIBLE

        hitUserData1()
binding.rlMenu.setOnClickListener {
    if (binding.rlMenu.getVisibility() === View.VISIBLE) {
        rightOut()
    } else {
        rightIn()
    }
}
        binding.rlSetting.setOnClickListener {


            if (binding.rlMenu.getVisibility() === View.VISIBLE) {
                rightOut()
            } else {
                rightIn()
            }

        }

 /*       binding.button1.setOnClickListener {
            val username = binding.editText.text.toString()

            if (username!=""){
            hitUserData(username)
            }else{
                showToast(this,"Please Enter User I'D")
            }
        }

*/


        binding.Menu.tvLogout.setOnClickListener {
            moveActivity(LoginActivity())
            myPreferences.saveString("isLogin","false")
            finishAffinity()
        }
    }

    fun rightOut() {

        val startAnimation = AnimationUtils.loadAnimation(
            applicationContext, R.anim.right_out
        )
        binding.rlMenu.setAnimation(startAnimation)
        binding.rlMenu.setVisibility(View.GONE)
        binding.rlMenu.setClickable(false)
    }

    fun rightIn() {
        val startAnimation = AnimationUtils.loadAnimation(
            applicationContext, R.anim.right_in
        )
        binding.rlMenu.setVisibility(View.VISIBLE)
        binding.rlMenu.setAnimation(startAnimation)
        binding.rlMenu.setClickable(true)
    }
private fun hitUserData1(){

    val instaId = myPreferences.getString("InstaId", "")
    val username = instaId

    if (username!=""){
        hitUserData(username)
    }else{
        showToast(this,"User I'd Not Found")
    }
}
    private fun observeM1ViewModel2() {
        // Observe changes in GetUserUpdatedData LiveData
        m1ViewModel2.GetUserUpdatedData.observe(this, Observer { updatedUserResp ->
            // Update RecyclerView adapter with the new data
            userAdapter.updateData(updatedUserResp.result)
            if (updatedUserResp.isSuccess) {
                binding.animationRefresh.visibility = View.GONE
                binding.animationRefresh.cancelAnimation()

                // Save the data to SharedPreferences
                saveM1ViewModel2DataToPreferences(updatedUserResp.result)
            } else {
                // Handle error if needed
            }
        })
    }

    private fun observePostData(){
           // Observe changes in GetUserUpdatedData LiveData
           m1ViewModel2.PostResp.observe(this, Observer { updatedUserResp ->

               if (updatedUserResp.isSuccess){
                   hitUpdatedUserData()



               }else{

               }
           })


}

    private fun observeUserData() {
        m1ViewModel.UserResp.observe(this) { userData ->
            if (userData.status == true) {
                binding.userFollowers.visibility = View.VISIBLE
                binding.cardView2.visibility = View.VISIBLE

                binding.userProfile.apply {
                    m1ViewModel.UserResp.observe(this@MainActivity) { userResponse ->
                        // Check if the URI is not null before loading the image
                        userResponse?.userProfilePic?.let { uriString ->
                            Glide.with(this)
                                .load(uriString)
                                .into(this)
                        }
                    }
                }

                binding.userFollowers.visibility = View.VISIBLE
                binding.userFollowers.text="Your Instagram Followers: ${userData.userFollowers}"
                hitPostUserData(userData.instagramUsername, userData.userFollowers)
            } else {

                binding.userFollowers.visibility = View.VISIBLE
                binding.userFollowers.text = "Invalid User ID: "
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
    private fun hitUserData(username: String) {
        val baseUrl = "https://instaskull.com/"
        val endUrl = "tucktools_new?username=$username"
        val referer = "https://www.tucktools.com/"
        val secFetchSite = "cross-site"
        m1ViewModel.hitUserData("$baseUrl$endUrl", referer, secFetchSite)
    }

    private fun hitUpdatedUserData() {
        val instaId = myPreferences.getString("InstaId", "")

        m1ViewModel2.hitUpdatedUserData("UserId/$instaId")
    }

    private fun hitPostUserData(username: String, followers: Int) {
        m1ViewModel2.hitPostdUserData(username, followers)
    }


    private fun initViews() {
        m1ViewModel = ViewModelProvider(this).get(M1ViewModel::class.java)
        m1ViewModel2 = ViewModelProvider(this).get(M1ViewModel2::class.java)
    }

    private fun fullScreen2() {
        window.setFlags(1024, 1024)
    }


    private fun initRecyclerView() {
        userAdapter = UserAdapter(emptyList(), this)
        binding.recyclerView.adapter = userAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClick(userId: String) {
        // Handle the click event by filling the EditText with the userId
        binding.editText.setText(userId)
    }




    private fun saveM1ViewModel2DataToPreferences(dataList: List<UpdatedUserResp.Result>) {
        val gson = Gson()
        val json = gson.toJson(M1ViewModel2Preferences(dataList))
        myPreferences.saveString("m1ViewModel2Data", json)
    }

    private fun loadM1ViewModel2DataFromPreferences(): List<UpdatedUserResp.Result> {
        val gson = Gson()
        val json = myPreferences.getString("m1ViewModel2Data", "")
        return if (json.isNullOrEmpty()) {
            // Default data if SharedPreferences is empty
            emptyList()
        } else {
            val m1ViewModel2Preferences = gson.fromJson(json, M1ViewModel2Preferences::class.java)
            m1ViewModel2Preferences.updatedUserRespList
        }
    }
}
