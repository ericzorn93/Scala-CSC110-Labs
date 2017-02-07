/*
** Program to play battleship (single player). The grid is built, and
** the player types in locations. When all the ships are sunk, the total
** number of shots is reported. I have reduced the comments. To see the
** expanded comments, check out earlier versions of the code,
*/

// Function to place a ship in the grid
def placeShip(grid: Array[Array[Char]], ship: Tuple2[Int,Int], orient: Boolean) {
  // Extract the ship parameters from the tuple
  val (size,num) = ship

  // Set the row/col parameters depending on orientation
  val (endRowAdjust,endColAdjust,vert,horiz) = if (orient) (size,0,1,0) else (0,size,0,1)

  // Find the starting position of the ship.
  val startRow = (math.random * (height - endRowAdjust)).toInt
  val startCol = (math.random * (width - endColAdjust)).toInt

  // Assume the ship won't overlap. Try to prove yourself wrong in the code.
  var overlap: Boolean = false
  
  // Loop through the positions
  var i: Int = 0
  while (!overlap && (i < size)) {
    // Check to see if there is an overlap
    if (grid(startRow + vert*i)(startCol + horiz*i) != '-')
      overlap = true
    else {
      // Keep placing the ship!
      grid(startRow + vert*i)(startCol + horiz*i) = ('0' + num).toChar
      i += 1
    }
  }
  // If overlap, call placement function recursively until successful
  if (overlap) {
    // Go back and clear the grid positions
    while (i > 0) {
      grid(startRow + vert*(i-1))(startCol + horiz*(i-1)) = '-'
      i -= 1
    }
    placeShip(grid,ship,orient)
  }
}

// Function to process a shot by the player
def processShot(game: Array[Array[Char]],
                board: Array[Array[Char]],
                row: Int, col: Int): Int = {
  var ret = 0
  if (game(row)(col) == '-') {
    board(row)(col) match {
      case '-' => game(row)(col) = 'o'
                  println("\nMiss!!\n")
      case v => game(row)(col) = 'X'
                println("\nHIT!\n")
                val ship: Char = board(row)(col)
                var none: Boolean = true
                for (r <- 0 until 10)
                  for (c <- 0 until 10) {
                    if ((game(r)(c) == '-') && (board(r)(c) == ship))
                      none = false
                  }
                if (none) {
                  ret = 1
                  for (r <- 0 until 10)
                    for (c <- 0 until 10)
                      if (board(r)(c) == ship)
                        game(r)(c) = 'S'
                }
    }
  }
  ret
}

// Function to print a matrix. Uses higher-order functions
def printBoard(grid: Array[Array[Char]]) {
  println("  " + (1 to 10).mkString(" "))
  println((0 until 10).map((x: Int) => ('A' + x).toChar + " " + grid(x).
          mkString(" ")).mkString("\n"))
}

///////////////////////////
///// Set up the game /////
///////////////////////////

// Set the Battleship game board size
val (height,width) = (10,10)

// Create the matrices to store the 2 representations of the playing field.
// The internal representation (board) shows the ships. Game is what the
// player sees. Both matrices are populated with '-'
val board: Array[Array[Char]] = new Array[Array[Char]](height)
        .map(_ => new Array[Char](width).map(_ => '-'))
val game: Array[Array[Char]] = new Array[Array[Char]](height)
        .map(_ => new Array[Char](width).map(_ => '-'))

val ships: Array[Tuple2[Int,Int]] = Array((5,1),(4,2),(3,3),(3,4),(2,5))

// Iterate over the ships, placing them in the grid.

ships.foreach(placeShip(board,_,math.random < 0.5))

/////////////////////////
///// Play the game /////
/////////////////////////

var totalSunk: Int = 0
var totalShots: Int = 0

println("\nWelcome to Battleship\n")
println("Try to sink the ships in as few guesses as possible!\n")
println("\t(enter your guess as a letter and a number separated by a space)\n")

do {
  // Print the board for each turn
  printBoard(game)

  //Uncomment below to "cheat"!
  //printBoard(board)

  print("\nTake a shot: ")
  // Get the user input. No special processing is done. If the input is bad, the
  // program crashes!
  try {
    val Array(r,c) = readLine.toLowerCase.split(" ")
    // Process the shot (function is above)
    val hit = processShot(game,board,r(0) - 'a',c.toInt - 1)
    totalShots += 1
    if (hit == 1) {
      println("You sunk my ship!!\n")
      totalSunk += 1
    }
  } catch {
    case _: Throwable =>
  }
  // Continue until all 5 ships sunk
} while (totalSunk < 5)

// Report how long it took to finish
printBoard(game)
println("\nYou took " + totalShots + " shots to finish.")