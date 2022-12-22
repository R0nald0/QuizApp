package com.example.quizapp.repository.repoImpl

import android.os.Bundle
import android.util.Log
import com.example.quizapp.firebase.FirebaseDb
import com.example.quizapp.model.Pergunta
import com.example.quizapp.model.Usuario
import com.example.quizapp.repository.IPerguntasBd
import com.example.quizapp.repository.IUsuarioBD
import com.example.quizapp.utils.Constantes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerguntasRepository :IPerguntasBd {
    private val firebaseDb = FirebaseDb()
    private var indexPergunta = 0
    private var totalAcertos = 0
    private val perguntaList = mutableListOf<Pergunta>()
    private var usuarioRepository :IUsuarioBD? = null

    init {
        usuarioRepository = UsuarioRepository()
    }

    override
    suspend fun recuperarPerguntas(){
          try {
              val query = firebaseDb.recuperarPerguntas()
               query.documents.forEach {  it ->
                   val list = it["Resposta"] as List<*>
                   val titulo = it["Titulo"].toString()
                   val respostaCerta = it["RespostaCerta"].toString().toInt()
                   val pergunta = Pergunta(
                       titulo,
                       list.get(0).toString(),
                       list.get(1).toString(),
                       list.get(2).toString(),
                       respostaCerta
                   )
                   perguntaList.add(pergunta)
               }

         }catch (ex:RuntimeException){
             throw RuntimeException("${ex.message}")
         }
    }

   override
     suspend fun obterPergunta(bundle: Bundle?): Pergunta? {

        if (perguntaList.isNotEmpty()){

            if ( indexPergunta  < perguntaList.size ){
                  return perguntaList[indexPergunta]
            }else{
                val usuario = usuarioRepository?.convertBundleToUsuario(bundle)
                if (usuario !=null){
                    usuario.pontuacao = totalAcertos
                    salvarAcertos(usuario)
                }
                return null
            }
        }
          return null
    }
     override
       fun listaPerguntasTamanho():Int = perguntaList.size
    override
       fun recuperarIndexPergunta():Int = indexPergunta++
    override
      fun verificarRespostaEscolhida(indexRespostaSelecionado : Int?){
                 val pergunta = perguntaList[indexPergunta - 1]
                 if (pergunta.respostaCerta == indexRespostaSelecionado){
                      totalAcertos += 1
                 }
    }
   override
      suspend fun salvarAcertos(usuario: Usuario){
           try {
                val checkSalvarPontuacao = firebaseDb.salvarPontuacaoUsuario(usuario)
                if (checkSalvarPontuacao) usuarioRepository?.salvarUsuario(usuario)
           }catch (ex :RuntimeException){
               throw RuntimeException("${ex.message} -- ${ex.stackTrace}")
           }

     }


}