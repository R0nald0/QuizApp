package com.example.quizapp.presenter.presenterImpl


import android.util.Log
import com.example.quizapp.view.interfaces.IMain
import com.example.quizapp.presenter.IMainPresenter
import com.example.quizapp.repository.repoImpl.UsuarioRepository
import com.example.quizapp.utils.Constantes
import kotlinx.coroutines.*


class MainPresenter(private val viewMain : IMain? =null) : IMainPresenter{
    private val usuarioRepository = UsuarioRepository()
    var job :Job? = null


    override fun recuperarDadosUsuario() {
         job =CoroutineScope(Dispatchers.IO).launch {
               withContext(Dispatchers.Main){
                   viewMain?.exibirProgressBar()
               }
            try {
                 val usuario = usuarioRepository.recuperarUsuarioLogado()
                  withContext(Dispatchers.Main){

                      viewMain?.addItensView(usuario)
                      viewMain?.esconderProgressBar()
                  }
             }catch (ex:Exception){
                withContext(Dispatchers.Main){
                    usuarioRepository.deslogarUsuario()
                    viewMain?.deslogarUsuario()
                    Log.i(Constantes.TAG, "Erro: ${ex.message} -- ${ex.stackTrace}")
                    viewMain?.mostrarMsg(ex.message.toString())
                }

             }
        }
    }

    override
    fun deslogarUsuario() {
        usuarioRepository.deslogarUsuario()
        viewMain?.deslogarUsuario()
    }

    override
     fun iniciarQuiz() {
        job=CoroutineScope(Dispatchers.IO).launch {
          try {
              val usuario = usuarioRepository.recuperarUsuarioLogado()
              viewMain?.iniciarQuiz(usuario)
          }catch (ex:Exception){
              withContext(Dispatchers.Main){
                  viewMain?.mostrarMsg("Algo deu errado ao logar o usuario")
                  viewMain?.deslogarUsuario()
              }
          }
      }

    }

    override fun onDestroy() {
       job?.cancel()
    }

}