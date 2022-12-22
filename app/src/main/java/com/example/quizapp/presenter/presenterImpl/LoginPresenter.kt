package com.example.quizapp.presenter.presenterImpl

import com.example.quizapp.presenter.ILoginPresenter
import com.example.quizapp.presenter.IUsuario
import com.example.quizapp.repository.IUsuarioBD
import com.example.quizapp.repository.repoImpl.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter(
    private  var viewLogin :  IUsuario.IViewLogin? = null,
    private  var  usuarioBd : IUsuarioBD?=null
) : ILoginPresenter {
    init {
        usuarioBd = UsuarioRepository()
    }

    override
    fun logar(email: String, senha: String){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val resultado = usuarioBd?.logarUsuario(email,senha)
                withContext(Dispatchers.Main){
                    viewLogin?.getToast("Entrando...")
                }
                viewLogin?.abrirView()
            }catch (ex:RuntimeException){
                withContext(Dispatchers.Main){
                    viewLogin?.getToast("${ex.message.toString()} ")
                }
            }

        }

    }

    override
    fun verificarUsuarioLogado(){
        if (usuarioBd?.verificarUsuariologdao() == true)viewLogin?.abrirView()
    }
}