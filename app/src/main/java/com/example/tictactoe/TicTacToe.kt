package com.example.tictactoe

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.widget.TextView
import java.io.*


class TicTacToe(private val context: Context) {

    private enum class GameState {
        X_TURN, O_TURN, X_WIN, O_WIN, TIE_GAME

    }
    var stop_db = false
    private var gameState: GameState? = null
    private lateinit var boardArray: Array<IntArray>
    fun resetGame() {
        boardArray = Array(
            NUM_ROWS
        ) { IntArray(NUM_COLUMNS) }
        gameState = GameState.X_TURN
    }

    fun pressedButtonAtLocation(row: Int, column: Int) {
        if (row < 0 || row >= NUM_ROWS || column < 0 || column >= NUM_COLUMNS) return
        if (boardArray[row][column] != MARK_NONE
        ) return  // Not empty
        if (gameState == GameState.X_TURN) {
            boardArray[row][column] = MARK_X
            gameState = GameState.O_TURN
        } else if (gameState == GameState.O_TURN) {
            boardArray[row][column] = MARK_O
            gameState = GameState.X_TURN
        }
        checkForWin()
    }

    private fun checkForWin() {
        if (!(gameState == GameState.X_TURN || gameState == GameState.O_TURN)) return
        if (didPieceWin(MARK_X)) {
            gameState = GameState.X_WIN
        } else if (didPieceWin(MARK_O)) {
            gameState = GameState.O_WIN
        } else if (isBoardFull) {
            Log.d("TicTacToeGame", "The pattern is full!")
            gameState = GameState.TIE_GAME
        }
    }

    private val isBoardFull: Boolean
        private get() {
            for (row in 0 until NUM_ROWS) {
                for (col in 0 until NUM_COLUMNS) {
                    if (boardArray[row][col] == MARK_NONE) {
                        Log.d("TicTacToeGame", "Empty at Row: $row Col: $col")
                        return false
                    }
                }
            }
            return true
        }

    private fun didPieceWin(markType: Int): Boolean {
        var allMarksMatch = true
        for (col in 0 until NUM_COLUMNS) {
            allMarksMatch = true
            for (row in 0 until NUM_ROWS) {
                if (boardArray[row][col] != markType) {
                    allMarksMatch = false
                    break
                }
            }
            if (allMarksMatch) return true
        }


        for (row in 0 until NUM_ROWS) {
            allMarksMatch = true
            for (col in 0 until NUM_COLUMNS) {
                if (boardArray[row][col] != markType) {
                    allMarksMatch = false
                    break
                }
            }
            if (allMarksMatch) return true
        }


        if (boardArray[0][0] == markType && boardArray[1][1] == markType && boardArray[2][2] == markType
        ) return true


        return if (boardArray[2][0] == markType && boardArray[1][1] == markType && boardArray[0][2] == markType
        ) true else false
    }

    fun stringForButtonAtLocation(row: Int, column: Int): String {
        val label = ""
        if (row >= 0 && row < NUM_ROWS && column >= 0 && column < NUM_COLUMNS) {
            if (boardArray[row][column] == MARK_X) {
                return "X"
            } else if (boardArray[row][column] == MARK_O) {
                return "O"
            }
        }
        return label
    }

    fun stringForGameState(): String {
        var gameStateLabel = ""
        val r = context.resources
        val p1 = MainActivity.instance.findViewById<TextView>(R.id.player_one_score).text.toString()
        val p2 = MainActivity.instance.findViewById<TextView>(R.id.player_two_score).text.toString()
        MainActivity.instance.findViewById<TextView>(R.id.player1score).text=readX()
        MainActivity.instance.findViewById<TextView>(R.id.player2score).text=readY()
        var points:String
        gameStateLabel = when (gameState) {
            GameState.X_TURN -> {
                stop_db = false
                p1
            }
            GameState.O_TURN -> {
                stop_db = false
                p2
            }
            GameState.X_WIN -> {
                points=readX()
                if(!stop_db){
                    var intpoints=points.toInt()+1
                    var stringpoint=intpoints.toString()
                    saveX(stringpoint)
                    MainActivity.instance.findViewById<TextView>(R.id.player1score).text=readX()
                    resetGame()
                }
                stop_db = true
                "$p1 Wins"
            }
            GameState.O_WIN -> {
                points=readY()
                if(!stop_db){
                    var intpoints=points.toInt()+1
                    var stringpoint=intpoints.toString()
                    saveO(stringpoint)
                    MainActivity.instance.findViewById<TextView>(R.id.player2score).text=readY()
                    resetGame()
                }
                stop_db = true
                "$p2 Wins"
            }
            else -> r.getString(R.string.tie_game)
        }
        return gameStateLabel
    }

    companion object {
        const val NUM_ROWS = 3
        const val NUM_COLUMNS = 3
        private const val MARK_NONE = 0
        private const val MARK_X = 1
        private const val MARK_O = 2
    }

    init {
        resetGame()
    }

    fun saveX(score: String) {
        val file:String = "savex.txt"
        val data:String = score
        Log.e("SCoreX",data.toString())
        val fileOutputStream:FileOutputStream
        try {
            fileOutputStream = context.openFileOutput(file, MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
        }catch (e: Exception){
            e.printStackTrace()
        }
    } fun saveO(score: String) {
        val file:String = "saveo.txt"
        val data:String = score
        Log.e("SCoreY",data.toString())
        val fileOutputStream:FileOutputStream
        try {
            fileOutputStream = context.openFileOutput(file, MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
        }catch (e: FileNotFoundException){
            Log.e("erroratsaveO",data)
            e.printStackTrace()
        }
    }
    fun readX(): String {
        var fileInputStream: FileInputStream? = null
        fileInputStream = context.openFileInput("savex.txt")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        Log.e("stringBuilderX",stringBuilder.toString())

        return stringBuilder.toString()
    }
    fun readY(): String {
        var fileInputStream: FileInputStream? = null
        fileInputStream = context.openFileInput("saveo.txt")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        Log.e("stringBuilderY",stringBuilder.toString())

        return stringBuilder.toString()
    }
}
