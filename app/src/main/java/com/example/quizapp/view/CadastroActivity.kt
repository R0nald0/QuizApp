package com.example.quizapp.view

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.quizapp.presenter.presenterImpl.CadastroPresenter
import com.example.quizapp.databinding.ActivityCadastroBinding
import com.example.quizapp.model.Usuario
import com.example.quizapp.presenter.IUsuario
import com.example.quizapp.presenter.ICadastroPresenter
import com.squareup.picasso.Picasso

class CadastroActivity : AppCompatActivity(),IUsuario.IViewCadastro{
    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }
      private  var abrirGaleria : ActivityResultLauncher<String>? = null
      private  var  abrirCamera: ActivityResultLauncher<Intent>? = null
      private lateinit var gerenciadorDePermissoao :  ActivityResultLauncher<Array<String>>

      private lateinit var presenter: ICadastroPresenter


      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
         presenter = CadastroPresenter(this)
          requisistarPermissao()
          contratoGetContent()
           contratoStartActivty()


        binding.imgBtnGaleria.setOnClickListener {
           presenter.requisitarPermissao(gerenciadorDePermissoao,
               Manifest.permission.READ_EXTERNAL_STORAGE,this)
        }

          binding.imgBtnCamera.setOnClickListener{
              presenter.requisitarPermissao(
                  gerenciadorDePermissoao,
                   Manifest.permission.CAMERA,this)
          }

    }

    override
    fun requisistarPermissao(){
      gerenciadorDePermissoao  = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
              presenter.verificarPermissao(it)
            }
    }

    override
    fun abrirGaleria(): ActivityResultLauncher<String>?{
        return abrirGaleria
    }

    override fun exibirProgressBar() {
        binding.progressBar2.visibility=View.VISIBLE
        binding.btnCadastrar.visibility=View.GONE
        binding.imgBtnGaleria.visibility=View.GONE
        binding.imgBtnCamera.visibility=View.GONE
    }

    override fun esconderProgressBar() {
        binding.progressBar2.visibility=View.GONE
        binding.btnCadastrar.visibility=View.VISIBLE
        binding.imgBtnGaleria.visibility=View.VISIBLE
        binding.imgBtnCamera.visibility=View.VISIBLE
    }

    override fun contratoGetContent() {
        abrirGaleria = registerForActivityResult(ActivityResultContracts.GetContent()){
            presenter.carregarImagemPerfil(it)
        }
    }

    override fun abrirCameraOnclik():ActivityResultLauncher<Intent>?{
       return abrirCamera
    }

    override fun contratoStartActivty() {
       abrirCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            presenter.adicionarFotoPorCamera(it,this)
         }
    }

    override
     fun cadastrarUsuario(view: View) {
        binding.apply {
            val nome = edtInputLayoutNome.text.toString()
            val email = edtInputLayoutEmail.text.toString()
            val senha= edtInputLayoutSenha.text.toString()
            presenter.cadastrarUsuario(email,senha,nome)
        }
    }

    override
     fun abrirView(usuario: Usuario) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("usuario",usuario)
        startActivity(intent)
        finish()
    }

    override
     fun exibirToast(mensagem : String){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    override
     fun carregarImagemPicasso(uri :Uri){
          Picasso.get().load(uri).into(binding.perfilImagem)
     }



}

/* override fun onRequestPermissionsResult(
       requestCode: Int,
       permissions: Array<out String>,
       grantResults: IntArray
   ) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults)
       permissions.forEachIndexed { index, s ->  }

      grantResults.forEachIndexed { index, i ->
          if ( i == 0){
          abrirGaleria?.launch("image/*")
      }else{
          Toast.makeText(this, "Permissao Negada", Toast.LENGTH_LONG).show()
       }
      }
   }*/