/*
** Program to generate a random grid to play battleship. The grid
** is 10x10.
*/

// Procedure to place a ship (represented by a tuple consisting of the
// ship size and the ship number) on the grid. The procedure takes three
// arguments: The grid (an Array of Arrays), the ship tuple
// (tuple2[Int,Int]), and a boolean indicating if the ship should be
// placed vertically or horizontally on the grid. If the boolean is true,
// the ship should be vertical. The procedure has a return type of Unit
// (note the lack of an '=' on the first line!). This procedure is called
// in the code section below.
//
// This is a complicated procedure. You do not need to understand it
// entirely to finish the Minesweeper lab. You could certainly try to figure
// it out, though!

def placeShip(grid: Array[Array[Char]], ship: Tuple2[Int,Int], orient: Boolean) {
  // Extract the ship parameters from the tuple
  val (size,num) = ship

  // Find the starting position of the ship. Dependent on the size and
  // the orientation. Don't start close to an edge if the ship will extend
  // outside the grid.
  val endRowAdjust = if (orient) size else 0
  val endColAdjust = if (orient) 0 else size
  val startRow = (math.random * (height - endRowAdjust)).toInt
  val startCol = (math.random * (width - endColAdjust)).toInt

  // Check that the ship will not overlap another. Call placeShip recursively
  // if it does. Depends on orientation.

  // Assume the ship won't overlap. Try to prove yourself wrong in the code.
  var overlap: Boolean = false
  
  // If/else to place a ship vertically or horizontally. The two clauses could
  // be combined, but the code becomes more compact/less readable if you do.
  
  // Ship is vertical
  if (orient) {
    // Loop through the positions
    var i: Int = 0
    while (!overlap && (i < size)) {
      // Check to see if there is an overlap
      if (grid(startRow + i)(startCol) != '-')
        overlap = true
      i += 1
    }
    // If overlap, call placement function recursively until successful
    if (overlap)
      placeShip(grid,ship,orient)
    // No overlap. Place the ship!
    else {
      // Reuse the loop variable
      i = 0
      while (i < size) {
        // Place a ship number in the grid
        grid(startRow + i)(startCol) = ('0' + num).toChar
        i += 1
      }
    }

  // Ship is horizontal (code looks very similar to above!)
  } else {
    // Loop through the positions
    var i: Int = 0
    while (!overlap && (i < size)) {
      // Check to see if there is an overlap
      if (grid(startRow)(startCol + i) != '-')
        overlap = true
      i += 1
    }
    // If overlap, call placement function recursively until successful
    if (overlap)
      placeShip(grid,ship,orient)
    // No overlap. Place the ship!
    else {
      // Reuse the loop variable
      i = 0
      while (i < size) {
        // Place a ship number in the grid
        grid(startRow)(startCol + i) = ('0' + num).toChar
        i += 1
      }
    }
  }
}

////////////////////////////
///// Code starts here /////
////////////////////////////

// Set the Battleship game board size
val height: Int = 10
val width: Int = 10

// Create the matrix to store the playing field. This is a 2-D
// matrix, so will be an Array of Arrays. Since each Array
// element represents a row in the grid. The number of rows is
// represented by the height variable.

val board: Array[Array[Char]] = new Array[Array[Char]](height)

// board is an Array that will store 10 Arrays of Ints. At this time
// The elements of this Array are null. We need to loop through the 10
// positions in the grid, and set each one to an Array of 10 Ints. For this
// loop while is used.

var i: Int = 0
while (i < height) {
  // Each Array element represents a row in the grid. The number of rows
  // is represented by the height variable
  board(i) = new Array[Char](width)
  i += 1
  }

// Next, set all the elements of the matrix to '-' (the character that
// we will use to indicate that no ship is present. We can then go back
// and "place" our ships by replacing positions in the grid. Note that 
// we will visit each node in the grid systematically to ensure that
// all are visited. For this traversal, I'll use the for comprehension.
// Note that because the for comprehension only has 1 thing in it we
// don't need curly braces (blocks).

for (h <- 0 until height)
  for (w <- 0 until width)
    board(h)(w) = '-'

// Print out the board to make sure everything is working. This process
// traverses the grid like above, but this time use nested while loops.

println("\nEmpty Board!")

// i is already declared. Reuse it!
i = 0
while (i < height) {
  // Create a loop variable for the inner loop. Each iteration of the outer
  // loop gives us a new inner loop variable (scope!).
  var j = 0
  while (j < width) {
    print(" " + board(i)(j))
    j += 1
  }
  println
  i += 1
}

// Place ships on the grid. Ships are represented by number:
//
// Ship                 Size     Number
// ----                 ----     ------
// Aircraft Carrier      5          1
// Battleship            4          2
// Submarine             3          3
// Destroyer             3          4
// Patrol Boat           2          5

// Create Array of tuples representing ships (size and number). Note type
// of tuple in Array declaration!

val ships: Array[Tuple2[Int,Int]] = Array((5,1),(4,2),(3,3),(3,4),(2,5))

// Iterate over the ships, placing them in the grid. Use a higher
// order function this time!
//
// Note:
//   Use parameter placeholders because you don't need to name the variable
//     representing the tuple pulled from the Array of ships
//   The third parameter of the function determines the orientation of the
//     ship on the board (50% chance vertical)

ships.foreach(placeShip(board,_,math.random < 0.5))

// Print the board after the ships are placed. Use the for comprehension
// over the elements of the grid!

println("\nFinal (random) Board Configuration!")
// Iterate over the rows
for (row <- board) {
  // Iterate over each element in a row
  for (element <- row)
    print(" " + element)
  println
}