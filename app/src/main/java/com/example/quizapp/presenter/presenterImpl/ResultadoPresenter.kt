package com.example.quizapp.presenter.presenterImpl

import android.util.Log
import com.example.quizapp.repository.IUsuarioBD
import com.example.quizapp.repository.repoImpl.UsuarioRepository
import com.example.quizapp.utils.Constantes
import com.example.quizapp.view.interfaces.IResultado
import kotlinx.coroutines.*

class ResultadoPresenter(private val viewResultado : IResultado? =null) {

    var usuarioRepository : IUsuarioBD? = null
    var job: Job? = null

    init {
        usuarioRepository = UsuarioRepository()
    }

   fun recuperarUsuario(){
     job =  CoroutineScope(Dispatchers.IO).launch {
           val usuario = usuarioRepository?.recuperarUsuarioLogado()
           if (usuario !=null ){
               Log.i(Constantes.TAG, "${usuario.nome} pontuacao= ${usuario.pontuacao} ")
               withContext(Dispatchers.Main){
                   viewResultado?.exibirCardUSuario(usuario)
               }
           }else{
             withContext(Dispatchers.Main){
                 viewResultado?.exbirMensagemToast("Usuario não encontrado")
             }
           }
       }
      }

    fun deslogar(){
        usuarioRepository?.deslogarUsuario()
        viewResultado?.deslogar()
    }

    fun onDestroy(){
        job?.cancel()
    }

}