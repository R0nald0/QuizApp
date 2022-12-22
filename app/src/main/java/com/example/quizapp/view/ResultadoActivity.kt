package com.example.quizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuProvider
import com.example.quizapp.R
import com.example.quizapp.model.Usuario
import com.example.quizapp.databinding.ActivityResultadoBinding
import com.example.quizapp.presenter.presenterImpl.ResultadoPresenter
import com.example.quizapp.view.interfaces.IResultado

class ResultadoActivity : AppCompatActivity(),IResultado {
     val binding by lazy {
        ActivityResultadoBinding.inflate(layoutInflater)
    }

    private var resultadoPresenter : ResultadoPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
            resultadoPresenter = ResultadoPresenter(this)
           carregarMenu()

    }

    override
    fun onStart() {
        super.onStart()
        binding.cardViewResultado.visibility = View.GONE
        binding.btnNovoQuiz.visibility = View.GONE
        binding.fragmentContainerView2.visibility = View.GONE

        resultadoPresenter?.recuperarUsuario()

    }

    override
    fun onDestroy() {
        resultadoPresenter?.onDestroy()
        super.onDestroy()
    }

    override
    fun novoQuis(view :View){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
    }

    override
    fun deslogar(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
    }

    override fun exibirCardUSuario(usuario: Usuario) {
        binding.cardViewResultado.visibility = View.VISIBLE
        binding.progressBarResultado.visibility = View.GONE
        binding.btnNovoQuiz.visibility = View.VISIBLE
        binding.fragmentContainerView2.visibility = View.VISIBLE
        binding.idPontuacao.text = usuario.pontuacao.toString()
        binding.nomUser.text = usuario.nome.replaceFirstChar { it.uppercase() }
    }

    override fun exbirMensagemToast(mensegem: String) {
        Toast.makeText(this, mensegem, Toast.LENGTH_LONG).show()
    }

    override
    fun recuperarUsuarioBudle(): Bundle? {
         return  intent.extras
    }

    override
    fun carregarMenu(){
          addMenuProvider(object :MenuProvider{
              override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                   menuInflater.inflate(R.menu.menu_main,menu)
              }

              override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
               return  when(menuItem.itemId){
                      R.id.iconSair-> {
                          resultadoPresenter?.deslogar()
                          true
                      }
                       R.id.iconConfiguracao->{
                           Toast.makeText(applicationContext, "Configuracao", Toast.LENGTH_LONG).show()
                           true
                       }
                      R.id.iconEdit->{
                        Toast.makeText(applicationContext, "Editar Perfil", Toast.LENGTH_LONG).show()
                        true
                   }
                   else -> true
               }

              }

          })
    }
}