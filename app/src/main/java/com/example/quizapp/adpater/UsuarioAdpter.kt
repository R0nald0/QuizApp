package com.example.quizapp.adpater

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.databinding.ItemRcViewBinding
import com.example.quizapp.model.Usuario
import com.example.quizapp.repository.repoImpl.UsuarioRepository
import com.example.quizapp.utils.Constantes
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioAdpter(

) :RecyclerView.Adapter<UsuarioAdpter.UsuarioViewHolder>() {
     var listPontaucao  = mutableListOf<Usuario>()
    val usuarioRepository = UsuarioRepository()


    fun carregarLista(list : MutableList<Usuario> ){
         listPontaucao.addAll(list)
        notifyDataSetChanged()
    }
    inner class UsuarioViewHolder(item :ItemRcViewBinding) : RecyclerView.ViewHolder(item.root){

        private lateinit var binding: ItemRcViewBinding
        init {
              binding= item
        }

        fun bind(usuario: Usuario,position: Int){
            binding.nomeUser.text = usuario.nome
            CoroutineScope(Dispatchers.IO).launch {

                if (usuario.urlImagemPerfil !="null"){
                    val uri = usuarioRepository.carregarImagemPerfil(usuario)
                    withContext(Dispatchers.Main){
                        Picasso.get().load(uri).into(binding.imgPerfil)
                    }
                }else{
                    withContext(Dispatchers.Main){
                        Picasso.get().load(R.drawable.perfil_imagem).into(binding.imgPerfil)
                    }
                }
            }

                binding.pontuacaoUser.text = "${usuario.pontuacao} Pts"
                val posicao = position +1
                binding.txvPosicao.text = "${posicao} "

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "CLICADO", Toast.LENGTH_LONG).show()
                //notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {

        val itemBindinUser = ItemRcViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UsuarioViewHolder(itemBindinUser)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
          val usuario = listPontaucao[position]
          holder.bind(usuario,position)
    }

    override fun getItemCount(): Int {
        return  listPontaucao.size
    }


}