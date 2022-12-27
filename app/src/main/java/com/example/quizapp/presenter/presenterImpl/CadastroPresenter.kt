package com.example.quizapp.presenter.presenterImpl

import android.Manifest
import android.app.Activity
import android.content.Context
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
     fun verificarPermissao(mapPermissao: Map<String, Boolean>) {
           val permis =  permissao.checarPermissao(mapPermissao)

        when(permis){
            Manifest.permission.CAMERA-> {
                if (mapPermissao[permis] == true){
                    viewCadastro?.exibirToast("permissaoAceita")
                    usuarioBd?.abrirCamera(viewCadastro?.abrirCameraOnclik())
                }
                else viewCadastro?.exibirToast("Permissao Negada")
            }

            Manifest.permission.READ_EXTERNAL_STORAGE -> {

                if (mapPermissao[permis] == true){
                    viewCadastro?.exibirToast("permissaoAceita")
                    usuarioBd?.abrirGaleria(viewCadastro?.abrirGaleria())
                }
                else viewCadastro?.exibirToast("permissao Negada")

            }
        }

    }
    override
     fun requisitarPermissao(gerenciadorDePermissoao : ActivityResultLauncher<Array<String>>, permissaoNome:String, activity: Activity){
         val getPermissao =permissao.pedirPermissao(gerenciadorDePermissoao,permissaoNome,activity)

          if (!getPermissao) {
              if (permissaoNome == Manifest.permission.READ_EXTERNAL_STORAGE){
                  usuarioBd?.abrirGaleria(viewCadastro?.abrirGaleria())
              }else{
                  usuarioBd?.abrirCamera(viewCadastro?.abrirCameraOnclik())
              }
          }

      }

    override
      fun adicionarFotoPorCamera(resultActivity : ActivityResult,context: Context) {
             val uri = usuarioBd?.adicionarFotoPorCamera(resultActivity,context)

        if (uri !=null ){
            viewCadastro?.carregarImagemPicasso(uri)
        }else{
            viewCadastro?.exibirToast("Nenhuma foto foi tirada")
        }

    }
}