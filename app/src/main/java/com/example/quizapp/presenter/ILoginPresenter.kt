package com.example.quizapp.presenter

interface ILoginPresenter  {
    fun logar(email: String, senha: String)
    fun verificarUsuarioLogado()
    fun checkErroLogin(text :CharSequence?,campo :Int)
    fun onDestroy()
}