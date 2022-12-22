package com.example.quizapp.firebase

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthFirebase {
  private val authFirebase =FirebaseAuth.getInstance()

    fun verificarUsuarioLogado(): FirebaseUser? {
      val user = authFirebase.currentUser
      if (user !=null){
          return user
      }else{

         throw RuntimeException("Usuairo não logado")
      }
   }
    fun verificarUsuarioLogadoBool():Boolean {
        val user = authFirebase.currentUser
        if (user !=null){
            return true
        }

        return false
    }

    fun  cadastrarUsuario(email:String,senha :String){
             authFirebase.createUserWithEmailAndPassword(
                    email,senha
                )
                    .addOnSuccessListener {


                    }
                    .addOnFailureListener {

                    }

    }

    suspend fun  logarUsurio(email:String, senha : String): AuthResult {
        return try {
            authFirebase.signInWithEmailAndPassword(
                email,senha
            ).await()

        }catch (ex :RuntimeException){
            throw RuntimeException("erro ao logar ${ex.message} -- ${ex.stackTrace}")
        }

    }

    fun deslogarUsuario(){
        authFirebase.signOut()
    }
}