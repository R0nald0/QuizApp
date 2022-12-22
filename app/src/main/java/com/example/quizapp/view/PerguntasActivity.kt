package com.example.quizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.quizapp.presenter.presenterImpl.PerguntasPrensenter
import com.example.quizapp.model.Pergunta
import com.example.quizapp.databinding.ActivityPerguntasBinding
import com.example.quizapp.presenter.IPerguntasPresenter
import com.example.quizapp.view.interfaces.IPerguntasActivity

class PerguntasActivity : AppCompatActivity(),IPerguntasActivity{

    private val binding by  lazy {
        ActivityPerguntasBinding.inflate(layoutInflater)
    }
    private var perguntasPrensenter : IPerguntasPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        perguntasPrensenter = PerguntasPrensenter(this)
        carregarProximaPergunta()
    }

    override fun onStart() {
        super.onStart()
        perguntasPrensenter?.recuperarPerguntas()
        perguntasPrensenter?.recuperarUsuario()
    }

    override
    fun validarResposta(): Boolean{
        return (
                        binding.idRadioBtn1.isChecked ||
                        binding.idRadioBtn2.isChecked ||
                        binding.idRadioBtn3.isChecked
                )
    }

    override
    fun exibirMensgemToast(mensagem : String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    override
    fun checarRespostaSelecioda():Int {
      return  when{
            binding.idRadioBtn1.isChecked -> 0
            binding.idRadioBtn2.isChecked -> 1
            binding.idRadioBtn3.isChecked -> 2
            else -> -1
        }
    }

    override
    fun recuperarUsuarioBudle() :Bundle?{
       return intent.extras
    }

    override
      fun esconderProgressBar() {
         binding.progressBarCarregando.visibility = View.GONE
         binding.txvCarregando.visibility = View.GONE

         binding.linearLayoutPerguntas.visibility =View.VISIBLE
    }

    override
      fun exibirProgressBar() {
        binding.linearLayoutPerguntas.visibility =View.GONE

        binding.progressBarCarregando.visibility = View.VISIBLE
        binding.txvCarregando.visibility = View.VISIBLE

    }

    override
    fun carregarPerguntas() {
         perguntasPrensenter?.recuperarPerguntas()
    }

    override
    fun exibirPerguntas(pergunta: Pergunta ) {

        binding.idPerguntas.text = pergunta.titulo
        binding.idRadioBtn1.text = pergunta.resposta_0
        binding.idRadioBtn2.text = pergunta.resposta_1
        binding.idRadioBtn3.text = pergunta.resposta_2
        binding.idRadioGroup.clearCheck();
    }

    override
     fun exibirTotalPergunta(tamnhoLista : Int,perguntaIndice:Int) {
        binding.idTxvNumeroPerguntas.text ="${perguntaIndice + 1}  de  ${tamnhoLista} Perguntas"
    }

    override
     fun carregarProximaPergunta() {
        binding.idBtnConfirmarResp.setOnClickListener {
               perguntasPrensenter?.buscarProximaPergunta()
        }
    }

    override
      fun chamarVeiw() {
        val intent = Intent(this,ResultadoActivity::class.java)
        startActivity(intent)
        finish()
    }

    override
      fun onDestroy() {
        perguntasPrensenter?.onDestroy()
        super.onDestroy()
    }

}