package com.example.quizapp.view.interfaces

import android.os.Bundle
import com.example.quizapp.model.Pergunta
import com.example.quizapp.model.Usuario

interface IPerguntasActivity  {
    fun carregarPerguntas()
    fun exibirPerguntas(pergunta: Pergunta)
    fun exibirTotalPergunta(tamnhoLista : Int,perguntaIndice:Int)
    fun carregarProximaPergunta()
    fun chamarVeiw()
    fun validarResposta(): Boolean
    fun exibirMensgemToast(mensgem : String)
    fun checarRespostaSelecioda():Int
    fun recuperarUsuarioBudle(): Bundle?
    fun esconderProgressBar()
    fun exibirProgressBar()
}