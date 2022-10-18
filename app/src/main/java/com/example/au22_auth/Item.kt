package com.example.au22_auth

import com.google.firebase.firestore.DocumentId

data class Item(@DocumentId var documntId : String? = null,
                var name: String? = null,
                var done : Boolean = false )