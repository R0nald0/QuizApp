package com.example.quizapp.presenter.presenterImpl

import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Contactables
import android.util.Log
import com.example.quizapp.model.Usuario
import com.example.quizapp.presenter.IPerfilPresenter
import com.example.quizapp.repository.IUsuarioBD
import com.example.quizapp.repository.repoImpl.UsuarioRepository
import com.example.quizapp.utils.Constantes
import com.example.quizapp.view.interfaces.IPerfilActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerfilPresenter(private val viewIPerfilActivity: IPerfilActivity) : IPerfilPresenter {
    var usuarioBd :IUsuarioBD? =null
      var  usuario: Usuario

    init {
        usuarioBd = UsuarioRepository()
        usuario = usuarioBd?.convertBundleToUsuario(viewIPerfilActivity.carregarBundle())!!
    }

    override fun recuperarBundleParaUsuario() {
        CoroutineScope(Dispatchers.IO).launch {
            val  uri = usuarioBd?.carregarImagemPerfil(usuario)
           withContext(Dispatchers.Main){
               viewIPerfilActivity.recuperarDadosUsuario(usuario,uri)
           }
        }
    }

    override fun atualizarUsuario(nome : String){
        if ( nome.length > 3  ){
            usuario.nome =nome
           CoroutineScope(Dispatchers.IO).launch {
               try {
                   usuarioBd?.salvarUsuario(usuario);
                   usuarioBd?.salvarPontuacaoUsuario(usuario)
                   viewIPerfilActivity.mudarView()

               }catch (ex :Exception){
                    viewIPerfilActivity.exibirToast("Erro ao alterar dados")
                    Log.i(Constantes.TAG, "atualizarUsuario: ${ex.stackTrace}")
               }
           }
        }else{
            viewIPerfilActivity.exibirToast("preencha o nome ")
        }

    }

   override fun onDestroy(){
        usuarioBd = null
    }
}