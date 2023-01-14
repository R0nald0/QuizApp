package com.example.quizapp.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityPerfilBinding
import com.example.quizapp.model.Usuario
import com.example.quizapp.presenter.IPerfilPresenter
import com.example.quizapp.presenter.presenterImpl.PerfilPresenter
import com.example.quizapp.utils.Constantes
import com.example.quizapp.view.interfaces.IPerfilActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerfilActivity() : AppCompatActivity(),IPerfilActivity {
    private val binding by lazy{
        ActivityPerfilBinding.inflate(layoutInflater)
    }
     private var perfilPresenter: IPerfilPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        perfilPresenter = PerfilPresenter(this)

         binding.button.setOnClickListener {
              editar()
           }

        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        perfilPresenter?.recuperarBundleParaUsuario()
    }

    override fun carregarBundle(): Bundle? {
        return  intent.extras
    }

    override fun recuperarDadosUsuario(usuario: Usuario,uri: Uri?) {
        binding.txtInputNme.hint = usuario.nome
        if (uri !=null){
            Picasso.get().load(uri).into(binding.imageView)
            Log.i(Constantes.TAG, "onMenuItemSelected: ${uri}")
        }else{
            Picasso.get().load(R.drawable.perfil_imagem).into(binding.imageView)
        }
    }

    override fun exibirToast(mensgem: String) {
        Toast.makeText(this, mensgem, Toast.LENGTH_LONG).show()
    }

     fun editar(){
         perfilPresenter?.atualizarUsuario(binding.edtInputNme.text.toString())
     }
    override
     fun mudarView(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        perfilPresenter = null
        perfilPresenter?.onDestroy()
        super.onDestroy()
    }
}

