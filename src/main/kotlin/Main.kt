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
import java.lang.Math.abs
import java.util.*


// Classes

class Player(_id: Int) {
    val id = _id
    val name = "Player $id"

    private fun parseInput(args: List<String>): IntArray {
        if (args.size != 2) {
            throw(IllegalArgumentException("Your input had too many or too few arguments."))
        }

        return intArrayOf(parseInt(args[0]) - 1, parseInt(args[1]) - 1)
    }

    open fun getInput(board: Board): IntArray {
        val sc = Scanner(System.`in`)

        println("$name, where would you like to place your next move? Format is (Row, Column)")
        val input = sc.nextLine()
        var intArgs = intArrayOf(0, 0, 0)

        try {
            val args = input.split(" ")
            intArgs = parseInput(args)
            board.validateMove(intArgs[0], intArgs[1])
        } catch (e: Exception) {
            println(e.message)
            getInput(board)
        }

        return intArgs
    }
}

class Board() {
    // 3x3 Matrix of ints set to 0
    var boardSize = 3
    private var matrix = Array(boardSize) { Array(boardSize) {'_'} }

    var rowsContainer = intArrayOf(0, 0, 0)
    var columnsContainer = intArrayOf(0, 0, 0)
    var diagonalContainer = intArrayOf(0, 0, 0)
    var oppositeDiagonalContainer = intArrayOf(0, 0, 0)

    fun isWinCondition(row: Int, col: Int, id: Int): Boolean {
        var mod = getPlayerMod(id)
        rowsContainer[row] += mod
        columnsContainer[col] += mod
        if (row == col) diagonalContainer[row] += mod
        if (row + col == 2) oppositeDiagonalContainer[row] += mod

        if (abs(rowsContainer[row]) == boardSize) return true
        if (abs(columnsContainer[col]) == boardSize) return true

        var sumForDiagonal = 0
        var sumForOppositeDiagonal = 0

        for (i in 0 until boardSize) {
            sumForDiagonal += diagonalContainer[i]
            sumForOppositeDiagonal += oppositeDiagonalContainer[i]
        }

        if (abs(sumForDiagonal) == boardSize) return true
        if (abs(sumForOppositeDiagonal) == boardSize) return true

        return false
    }

    // check if a move is valid or not
    fun validateMove(row: Int, col: Int) {
        if (row > 3 || row < 0) {
            throw(IllegalArgumentException("Your row index is out of range."))
        }

        if (col > 3 || col < 0) {
            throw(IllegalArgumentException("Your column index is out of range."))
        }

        if (matrix[row][col] != '_') {
            throw(IllegalArgumentException("Your selection is not a valid move."))
        }
    }

    private fun getPlayerChar(id: Int): Char {
        if (id == 1) return 'X'
        return 'O'
    }

    private fun getPlayerMod(id: Int): Int {
        if (id == 1) return 1
        return -1
    }

    fun makeMove(row: Int, col: Int, id: Int): Boolean {
        validateMove(row, col)
        matrix[row][col] = getPlayerChar(id)

        return isWinCondition(row, col, id)
    }

    private fun getChar(c: Char, row: Int): Char {
        if (c == '_' && row == 2) return ' ';

        return c
    }

    // displays board
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

    val players = listOf<Player>(player1, player2)

    println(players[0].name)
    gameLoop(players)
}

// Game Loop

fun gameLoop(players: List<Player>) {
    // Initialization
    var running = true
    var turn = 1

    // Create board
    val board = Board()
    var player = players[turn - 1]

    while (running) {
        board.print() // display current board
        player = players[turn - 1] // current player

        var intArgs = player.getInput(board)
        running = !board.makeMove(intArgs[0], intArgs[1], player.id)

        // change turns
        if (++turn == 3) turn = 1
    }

    board.print()
    println("${player.name} wins!")
}