package com.example.quizapp.repository

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.example.quizapp.model.Usuario
import com.google.firebase.firestore.DocumentSnapshot

interface IUsuarioBD {
    suspend fun cadastrarUsuario(email: String, senha: String, nome:String): Usuario
    suspend fun logarUsuario(email:String ,senha :String): Boolean
    suspend fun recuperarUsuarioLogado():Usuario
     fun verificarUsuariologdao():Boolean
     fun deslogarUsuario()
     fun salvarUsuario(usuario: Usuario)
     fun convertBundleToUsuario(bundle: Bundle?): Usuario?
     fun recuperarUriImagen(uri: Uri?)
     fun abrirGaleria(galeria : ActivityResultLauncher<String>? = null,)
    suspend fun carregarImagemPerfil(usuario: Usuario):Uri
    fun abrirRecusroFoto( permissao :String)
    fun adicionarFotoPorCamera(resultActivity : ActivityResult)
}