package com.example.quizapp.repository

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.example.quizapp.model.Usuario
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot

interface IUsuarioBD {
    suspend fun cadastrarUsuario(email: String, senha: String, nome:String): Usuario
    suspend fun logarUsuario(email:String ,senha :String): AuthResult
    suspend fun recuperarUsuarioLogado():Usuario
     fun verificarUsuariologdao():Boolean
     fun deslogarUsuario()
     fun salvarUsuario(usuario: Usuario)
     fun convertBundleToUsuario(bundle: Bundle?): Usuario?
     fun recuperarUriImagen(uri: Uri?)
    suspend fun carregarImagemPerfil(usuario: Usuario):Uri


}