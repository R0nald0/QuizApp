package com.example.quizapp.config.permissões

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.quizapp.repository.repoImpl.UsuarioRepository
import com.example.quizapp.utils.Constantes


class Permissao{

    var listaPermissoesNegadas = mutableListOf<String>()

   /* fun verificarPermicoes(activity:Activity,permissao : String):MutableList<String>{

        val valorPermissao =
            ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_DENIED

        if (valorPermissao)
            listaPermissoesNegadas.add(permissao)

        return  listaPermissoesNegadas;
    }*/

    //forma antiga
    fun  requisiatarPermissao(activity:Activity,permissao : String){

       // var listaPermissoesNegadas = verificarPermicoes(activity,permissao)

        if (listaPermissoesNegadas.isNotEmpty()){
            ActivityCompat.requestPermissions(activity,listaPermissoesNegadas.toTypedArray(),0)
        }
    }

    fun pedirPermissao(gerenciadorDePermissoao : ActivityResultLauncher<String>,permissao: String,activity: Activity):Boolean{

        val valorPermissao = ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_DENIED
              if (valorPermissao) listaPermissoesNegadas.add(permissao)
        Log.i(Constantes.TAG, "pedirPermissao: ${listaPermissoesNegadas}")
        if (listaPermissoesNegadas.contains(permissao) ) {
                gerenciadorDePermissoao.launch(permissao)
                return true
        }else{
             return false
        }

    }

}