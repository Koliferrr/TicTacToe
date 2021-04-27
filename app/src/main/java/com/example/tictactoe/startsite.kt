package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class startsite : AppCompatActivity() {
    companion object{
        lateinit var instancestartseite: startsite
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        instancestartseite=this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startsite)
        var startbutton=findViewById<Button>(R.id.startbutton)

        startbutton.setOnClickListener{
            val p1namefield=findViewById<EditText>(R.id.Player1)
            val p2namefield=findViewById<EditText>(R.id.Player2)
            val names = arrayListOf<String>()
            if(p1namefield.length()>0 && p2namefield.length()>0){
            val p1name=findViewById<EditText>(R.id.Player1).text
            val p2name=findViewById<EditText>(R.id.Player2).text
            names.add(p1name.toString())
            names.add(p2name.toString())
            }
            else if (p1namefield.length()>0 && p2namefield.length()<=0){
                val p1name=findViewById<EditText>(R.id.Player1).text
                val p2name="player2"
                names.add(p1name.toString())
                names.add(p2name)
            }
            else if (p1namefield.length()<=0 && p2namefield.length()>0){
                val p2name=findViewById<EditText>(R.id.Player2).text
                val p1name="player1"
                names.add(p1name)
                names.add(p2name.toString())
            }else if (p1namefield.length()<=0 && p2namefield.length()<=0){
                val p1name="player1"
                val p2name="player2"
                names.add(p1name)
                names.add(p2name)
            }
            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("player1name",p1name)
            //intent.putExtra("player2name",p2name)

            intent.putExtra("verlauf", names)
            startActivity(intent)
        }

    }
}