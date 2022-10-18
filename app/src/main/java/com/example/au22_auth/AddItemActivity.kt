package com.example.au22_auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class AddItemActivity : AppCompatActivity() {

    lateinit var nameView : EditText
    lateinit var db : FirebaseFirestore
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        db = Firebase.firestore
        auth = Firebase.auth

        nameView = findViewById(R.id.nameView)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            saveItem()
        }

        // read items
        val user = auth.currentUser
        if(user != null) {
            db.collection("users").document(user.uid)
                .collection("items")
                .addSnapshotListener { snapshot , e ->
                    if (snapshot != null) {
                        for (document in snapshot.documents) {
                            val item = document.toObject<Item>()
                            Log.d("!!!", "item: ${item}")
                        }
                    }


                }






        }




    }

    fun saveItem() {
        val item = Item(name = nameView.text.toString() )
        nameView.setText("")

        val user = auth.currentUser
        if( user == null) {
            return
        }

        db.collection("users").document(user.uid).collection("items").add(item)
            .addOnCompleteListener{
                Log.d("!!!", "add item")
            }


    }
}