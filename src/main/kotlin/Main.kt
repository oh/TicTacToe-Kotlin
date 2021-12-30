//////////////////////////////////////////
// 2 Player Tic-Tac-Toe
//
// Made by: Hunter Webb & Jason Grossman
// Date Created: 12/28/2021
// Last Modified: 12/28/2021
//
// TODO: Add AI as Player 2
///////////////////////////////////////////

import java.lang.Integer.parseInt
import java.util.*


// Classes

class Player(_id: Int) {
    private val id = _id
    val name = "Player $id"
}

class Board() {
    // 3x3 Matrix of ints set to 0
    var matrix = Array(3) { Array(3) {'_'} }

    fun checkWinConditions() {

    }

    private fun getChar(c: Char, row: Int): Char {
        if (c == '_' && row == 2) return ' ';

        return c
    }

    private fun validateInput(args: List<String>, turn: Int)  {
        if (args.size != 2) {
            throw(IllegalArgumentException("Your input had too many or too few arguments."))
        }

        val row = parseInt(args[0])
        val col = parseInt(args[1])

        if (row > 3 || row < 0) {
            throw(IllegalArgumentException("Your row index is out of range."))
        }

        if (col > 3 || col < 0) {
            throw(IllegalArgumentException("Your column index is out of range."))
        }
    }

    fun getInput(turn: Int) {
        val sc = Scanner(System.`in`)

        println("Player $turn, where would you like to place your next move? Format is (Row, Column)")
        val input = sc.nextLine()
        try {
            val args = input.split(" ")
            validateInput(args, turn)
        } catch (e: Exception) {
            println(e.message)
            getInput(turn)
        }

    }

    fun print() {
        print("   1:  2:  3:\n" +
              "1: _${getChar(matrix[0][0], 0)}_|_${getChar(matrix[0][1], 0)}_|_${getChar(matrix[0][2], 0)}_\n" +
              "2: _${getChar(matrix[1][0], 1)}_|_${getChar(matrix[1][1], 1)}_|_${getChar(matrix[1][2], 1)}_\n" +
              "3:  ${getChar(matrix[2][0], 2)} | ${getChar(matrix[2][1], 2)} | ${getChar(matrix[2][2], 2)} \n")
    }

}


// Main Initialization

fun main(args: Array<String>) {
    var playerDefault = 1

    val scanner = Scanner(System.`in`)
    var player1 = Player(playerDefault++)
    var player2 = Player(playerDefault)

    gameLoop(player1, player2)
}

// Game Loop

fun gameLoop(player1: Player, player2: Player) {
    // Initialization
    var running = true
    var turn = 1

    // Create board
    val board = Board()

    while (running) {
        board.print()
        board.getInput(turn)

        running = false
    }
}