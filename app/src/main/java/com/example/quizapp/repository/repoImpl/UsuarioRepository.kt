package com.example.quizapp.repository.repoImpl

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.quizapp.BuildConfig
import com.example.quizapp.firebase.AuthFirebase
import com.example.quizapp.firebase.FirebaseDb
import com.example.quizapp.firebase.FirebaseDocsStorage
import com.example.quizapp.model.Usuario
import com.example.quizapp.repository.IUsuarioBD
import com.example.quizapp.utils.Constantes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class UsuarioRepository(): IUsuarioBD {
    private val auth =FirebaseAuth.getInstance()
    private val firebaseAuth =AuthFirebase()
    private val db = FirebaseDb()
    private val storage =FirebaseDocsStorage
    private var abrirGaleria : ActivityResultLauncher<String>? = null
    private  var uriImage : Uri? =null


     override
      suspend fun cadastrarUsuario(email: String, senha: String, nome:String):Usuario{
           try {
               val result = auth.createUserWithEmailAndPassword(email,senha).await()
               val usuario = Usuario(
                   result.user?.uid.toString(),
                   nome,0,email,
                   "************",
                   uriImage.toString()
               )
               Log.i(Constantes.TAG, "cadastrarUsuarioURlPEr:${uriImage} ")
               if (uriImage != null ) storage.salvarImagem(usuario)
                 db.salvarUsuario(usuario)
                 return usuario
           }catch (ex:Exception){
               throw Exception(ex.message)
           }

    }

     override
       suspend fun logarUsuario(email:String, senha :String): Boolean {
        try {
             firebaseAuth.logarUsurio(email,senha)
            return  true
        }catch (e:RuntimeException){
            Log.i(Constantes.TAG, "logarUsuario: Erro ao logar Usuario ${e.message}")
            throw RuntimeException(e.message.toString())
        }
    }

    override
       suspend fun recuperarUsuarioLogado():Usuario {
      try {
               val user =  firebaseAuth.verificarUsuarioLogado()
               val doc =   db.recuperarUsuaarioLogado(user?.uid.toString())
               val dados = doc.data

               val usuario= Usuario(
                       dados?.get("id").toString(),
                       dados?.get("nome").toString(),
                       dados?.get("pontuacao").toString().toInt(),
                       dados?.get("email").toString(),
                       dados?.get("senha").toString(),
                       dados?.get("urlImagemPerfil").toString(),
                   )
                   return usuario

           }catch (ex:RuntimeException){
               throw RuntimeException(ex.message.toString())
           }

    }

    override
       fun verificarUsuariologdao(): Boolean {
           val isAuth =firebaseAuth.verificarUsuarioLogadoBool()
           if (isAuth)return true
        return false
    }

    override
        fun deslogarUsuario() {
       auth.signOut()
    }

    override
     fun salvarUsuario(usuario: Usuario) {
        db.salvarUsuario(usuario)
    }
     override
     fun convertBundleToUsuario(bundle: Bundle?): Usuario?{

        val usuario = bundle?.getParcelable<Usuario>("usuario")
        if (usuario != null) {
            return  usuario
        }
        return null
    }

     override
     fun recuperarUriImagen(uri: Uri?){
          if(uri != null){
              Log.i(Constantes.TAG, "recuperarUriImagen: ${uri}")
              uriImage = uri
           }
    }
     override
     fun abrirGaleria(galeria : ActivityResultLauncher<String>? ,){
              abrirGaleria = galeria
              abrirGaleria?.launch("image/**")
    }

    override
     suspend fun carregarImagemPerfil(usuario: Usuario):Uri {
        Log.i(Constantes.TAG, "Imagem uPerfil ${usuario.urlImagemPerfil}")
         return  storage.dowloadImagem(usuario)
    }

    override
     fun abrirRecusroFoto(permissao :String){
        when(permissao){
            Manifest.permission.READ_EXTERNAL_STORAGE->{
                Log.i(Constantes.TAG, "abrirRecusroFoto: galeria")
            }
            Manifest.permission.CAMERA ->{
                Log.i(Constantes.TAG, "abrirRecusroFoto: Camera")
            }
        }
    }
    override
        fun abrirCamera(abrirCamera: ActivityResultLauncher<Intent>? ){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
         abrirCamera?.launch(intent)
    }

    override
      fun adicionarFotoPorCamera(resultActivity: ActivityResult,context: Context) : Uri? {

        val bitmap = resultActivity.data?.extras?.get("data") as Bitmap
        val caminho = File(context.cacheDir, "images")

            try {
                caminho.mkdirs()
                val file = File(caminho,"image.jpg")
                val output = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                output.flush()
                output.close()
                uriImage = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".provider", file)
                Log.i(Constantes.TAG, "File--: ${file}")
                Log.i(Constantes.TAG, "Uri-file: ${uriImage}")

                return uriImage
            } catch (ex: RuntimeException) {
                throw RuntimeException("Erro : ${ex.message}-- ${ex.stackTrace}")
            }

    }
}