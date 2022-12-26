package com.example.quizapp.presenter

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher

interface ICadastroPresenter {
    fun cadastrarUsuario(email: String,senha :String , nome:String)

    fun carregarImagemPerfil(uri: Uri?)
    fun verificarPermissao(permissao: Map<String, Boolean>)
    fun requisitarPermissao(gerenciadorDePermissoao :  ActivityResultLauncher<Array<String>>, permissaoNome:String, activity: Activity)
    fun adicionarFotoPorCamera( resultActivity : ActivityResult,context: Context)
}