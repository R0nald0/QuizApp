package com.example.quizapp.presenter

interface IPerguntasPresenter {
    fun recuperarPerguntas()
    fun exibirInformacaoLista()
    fun buscarProximaPergunta()
    fun recuperarUsuario()
    fun onDestroy()
}