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

import com.example.quizapp.view.interfaces.IMain
import com.example.quizapp.presenter.presenterImpl.MainPresenter
import com.example.quizapp.databinding.ActivityMainBinding

import com.example.quizapp.R
import com.example.quizapp.model.Usuario

class MainActivity : AppCompatActivity(), IMain {

  private val binding by lazy {
      ActivityMainBinding.inflate(layoutInflater)
  }
    private var mainPresenter:MainPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainPresenter = MainPresenter(this)
        configuraPopUpMenu()

        binding.idBtnIniciar.setOnClickListener {
            mainPresenter?.iniciarQuiz()
        }
    }

    override fun onStart() {
        super.onStart()
        mainPresenter?.recuperarDadosUsuario()

    }
     override
     fun iniciarQuiz( usuario: Usuario){
            val intent = Intent(this, PerguntasActivity::class.java)
             intent.putExtra("usuario",usuario)
             startActivity(intent)
             finish()
     }

    override fun esconderProgressBar() {
        binding.linearLayout.visibility = View.VISIBLE
        binding.fragmentContainerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    override fun exibirProgressBar() {
        binding.progressBar.visibility =View.VISIBLE

        binding.linearLayout.visibility = View.GONE
        binding.fragmentContainerView.visibility =View.GONE
    }

    override
     fun recuperarIntent(): Bundle? {
        return intent.extras
    }

    override
    fun addItensView(nome : String) {
        binding.apply {
            txtBemVindo.text ="Olá,${nome.replaceFirstChar { it.uppercase() }},Bem-Vindo "
        }
    }

    override
    fun mostrarMsg(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    override
    fun deslogarUsuario() {
        val intent = Intent(this@MainActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private
    fun configuraPopUpMenu(){
        addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                 menuInflater.inflate(R.menu.menu_main,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
              return  when(menuItem.itemId){
                  R.id.iconSair -> {
                         mainPresenter?.deslogarUsuario()
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
                  else-> true
              }

            }

        })
    }

    override fun onDestroy() {
        mainPresenter?.onDestroy()
        mainPresenter = null
        super.onDestroy()
    }
}
