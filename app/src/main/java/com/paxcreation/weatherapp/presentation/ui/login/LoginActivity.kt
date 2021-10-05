package com.paxcreation.weatherapp.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.ActivityLoginBinding
import com.paxcreation.weatherapp.domain.config.AppConfig.RC_SIGN_IN
import com.paxcreation.weatherapp.presentation.ui.base.activity.BaseViewModelActivity
import com.paxcreation.weatherapp.presentation.ui.main.MainActivity
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseViewModelActivity<ActivityLoginBinding, LoginViewModel>() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    //facebook
    private lateinit var callbackManager: CallbackManager

    private val TAG = "TAG"

    override fun createViewModelClass() = LoginViewModel::class.java
    override fun getScreenName(): String? = null
    override fun getLayoutResource() = R.layout.activity_login
    override fun initObserver() {}

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        firebaseAuth = Firebase.auth
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        callbackManager = CallbackManager.Factory.create()


        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    handleFacebookAccessToken(result?.accessToken)
                }

                override fun onCancel() {
                    Toast.makeText(applicationContext, "Login Cancelled", Toast.LENGTH_LONG).show()
                }

                override fun onError(error: FacebookException?) {
                    Toast.makeText(applicationContext, error?.message, Toast.LENGTH_LONG).show()
                }

            })


        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)



        binding.buttonSkip.setOnClickListener {
            viewModel.doSkip(applicationContext)
        }
        binding.buttonFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"))
        }

        binding.buttonGoogle.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }


    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun handleFacebookAccessToken(token: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(token?.token.toString())
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext, "Authentication success.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}


