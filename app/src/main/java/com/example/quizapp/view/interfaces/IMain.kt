package com.example.quizapp.view.interfaces

import android.os.Bundle
import com.example.quizapp.model.Usuario

interface IMain {
   fun recuperarIntent() : Bundle?
   fun addItensView(usuario: Usuario)
   fun mostrarMsg(mensagem :String)
   fun deslogarUsuario()
   fun iniciarQuiz(usuario: Usuario)
   fun esconderProgressBar()
   fun exibirProgressBar()

}