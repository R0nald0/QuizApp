package com.example.quizapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.adpater.UsuarioAdpter
import com.example.quizapp.databinding.FragmentRankingBinding
import com.example.quizapp.firebase.FirebaseDb
import com.example.quizapp.model.Usuario

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RankingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RankingFragment : Fragment() {

    private val binding by lazy {
        FragmentRankingBinding.inflate(layoutInflater)
    }

    var listUserRanking = mutableListOf<Usuario>()
    private  var usuarioAdpter : UsuarioAdpter? = null
    private lateinit var linearLayoutManager : LinearLayoutManager
    private val firebaseDb = FirebaseDb()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usuarioAdpter = UsuarioAdpter()
        linearLayoutManager = LinearLayoutManager(this.context)
        binding.rcvRanking.adapter =  usuarioAdpter
        binding.rcvRanking.layoutManager = linearLayoutManager

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val view =  binding.root

      return view
    }

    override fun onStart() {
        super.onStart()
        firebaseDb.recupercarListaPontuacao{
            carregarRankingUsuarios(it)
        }
    }
    fun carregarRankingUsuarios(list: MutableList<Usuario>){
        usuarioAdpter?.carregarLista(list)
    }

}