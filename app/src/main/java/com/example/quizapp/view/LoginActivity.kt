package com.example.quizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.quizapp.presenter.IUsuario
import com.example.quizapp.presenter.presenterImpl.CadastroPresenter
import com.example.quizapp.databinding.ActivityLoginBinding
import com.example.quizapp.presenter.ICadastroPresenter
import com.example.quizapp.presenter.ILoginPresenter
import com.example.quizapp.presenter.presenterImpl.LoginPresenter

class LoginActivity : AppCompatActivity(),IUsuario.IViewLogin {
     private val binding by lazy {
         ActivityLoginBinding.inflate(layoutInflater)
     }
    private lateinit var  usuarioPresenter : ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash = installSplashScreen()
       /* splash.setKeepOnScreenCondition(SplashScreen.KeepOnScreenCondition {
            true
        })*/
        setContentView(binding.root)
        usuarioPresenter = LoginPresenter(this)
    }


    override fun onStart() {
        super.onStart()
        usuarioPresenter.verificarUsuarioLogado()
    }

    override fun logarUsario(view: View) {
        binding.progressBarLogin.visibility=View.VISIBLE
        binding.idBtnLogin.visibility=View.GONE
        binding.txtCadastrar.visibility=View.GONE

        val email = binding.edtInputEmail.text.toString()
        val senha= binding.edtIntputSenha.text.toString()
        usuarioPresenter.logar(email, senha)
    }

    override fun abrirView() {
        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getToast(mensagem: String) {
             Toast.makeText(this@LoginActivity, mensagem, Toast.LENGTH_LONG).show()
        binding.progressBarLogin.visibility=View.GONE
        binding.idBtnLogin.visibility=View.VISIBLE
        binding.txtCadastrar.visibility=View.VISIBLE
    }

    override fun viewCadastro(view: View) {
        val intent = Intent(this@LoginActivity,CadastroActivity::class.java)
        startActivity(intent)
    }

}