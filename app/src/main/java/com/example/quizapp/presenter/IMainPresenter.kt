package com.example.quizapp.presenter

import com.example.quizapp.model.Usuario

interface IMainPresenter {
    fun recuperarDadosUsuario()
    fun deslogarUsuario()
    fun iniciarQuiz()
    fun onDestroy()
}