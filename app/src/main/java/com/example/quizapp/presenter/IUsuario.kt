package com.example.quizapp.presenter

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.example.quizapp.model.Usuario

interface IUsuario{

    interface IViewLogin {
        fun logarUsario(view: View)
        fun abrirView()
        fun getToast(mensagem: String)
        fun viewCadastro(view: View)
    }

        interface IViewCadastro {
            fun cadastrarUsuario(view: View)
            fun abrirView(usuario: Usuario)
            fun exibirToast(mensagem:String)
            fun carregarImagemPicasso(uri : Uri)
            fun carregarBitMap(bitmap: Bitmap )
            fun requisistarPermissao()
            fun abrirGaleria(): ActivityResultLauncher<String>?
            fun abrirCameraOnclik() :ActivityResultLauncher<Intent>?
            fun exibirProgressBar()
            fun esconderProgressBar()
            fun contratoGetContent()
            fun contratoStartActivty()
        }
}


