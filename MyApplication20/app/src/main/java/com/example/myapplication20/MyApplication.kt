package com.example.myapplication20

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.common.KakaoSdk

class MyApplication: MultiDexApplication() {
    companion object{
        lateinit var auth: FirebaseAuth
        var email: String? = null

        fun checkAuth(): Boolean{
            var currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                currentUser.isEmailVerified
            }?: let{
                false
            }
        }
    }

    override fun onCreate(){
        super.onCreate()
        auth = Firebase.auth
        KakaoSdk.init(this, "81da1541e6fc97c2655f8f088103c22a")
    }
}