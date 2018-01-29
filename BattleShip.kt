const val MAX_NO_OF_SHIPS = 5
val ocean = Array(10) { arrayOfNulls<String>(10)}    // Create the ocean with a two-dim array.
var userShipLeft = 0;
var computerShipLeft = 0;

fun main(args: Array<String>) {

    println("****Welcome to Battle Ships game****")
    println("\n  Right now, the sea is empty.")

    // This shows the occean empty
    printOcean()

    // Deploy user ships
    deployUserShips()

    // Deploy computer ships
    deployComputerShips()

    // Commence game
    playersTurn()
}

fun printOcean() {
    println("\n  0123456789  ")
    for (row in ocean.indices) {
        print("$row|")
        for (col in ocean.get(0).indices) {
            if (ocean[row][col] == null) {
                print(" ")
            } else {
                print("@")
            }
        }
        println("|$row")
    }
    println("  0123456789  \n")
}

/* Deploy user ship */
fun deployUserShips() {
    println("Deploy your ships:")
    for (ship in 1..MAX_NO_OF_SHIPS) {
        var check = false
        do {
            print("Enter the X and Y coordinate for ship $ship: ")
            try {
                val (x, y) = readLine()!!.split(' ').map(String::toInt)
                if (x > 9 || y > 9) {
                    println("Invalid coordinate! Please try again.")
                    check = false
                } else {
                    check = checkCoordinate(x, y)
                    if (check) {
                        ocean[x][y] = "U"
                        continue
                    }
                }
            } catch (e: NumberFormatException) {
                println("\nInvalid Coordinate! Please try again.\n")
            } catch (e1: IndexOutOfBoundsException) {
                println("\nInvalid Coordinate! Please try again.\n")
            }
        } while (check == false)
    }
    userShipLeft = MAX_NO_OF_SHIPS;
    printOcean()
}

/* Check the Coordinate to see if its empty. */
fun checkCoordinate(x: Int, y: Int): Boolean {
    if (ocean[x][y] == null)
        return true

    return false
}

/* Deploy Computer ships */
fun deployComputerShips() {
    println("Computer is deploying it ships.")
    for (ship in 1..MAX_NO_OF_SHIPS) {
        var check: Boolean
        do {
            val x = (Math.random() * 10).toInt()
            val y = (Math.random() * 10).toInt()
            check = checkCoordinate(x, y)

            if (check) {
                ocean[x][y] = "C"
                println("$ship ship DEPLOYED")
                continue
            }
        } while (check == false)
    }
    computerShipLeft = MAX_NO_OF_SHIPS
    println()
}

/* Players taking turns to play. */
fun playersTurn() {
    do {
        userTurn()
        computerTurn()
    } while (userShipLeft > 0 && computerShipLeft > 0)

    if (userShipLeft == 0) {
        println("\nYour ships: $userShipLeft | Computer ships: $computerShipLeft")
        println("COMPUTER WINS")
    } else {
        println("\nYour ships: $userShipLeft | Computer ships: $computerShipLeft")
        println("HOORAY! YOU WON THE BATTLE!")
    }
}

fun userTurn() {
    if (userShipLeft == 0 || computerShipLeft == 0) return

    println("\nYOUR TURN")
    print("Enter the x and y coordinate you want to shoot: ")

    try {
        val (x, y) = readLine()!!.split(' ').map(String::toInt)

        if (x > 9 || y > 9) {
            println("Invalid Coordinate! Please Try again.")
            userTurn()
        } else {
            when(ocean[x][y]) {
                null -> {
                    ocean[x][y] = "-"
                    println("Sorry, you missed!")
                    printCurrentOceanState()
                }
                "U" -> { // user ship coordinate
                    ocean[x][y] = "x"
                    userShipLeft--
                    println("Oh no, you sunk your own ship :(")
                    printCurrentOceanState()
                }
                "!", "-", "x" -> { // Already sunk ship coordinate
                    println("Invalid Coordinate! Please try again.")
                    userTurn()
                }
                else -> {
                    ocean[x][y] = "!"
                    computerShipLeft--
                    println("Boom! You sunk computer ship.")
                    printCurrentOceanState()
                }
            }
        }
    } catch (e: NumberFormatException) {
        println("\nInvalid Coordinate! Please try again.\n")
        userTurn()
    } catch (e1: IndexOutOfBoundsException) {
        println("\nInvalid Coordinate! Please try again.\n")
        userTurn()
    }
}

fun computerTurn() {
    if (userShipLeft == 0 || computerShipLeft == 0) return

    println("\nCOMPUTER'S TURN")
    val x = (Math.random() * 10).toInt()
    val y = (Math.random() * 10).toInt()

    when(ocean[x][y]) {
        null -> {
            ocean[x][y] = "-"
            println("Computer missed!")
        }
        "C" -> {
            ocean[x][y] = "x"
            computerShipLeft--
            println("The computer just sunk one of its ships :(")
        }
        "!", "-", "x" -> computerTurn()
        else -> {
            ocean[x][y] = "!"
            userShipLeft--
            println("Boom! Computer sunk one of your ships.")
        }
    }
}

fun printCurrentOceanState() {
    println("\n  0123456789  ")
    for (row in ocean.indices) {
        print("$row|")
        for (col in ocean.get(0).indices) {
            when(ocean[row][col]) {
                null, "-" -> print(" ")
                "U" -> print("@")
                "x", "!" -> print("x")
                else -> print(" ") // "C" - Computer ship
            }
        }
        println("|$row")
    }
    println("  0123456789  \n")
}
