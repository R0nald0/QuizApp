package com.example.quizapp.presenter.presenterImpl

import com.example.quizapp.presenter.IPerguntasPresenter
import com.example.quizapp.repository.IPerguntasBd
import com.example.quizapp.repository.IUsuarioBD
import com.example.quizapp.repository.repoImpl.PerguntasRepository
import com.example.quizapp.repository.repoImpl.UsuarioRepository
import com.example.quizapp.view.interfaces.IPerguntasActivity
import kotlinx.coroutines.*

class PerguntasPrensenter(
    private val viewPergunta:IPerguntasActivity?=null
): IPerguntasPresenter {

      var perguntasBd : IPerguntasBd? = null
      var usuarioBd : IUsuarioBD? = null
      var job: Job? = null

      init {
           usuarioBd = UsuarioRepository()
           perguntasBd = PerguntasRepository()
      }
      override
       fun recuperarPerguntas(){
          job  = CoroutineScope(Dispatchers.IO).launch {
              viewPergunta?.exibirProgressBar()
              perguntasBd?.recuperarPerguntas()
              val pergunta = perguntasBd?.obterPergunta( viewPergunta?.recuperarUsuarioBudle())
              withContext(Dispatchers.Main){
                 val usuario = usuarioBd?.convertBundleToUsuario( viewPergunta?.recuperarUsuarioBudle())
                  exibirInformacaoLista()
                  if (pergunta != null && usuario !=null) {
                      viewPergunta?.exibirPerguntas(pergunta)
                      viewPergunta?.esconderProgressBar()
                  }
              }
         }
      }
      override
        fun exibirInformacaoLista(){
          perguntasBd?.let {
              viewPergunta?.exibirTotalPergunta(
                  it.listaPerguntasTamanho(),
                  it.recuperarIndexPergunta()
              )
          }
     }
      override
        fun buscarProximaPergunta(){
          perguntasBd?.verificarRespostaEscolhida(
              viewPergunta?.checarRespostaSelecioda()
          )
          val respostaCheck = viewPergunta?.validarResposta()
            if (respostaCheck == true){
                   CoroutineScope(Dispatchers.IO).launch {
                       val pergunta = perguntasBd?.obterPergunta( viewPergunta?.recuperarUsuarioBudle())

                       if (pergunta != null) {
                              val tamanhoLista = perguntasBd?.listaPerguntasTamanho()
                              val indicePergunta = perguntasBd?.recuperarIndexPergunta()
                           if (tamanhoLista !=null && indicePergunta !=null){
                               withContext(Dispatchers.Main){
                                   viewPergunta?.exibirTotalPergunta(
                                       tamanhoLista,
                                       indicePergunta
                                   )
                                   viewPergunta?.exibirPerguntas(pergunta)
                               }
                           }


                       }else{
                           withContext(Dispatchers.Main){
                               viewPergunta?.chamarVeiw()
                           }
                       }
                   }


            }else{
                viewPergunta?.exibirMensgemToast("Selecione uma Resposta")
            }

       }
      override
        fun recuperarUsuario(){

      }
      override
        fun onDestroy(){
        job?.cancel()
    }
}
