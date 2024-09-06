package com.example.bdfirebase

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.example.bdfirebase.ui.theme.BDFirebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BDFirebaseTheme {
               App(db)
            }
        }
    }
}


@Composable
fun App(db : FirebaseFirestore) {
    var nome by remember {
        mutableStateOf("")
    }
    var telefone by remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
        }
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text(text = "App Firebase Firestore")
        }

        Column(
        ) {
            TextField(
                value = nome,
                onValueChange = { nome = it }
            )
        }
    }

    Row(
        Modifier
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth(0.3f)
        ) {
            Text(text = "Telefone:")
        }
        Column(
        ) {
            TextField(
                value = telefone,
                onValueChange = { telefone = it }
            )
        }
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
    }
    Row(
        Modifier
            .fillMaxWidth(),
        Arrangement.Center
    ) {
        Button(onClick = {
            val pessoas = hashMapOf(
                "nome" to nome,
                "telefone" to telefone

            )
            db.collection("clientes").add(pessoas)
                .addOnSuccessListener { DocumentReference ->
                    Log.d(
                        TAG,
                        "DocumentSnapshot written ID: ${DocumentReference.id}"
                    )
                }

                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error written Document", e) }

        }
        ) {
            Text(text = "Cadastrar")

        }
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
    }
    Row(
        Modifier
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth(0.3f)
        ) {
            Text(text = "Nome:")
        }
    }
    Row(
        Modifier.fillMaxWidth()
    ){
        Column(

        ){
         db.collection("Clientes")
             .get()
             .addOnSuccessListener{Documents -> for (document in Documents){
                  val listar = hashMapOf(
                      "nome" to "${document.data.get("nome")}",
                      "telefone" to "${document.data.get("telefone")}"
                  )
                 Log.w(TAG, "${document.id} => ${document.data}")
             }
             }
             .addOnFailureListener{exception ->
                 Log.w(TAG,"Error getting documents: ", exception)

             }
        }
    }
}
