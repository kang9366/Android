package com.example.restaurantbook

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class LoginActivity : AppCompatActivity() {

    //variable
    var auth : FirebaseAuth? = null
    var callbackManager : CallbackManager? = null
    var googleSignInClient : GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //setting firebase auth
        auth = FirebaseAuth.getInstance()

        //login with google
        callbackManager = CallbackManager.Factory.create()
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
        requestIdToken("935940652134-fdivfa4jvvvefuavn1g01skhfmiv75eb.apps.googleusercontent.com").requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        LoginButton.setOnClickListener {
            if(edit_email.text.toString() == ""){
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }else if(edit_password.text.toString() == ""){
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }else{
                signinEmail()
            }
        }

        SignUpButton.setOnClickListener {
            go_to_signUp_page()
        }

        loginFacebook.setOnClickListener {
            facebookLogin()
        }

        loginKakao.setOnClickListener {
            googleLogin()
        }

        loginNaver.setOnClickListener {
            prepare()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_LOGIN_CODE){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                if(result.isSuccess){
                    var account = result.signInAccount
                    if (account != null) {
                        firebaseAuthWithGoogle(account)
                    }
                }
            }
        }
    }

    //login with email
    fun signinEmail(){
        auth?.signInWithEmailAndPassword(edit_email.text.toString(), edit_password.text.toString())
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    //creating a user account
                    go_to_get_information_page(task.result?.user)
                }else{
                    //fail to login
                    Toast.makeText(this, "이메일 혹은 비밀번호 불일치", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //login with google
    fun googleLogin(){
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    fun firebaseAuthWithGoogle(account : GoogleSignInAccount){
        var credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)?.addOnCompleteListener { task ->
            if(task.isSuccessful){
                //creating a user account
                go_to_get_information_page(task.result?.user)
            }else{
                //fail to login
                Toast.makeText(this, "이메일 혹은 비밀번호 불일치", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //login with facebook
    //facebook hash key = wYyLk4l7k9eouN0dZDpjzNITzBM=
    fun printHashKey() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("TAG", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("TAG", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("TAG", "printHashKey()", e)
        }
    }

    fun facebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                handleFacebookAccessToken(result?.accessToken)
            }
            override fun onCancel() {
            }
            override fun onError(error: FacebookException?) {
            }
        })
    }

    fun handleFacebookAccessToken(token : AccessToken?){
        var credential = FacebookAuthProvider.getCredential(token?.token!!)
        auth?.signInWithCredential(credential)?.addOnCompleteListener { task ->
            if(task.isSuccessful){
                //Login
                go_to_get_information_page(task.result?.user)
            }else{
                Toast.makeText(this, "fail to login", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //page intent functions
    fun go_to_get_information_page(user : FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, GetInformationActivity::class.java))
        }
    }

    fun go_to_signUp_page(){
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    //fun intent toast
    fun prepare(){
        Toast.makeText(this@LoginActivity, "준비중입니다", Toast.LENGTH_SHORT).show()
    }
}