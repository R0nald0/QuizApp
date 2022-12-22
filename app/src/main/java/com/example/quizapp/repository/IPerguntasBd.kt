package com.example.quizapp.repository

import android.os.Bundle
import com.example.quizapp.model.Pergunta
import com.example.quizapp.model.Usuario

interface IPerguntasBd {
    suspend fun recuperarPerguntas()
    suspend fun obterPergunta(bundle: Bundle?): Pergunta?
    fun listaPerguntasTamanho():Int
    fun recuperarIndexPergunta():Int
    fun verificarRespostaEscolhida(indexRespostaSelecionado : Int?)
    suspend fun salvarAcertos(usuario: Usuario)
}