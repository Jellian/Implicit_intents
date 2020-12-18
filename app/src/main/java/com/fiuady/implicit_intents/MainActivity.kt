package com.fiuady.implicit_intents

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var sharetext: EditText
    private val REQUEST_CAMERA=1
    var picture: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharetext= findViewById(R.id.text_share);
        opencameraOnclick()

//        val intent = intent
//        val uri = intent.data
//        if (uri != null) {
//            val uri_string = "URI: $uri"
//            sharetext.setText(uri_string);
//        }


    }

    fun shareText(view:View) {
        //funcion para compartir el texto que se escribe y el que ya se tiene escrito
        val txt:String =sharetext.text.toString()
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Share this text with: ")
                .setText(txt)
                .startChooser();
    }

    private fun opencameraOnclick(){

        camera.setOnClickListener(){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(checkSelfPermission(CAMERA)==PackageManager.PERMISSION_DENIED||checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    //GET PERMISSIONS//
                    val permissionCamera= arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissionCamera, REQUEST_CAMERA)
                }else
                    opencamera()
            }
            else{
                opencamera()
            }
        }
    }

    private fun opencamera(){
        //recuperar los bits de una foto--espacio de memoria vacio ContentValues
        val value= ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "Nueva Imagen")
        picture=contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value)
        val camaraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picture)
        startActivityForResult(camaraIntent, REQUEST_CAMERA)
    }
//Detectar cuando se pulse el boton para abrir la camara
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    opencamera()
                else
                    Toast.makeText(applicationContext, "No puedes acceder a la cámara", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && requestCode==REQUEST_CAMERA){
        image.setImageURI(picture)
        }

    }


}