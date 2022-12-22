package com.example.quizapp.presenter.presenterImpl


import com.example.quizapp.view.interfaces.IMain
import com.example.quizapp.presenter.IMainPresenter
import com.example.quizapp.repository.repoImpl.UsuarioRepository
import kotlinx.coroutines.*


class MainPresenter(private val viewMain : IMain? =null) : IMainPresenter{
    private val usuarioRepository = UsuarioRepository()

    override fun recuperarDadosUsuario() {
          CoroutineScope(Dispatchers.IO).launch {
             try {
                 viewMain?.exibirProgressBar()
                 val usuario = usuarioRepository.recuperarUsuarioLogado()
                  withContext(Dispatchers.Main){
                      viewMain?.addItensView(usuario.nome)
                      viewMain?.esconderProgressBar()
                  }

             }catch (ex:RuntimeException){
                withContext(Dispatchers.Main){
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
      CoroutineScope(Dispatchers.IO).launch {
          val usuario = usuarioRepository.recuperarUsuarioLogado()
          viewMain?.iniciarQuiz(usuario)
      }

    }

}