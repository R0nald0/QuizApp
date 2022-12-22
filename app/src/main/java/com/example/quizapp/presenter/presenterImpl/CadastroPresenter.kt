package com.example.quizapp.presenter.presenterImpl

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.example.quizapp.config.permissões.Permissao
import com.example.quizapp.presenter.IUsuario
import com.example.quizapp.presenter.ICadastroPresenter
import com.example.quizapp.repository.IUsuarioBD
import com.example.quizapp.repository.repoImpl.UsuarioRepository
import com.example.quizapp.utils.Constantes
import kotlinx.coroutines.*

class CadastroPresenter (
    private var viewCadastro: IUsuario.IViewCadastro? = null,
    private  var  usuarioBd :IUsuarioBD?=null
       ) : ICadastroPresenter{

    private val  permissao = Permissao()

     init {
         usuarioBd = UsuarioRepository()
     }

    override
     fun cadastrarUsuario(email: String, senha :String, nome:String){
            CoroutineScope(Dispatchers.IO).launch{
                withContext(Dispatchers.Main){
                    viewCadastro?.exibirProgressBar()
                }
                try {
                    val resultado = usuarioBd?.cadastrarUsuario(email,senha,nome)
                    if (resultado != null) {
                        viewCadastro?.abrirView(resultado)
                    }
                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                        viewCadastro?.exibirToast(e.message.toString())
                        viewCadastro?.esconderProgressBar()
                    }
                }
            }
    }

    override
      fun carregarImagemPerfil(uri: Uri?){
        usuarioBd?.recuperarUriImagen(uri)
          if (uri !=null){
              viewCadastro?.carregarImagemPicasso(uri)
          }else{
              viewCadastro?.exibirToast("Nenhuma imagem seleicioda")
          }
    }

    override
      fun abrirGaleria(){

    }
    override
     fun verificarPermissao(permissao: Boolean) {

     if (permissao ){
         Log.i(Constantes.TAG, "verificarPermissao: ${permissao}")
         viewCadastro?.exibirToast("permissaoAceita")
         usuarioBd?.abrirGaleria(viewCadastro?.abrirGaleria())
          }else{

              viewCadastro?.exibirToast("permissaoNEgada")
          }
    }
    override
     fun requisitarPermissao(gerenciadorDePermissoao :ActivityResultLauncher<String>, permissaoNome:String, activity: Activity){
         val getPermissao =permissao.pedirPermissao(gerenciadorDePermissoao,permissaoNome,activity)
          if (!getPermissao) usuarioBd?.abrirGaleria(viewCadastro?.abrirGaleria())
            Log.i(Constantes.TAG, "pedirPermissao: ${getPermissao}")
      }

    override
      fun adicionarFotoPorCamera(resultActivity : ActivityResult) {
        usuarioBd?.adicionarFotoPorCamera(resultActivity)
    }
}