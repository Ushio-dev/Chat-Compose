package com.example.chatcompose.data

import android.util.Log
import com.example.chatcompose.Mensaje
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseDataManager {
    private val databse = FirebaseDatabase.getInstance().getReference("message")

    suspend fun writeMessage(userId: String, msg: Mensaje) {
        databse.child(userId).setValue(msg).await()
    }

    fun getIdReference(): String? {
        return databse.push().key
    }

    fun getMessagges(): Flow<List<Mensaje>> = callbackFlow {
        val suscription = databse.addValueEventListener(object : ValueEventListener {
            var msgs = arrayListOf<Mensaje>()
            override fun onDataChange(snapshot: DataSnapshot) {
                msgs.clear()
                for (dataSnapshot in snapshot.children) {
                    var mensajes = dataSnapshot.getValue(Mensaje::class.java)

                    mensajes?.let {
                        Log.d("MENSAJES", mensajes.toString())
                        msgs.add(it)
                    }
                }
                trySend(msgs.toList()).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        awaitClose {databse.ref.removeEventListener(suscription)}
    }
}