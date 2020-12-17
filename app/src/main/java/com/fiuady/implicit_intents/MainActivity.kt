package com.fiuady.implicit_intents

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat


class MainActivity : AppCompatActivity() {
    private lateinit var sharetext: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharetext= findViewById(R.id.text_share);

    }

    fun shareText(view: View) {

        val txt:String =sharetext.text.toString()
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Share this text with: ")
                .setText(txt)
                .startChooser();
    }
}