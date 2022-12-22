package com.example.quizapp.presenter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher

interface ICadastroPresenter {
    fun cadastrarUsuario(email: String,senha :String , nome:String)

    fun carregarImagemPerfil(uri: Uri?)
    fun verificarPermissao(permissao: Boolean)
    fun abrirGaleria()
    fun requisitarPermissao(gerenciadorDePermissoao : ActivityResultLauncher<String>, permissaoNome:String, activity: Activity)
    fun adicionarFotoPorCamera( resultActivity : ActivityResult)
}