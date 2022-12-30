package com.example.quizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.widget.doOnTextChanged
import com.example.quizapp.presenter.IUsuario
import com.example.quizapp.databinding.ActivityLoginBinding
import com.example.quizapp.presenter.ILoginPresenter
import com.example.quizapp.presenter.presenterImpl.LoginPresenter
import com.example.quizapp.utils.Constantes

class LoginActivity : AppCompatActivity(),IUsuario.IViewLogin {
     private val binding by lazy {
         ActivityLoginBinding.inflate(layoutInflater)
     }
    private lateinit var  presenterLogin : ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash = installSplashScreen()
       /* splash.setKeepOnScreenCondition(SplashScreen.KeepOnScreenCondition {
            true
        })*/
        setContentView(binding.root)
        presenterLogin = LoginPresenter(this)
        binding.txtInputSenha.isEndIconVisible =false
        binding.txtInputEmail.isEndIconVisible =false

        checkErroLogin()

    }

    override fun  checkErroLogin(){
        binding.edtIntputSenha.doOnTextChanged { text, start, before, count ->
             //1 = campo de senha
            presenterLogin.checkErroLogin(text,1)
        }
        binding.edtInputEmail.doOnTextChanged { text, start, before, count ->
             //0 = campo de email
            presenterLogin.checkErroLogin(text,0)

        }
    }

    override fun exibirErroMensagerEmail(mensagem: String){
        binding.txtInputEmail.error = mensagem
    }
    override fun exibirErroMensager(mensagem: String){
        binding.txtInputSenha.error = mensagem
    }

    override fun esconderErroMensager(){
        binding.txtInputSenha.error =null
        binding.txtInputSenha.isEndIconVisible =true
    }

    override fun esconderErroMensagerEmail() {
        binding.txtInputEmail.error =null
        binding.txtInputEmail.isEndIconVisible =true
    }

    override fun onStart() {
        super.onStart()
        presenterLogin.verificarUsuarioLogado()
    }

    override fun logarUsario(view: View) {
        binding.progressBarLogin.visibility=View.VISIBLE
        binding.idBtnLogin.visibility=View.GONE
        binding.txtCadastrar.visibility=View.GONE


        val email = binding.edtInputEmail.text.toString()
        val senha= binding.edtIntputSenha.text.toString()
        presenterLogin.logar(email, senha)
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