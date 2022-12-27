package com.example.quizapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import com.example.quizapp.BuildConfig
import java.io.File
import java.io.FileOutputStream

class RecursoFotos {
    private  var uriImage : Uri? =null



    fun abrirCamera(abrirCamera: ActivityResultLauncher<Intent>? ){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        abrirCamera?.launch(intent)
    }

    fun abrirGaleria(galeria : ActivityResultLauncher<String>?){
        if (galeria != null) {
            galeria.launch("image/**")
        }
    }

    fun adicionarFotoPorCamera(resultActivity: ActivityResult, context: Context) : Uri? {

        if(resultActivity.resultCode == Activity.RESULT_OK){
            val bitmap = resultActivity.data?.extras?.get("data") as Bitmap
            val caminho = File(context.cacheDir, "images")
            caminho.mkdirs()
            val file = File(caminho,"image.jpg")
            val output = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
            output.flush()
            output.close()

            uriImage = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".provider", file)
            Log.i(Constantes.TAG, "File--: ${file}")

            Log.i(Constantes.TAG, "Uri-file: ${uriImage}")
            return uriImage

        }else{
            return null
        }

    }
}