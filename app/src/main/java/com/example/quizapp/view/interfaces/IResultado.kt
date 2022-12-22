package com.example.quizapp.view.interfaces

import android.os.Bundle
import android.view.View
import com.example.quizapp.model.Usuario

interface IResultado {
   fun recuperarUsuarioBudle() : Bundle?
   fun novoQuis(view : View)
   fun exibirCardUSuario(usuario: Usuario)
   fun exbirMensagemToast(mensegem:String)
   fun carregarMenu()
   fun deslogar()
}