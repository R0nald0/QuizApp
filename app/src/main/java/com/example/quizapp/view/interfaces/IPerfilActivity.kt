package com.example.quizapp.view.interfaces

import android.net.Uri
import android.os.Bundle
import com.example.quizapp.model.Usuario

interface IPerfilActivity {
    fun carregarBundle(): Bundle?
    fun recuperarDadosUsuario(usuario: Usuario,uri: Uri?)
    fun exibirToast(mensgem: String)
    fun mudarView()
}