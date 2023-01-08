package com.example.quizapp.firebase

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
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

    suspend fun  cadastrarUsuario(email:String, senha :String):AuthResult{
           return try {
                authFirebase.createUserWithEmailAndPassword(
                    email,senha
                ).await()
            }catch (ex :Exception){
                throw Exception(ex.message)
            }

    }

    suspend fun  logarUsurio(email:String, senha : String): AuthResult {
           return authFirebase.signInWithEmailAndPassword(
                email,senha
            ).await()
    }

    fun deslogarUsuario(){
        authFirebase.signOut()
    }
}