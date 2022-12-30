package com.example.quizapp.utils

class VerificadorConta {

    fun verificarCamposEmail(email :CharSequence?):Boolean{
        if (email != null) {
            return !email.contains("@")
        }
        return true
    }

    fun verificarCampoLogin( texto :CharSequence?):Boolean {
        if (texto != null) {
            return texto.length<=5
        }
        return true
    }
}