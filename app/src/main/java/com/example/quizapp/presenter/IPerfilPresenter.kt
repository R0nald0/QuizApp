package com.example.quizapp.presenter

interface IPerfilPresenter {
  fun recuperarBundleParaUsuario()
  fun atualizarUsuario(nome : String)
  fun onDestroy()
}