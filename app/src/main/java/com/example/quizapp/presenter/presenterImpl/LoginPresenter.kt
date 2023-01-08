package com.example.quizapp.presenter.presenterImpl

import com.example.quizapp.presenter.ILoginPresenter
import com.example.quizapp.presenter.IUsuario
import com.example.quizapp.repository.IUsuarioBD
import com.example.quizapp.repository.repoImpl.UsuarioRepository
import com.example.quizapp.utils.VerificadorConta
import com.google.firebase.FirebaseTooManyRequestsException
import kotlinx.coroutines.*

class LoginPresenter(
    private  var viewLogin :  IUsuario.IViewLogin? = null,
    private  var  usuarioBd : IUsuarioBD?=null
) : ILoginPresenter {
     val verificadorConta = VerificadorConta()
    var job :Job? =null

    init {
        usuarioBd = UsuarioRepository()
    }

    override fun checkErroLogin(text :CharSequence?,campo: Int){
            when(campo){
                 0->{
                    val texto = verificadorConta.verificarCamposEmail(text);
                    if (texto){
                        viewLogin?.exibirErroMensagerEmail("Seu email deve conter @")
                    }else{
                        viewLogin?.esconderErroMensagerEmail()
                    }
                }
                1->{
                    val texto = verificadorConta.verificarCampoLogin(text);
                    if (texto){
                        viewLogin?.exibirErroMensager("Digite uma senha com mais de 5 caracteres")
                    }else{
                        viewLogin?.esconderErroMensager()
                    }
                }
            }
    }


    override
    fun logar(email: String, senha: String){
        viewLogin?.exibirProgressBar()
        job = CoroutineScope(Dispatchers.IO).launch {
                 try{
                     val resultado = usuarioBd?.logarUsuario(email,senha)
                         withContext(Dispatchers.Main){
                             viewLogin?.getToast("Entrando...")
                             viewLogin?.abrirView()
                         }
                 }
                 catch (ex: FirebaseTooManyRequestsException){
                     withContext(Dispatchers.Main){
                         viewLogin?.getToast("Muitas tentativas,acesso está temporariamente desativado")
                         viewLogin?.esconderProgressBar()
                     }
                 }
                 catch (ex:Exception){
                     withContext(Dispatchers.Main){
                         viewLogin?.getToast("Verifique os campos e preencha corretamente,Email ou Senha inválido")
                         viewLogin?.esconderProgressBar()
                         ex.printStackTrace()
                     }
                 }

             }


    }

    override
    fun verificarUsuarioLogado(){
        if (usuarioBd?.verificarUsuariologdao() == true)viewLogin?.abrirView()
    }

    override fun onDestroy() {
        job?.cancel()
    }
}