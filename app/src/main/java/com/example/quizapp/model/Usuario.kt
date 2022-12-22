package com.example.quizapp.model

import android.os.Parcelable
import android.provider.ContactsContract.CommonDataKinds.Email
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuario (
    var id:String,
    var nome:String,
    var pontuacao :Int,
    var email: String,
    var senha: String,
    var urlImagemPerfil: String?
): Parcelable
