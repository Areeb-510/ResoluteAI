package com.example.resoluteai

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.resoluteai.data.QRResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class MainActivity : AppCompatActivity() {

    private lateinit var btn_scan : Button

    private lateinit var rootView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        rootView = findViewById(android.R.id.content)

        val snackbar = Snackbar.make(rootView, "Welcome ResoluteAI user, you can Scan the QR here", Snackbar.LENGTH_LONG)
        snackbar.show()

        btn_scan = findViewById(R.id.btn_scan)

        btn_scan.setOnClickListener {
            barcodeLauncher.launch(ScanOptions())
        }
    }

    // Register the launcher and result handler
    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else {


            val db = Firebase.firestore

            val qrResult = QRResult(result.contents.toString())

            db.collection("QR_Result")
                .add(qrResult)
                .addOnSuccessListener { documentReference ->
                    // Document added successfully
                    val documentId = documentReference.id
                    // Perform any additional operations

                    val snackbar = Snackbar.make(rootView, "Added ${qrResult.qr_value} to Firebase firestore", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
                .addOnFailureListener { e ->
                    // Error occurred while adding the document
                    // Handle the error appropriately
                    Log.d("check",e.toString())
                }
        }
    }
}