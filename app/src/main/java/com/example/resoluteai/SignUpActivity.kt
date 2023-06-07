package com.example.resoluteai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignIn: MaterialButton

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // User is not logged in
            // Prompt the user to log in or sign up
        }


        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSignIn = findViewById(R.id.btnSignIn)

        val toSignIn : TextView = findViewById(R.id.toSignIn)

        toSignIn.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            // Validate email and password
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // User creation successful
                        val user: FirebaseUser? = auth.currentUser
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        // You can perform additional operations on the user, such as updating their profile
                        // or sending a verification email
                    } else {
                        // User creation failed
                        val exception = task.exception
                        // Handle the exception and display an error message to the user
                    }
                }

        }


    }

//    fun toSignIn(view: View) {
//        val intent = Intent(this,SignInActivity::class.java)
//        startActivity(intent)
//    }
}