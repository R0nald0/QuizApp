package com.example.quizapp.presenter

interface IMainPresenter {
    fun recuperarDadosUsuario()
    fun deslogarUsuario()
    fun iniciarQuiz()
    fun onDestroy()
}