package com.example.quizapp.firebase

import android.net.Uri
import android.util.Log
import com.example.quizapp.model.Usuario
import com.example.quizapp.utils.Constantes
import com.example.quizapp.view.CadastroActivity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await


object FirebaseDocsStorage  {
    const val CAMINHO_IMAGEM_PERFIL =""
    private val firebaseStorage = FirebaseStorage.getInstance()


  suspend fun salvarImagem(usuario:Usuario){
      firebaseStorage.getReference("perfil")
         .child("${usuario.id}")
         .child("fotoPerfil01")
         .putFile(Uri.parse(usuario.urlImagemPerfil)).await()
   }
    suspend fun dowloadImagem(usuario: Usuario):Uri {
         val uri= firebaseStorage.getReference("perfil")
                 .child(usuario.id)
                 .child("fotoPerfil01")
                 .downloadUrl.await()
        Log.i(Constantes.TAG, "Imagem uriPerfil ${uri}")
        return  uri
    }


}