package com.example.quizapp.repository.repoImpl
import android.net.Uri
import android.os.Bundle
import com.example.quizapp.firebase.AuthFirebase
import com.example.quizapp.firebase.FirebaseDb
import com.example.quizapp.firebase.FirebaseDocsStorage
import com.example.quizapp.model.Usuario
import com.example.quizapp.repository.IUsuarioBD



class UsuarioRepository(): IUsuarioBD {
    private val firebaseAuth =AuthFirebase()
    private val db = FirebaseDb()
    private val storage =FirebaseDocsStorage
    private  var uriImage : Uri? =null

     override
      suspend fun cadastrarUsuario(email: String, senha: String, nome:String):Usuario{
           try {
               val result = firebaseAuth.cadastrarUsuario(email,senha)
               val usuario = Usuario(
                   result.user?.uid.toString(),
                   nome,0,email,
                   "************",
                   uriImage.toString()
               )
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
        firebaseAuth.deslogarUsuario()
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
              uriImage = uri
           }
    }

    override
     suspend fun carregarImagemPerfil(usuario: Usuario):Uri {
         return  storage.dowloadImagem(usuario)
    }



}