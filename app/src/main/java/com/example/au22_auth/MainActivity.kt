package com.example.au22_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var emailView : EditText
    lateinit var passwordView :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailView = findViewById(R.id.emailEditText)
        passwordView = findViewById(R.id.passwordEditText)

        auth = Firebase.auth

        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            signUp()
        }

        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener {
            signIn()
        }

       // auth.signOut()

        if(auth.currentUser != null) {
            Log.d("!!!", "${auth.currentUser?.email}")
            goToAddActivity()
        }


    }

    fun goToAddActivity() {
        val intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }

    fun signIn() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    Log.d("!!!", "sign in sucess")
                    goToAddActivity()
                } else {
                    Log.d("!!!","sing in fail ${task.exception}")
                }
            }
    }


    fun signUp() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.d("!!!", "create sucess")
                    goToAddActivity()
                } else {
                    Log.d("!!!","user not created ${task.exception}")
                }
            }


    }

}