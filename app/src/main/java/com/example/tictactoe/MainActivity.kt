package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var game: TicTacToe
    private var textviews =
        Array(TicTacToe.NUM_ROWS) { arrayOfNulls<TextView>(TicTacToe.NUM_COLUMNS) }
    private lateinit var newgamebutton: Button
    private lateinit var resetbutton: Button

    companion object {
        lateinit var instance: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        game = TicTacToe(this)
         //val bundle: Bundle ?=intent.extras
        //var player1name: String? = intent.getStringExtra("player1name").toString()

        //Toast.makeText(this,player1name,Toast.LENGTH_SHORT).show()
        instance=this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var player1 = findViewById<TextView>(R.id.player_one_score)
        val player2 = findViewById<TextView>(R.id.player_two_score)
        var intent = intent
        val verlauf=intent.getStringArrayListExtra("verlauf")
        var i=0;
        if (verlauf != null) {
            for (i in verlauf) {
                Log.e("name", verlauf.toString())

            }
        }
        player1.text = verlauf!![0]
        player2.text = verlauf!![1]

        game.saveX("0")
        game.saveO("0")

        textviews[0][0] = findViewById(R.id.button00)
        textviews[0][1] = findViewById(R.id.button01)
        textviews[0][2] = findViewById(R.id.button02)
        textviews[1][0] = findViewById(R.id.button10)
        textviews[1][1] = findViewById(R.id.button11)
        textviews[1][2] = findViewById(R.id.button12)
        textviews[2][0] = findViewById(R.id.button20)
        textviews[2][1] = findViewById(R.id.button21)
        textviews[2][2] = findViewById(R.id.button22)

        for (i in 0..TicTacToe.NUM_ROWS - 1) {
            for (j in 0..TicTacToe.NUM_COLUMNS - 1) {
                textviews[i][j]?.setOnClickListener(this)
            }
        }
        newgamebutton = findViewById(R.id.newgamebutton)
        newgamebutton.setOnClickListener(this)
        resetbutton = findViewById(R.id.resetbtn)
        resetbutton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var gamestateTextview = findViewById<TextView>(R.id.gamestatetextview)
        if (v?.id == R.id.newgamebutton) {
            game.resetGame()
        }
        if (v?.id == R.id.resetbtn) {
            game.saveX("0")
            game.saveO("0")
            game.stringForGameState()
        }
        for (i in 0..TicTacToe.NUM_ROWS - 1) {
            for (j in 0..TicTacToe.NUM_COLUMNS - 1) {
                if (v?.id == textviews[i][j]?.id) {
                    game.pressedButtonAtLocation(i, j)
                }
            }
        }
        for (i in 0..TicTacToe.NUM_ROWS - 1) {
            for (j in 0..TicTacToe.NUM_COLUMNS - 1) {
                textviews[i][j]?.text = game.stringForButtonAtLocation(i, j)
            }
        }
        gamestateTextview.text = game.stringForGameState()
    }
}