package com.example.quizapp.presenter

interface ILoginPresenter  {
    fun logar(email: String, senha: String)
    fun verificarUsuarioLogado()
}