package com.vijay.chatter

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.vijay.chatter.Model.Users
import com.vijay.chatter.databinding.ActivitySignupBinding


class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signup)

        supportActionBar?.hide()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        progressDialog = ProgressDialog(this@SignupActivity)
        progressDialog.setTitle("Creating Account")
        progressDialog.setMessage("Your Account is Creating")

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnSignup.setOnClickListener {
            val username = binding.txtUsername.text.toString().trim()
            val email = binding.txtEmail.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()
            signUp(username, email, password)
        }


    }

    private fun signUp(username: String, email: String, password: String) {
        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()
        ) {
            progressDialog.show()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressDialog.dismiss()
                    if (task.isSuccessful) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        val user = Users(
                            binding.txtUsername.text.toString().trim(),
                            binding.txtEmail.text.toString().trim(),
                            binding.txtPassword.text.toString().trim()
                        )
                        val userId = task.result.user?.uid
                        if (userId != null) {
                            database.reference.child("Users").child(userId).setValue(user)
                        }
                    //val user = auth.currentUser

                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //updateUI(null)
                    }
                }

        } else {
            Toast.makeText(this, "Enter Credentials", Toast.LENGTH_SHORT).show()
        }
    }


}


