package com.example.quizapp.firebase

import android.util.Log
import com.example.quizapp.model.Usuario
import com.example.quizapp.utils.Constantes
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.tasks.await
import kotlin.math.E


class FirebaseDb  {

       private val firebaseDb = FirebaseFirestore.getInstance()

       suspend fun recuperarPerguntas():QuerySnapshot {
          return try {
               firebaseDb.collection(Constantes.DOC_PERGUNTAS_BD).get().await()
           }catch (ex:RuntimeException){
              Log.i(Constantes.TAG, "Erro ao Carregar perguntas ${ex.message}--${ex.stackTrace}")
               throw RuntimeException(ex.message)
           }
       }

       suspend fun salvarPontuacaoUsuario(usuario :Usuario):Boolean{
          return try {
              firebaseDb.collection(Constantes.DOC_RANKING_BD)
                  .document(usuario.id)
                  .set(usuario)
                  .await()
              return  true
          }catch (ex:Exception){
               throw Exception("${ex.message} -- ${ex.stackTrace}")
          }

    }

       fun recupercarListaPontuacao(funcaoCarregar: (MutableList<Usuario>) -> Unit){
        firebaseDb.collection(Constantes.DOC_RANKING_BD)
            .orderBy("pontuacao",Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->

                val listaDocument =value?.documents
                if (listaDocument  != null){
                    val list = mutableListOf<Usuario>()

                    listaDocument.forEach {
                        val itemDado =it.data
                        val id =it.get("id").toString()
                        val nome = itemDado?.get("nome").toString()
                        val pontuacao = itemDado?.get("pontuacao").toString().toInt()
                        val urlImage =itemDado?.get("urlImagemPerfil").toString()
                        val usuario = Usuario(id,nome,pontuacao,"","",urlImage)
                        list.add(usuario)
                    }
                    Log.i("INFO_", "recupercarfb: $list")
                    funcaoCarregar(list)
                }
            }
    }

       fun salvarUsuario(usuario: Usuario) {
                    firebaseDb.collection(Constantes.DOC_USUARIO_BD)
                        .document(usuario.id)
                        .set(usuario)
                        .addOnSuccessListener {
                            Log.i(Constantes.TAG, "salvarUsuario: ${usuario.nome}")
                        }
                        .addOnFailureListener {
                            Log.i(Constantes.TAG, "Erro ao salvar: ${it.message} -- ${it.stackTrace}")
                        }
                }

       suspend fun recuperarUsuaarioLogado(uuid: String):DocumentSnapshot{
          return  firebaseDb.collection(Constantes.DOC_USUARIO_BD)
                .document(uuid)
                .get().await()

        }



}