package com.example.quizapp.config.permissões


import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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

    fun pedirPermissao(gerenciadorDePermissoao :  ActivityResultLauncher<Array<String>>,permissao: String,activity: Activity):Boolean{

        val valorPermissao =
             ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_DENIED

              if (valorPermissao && !listaPermissoesNegadas.contains(permissao))
                    listaPermissoesNegadas.add(permissao)

        if (listaPermissoesNegadas.isNotEmpty() ) {
             gerenciadorDePermissoao.launch(listaPermissoesNegadas.toTypedArray())
              listaPermissoesNegadas.remove(permissao)
                return true
        }else{
             return false
        }

    }

    fun checarPermissao(permissao : Map<String, Boolean>):String {
        var itemPermisao = ""
          permissao.keys.forEach {
             itemPermisao = it
        }
        return itemPermisao
    }


}