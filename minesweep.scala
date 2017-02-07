def placeShip(grid: Array[Array[Char]], ship: Tuple2[Int,Int], orient: Boolean) {
val startRow = (math.random * (height - endRowAdjust)).toInt
  val startCol = (math.random * (width - endColAdjust)).toInt
var overlap: Boolean = false
var i: Int = 0
  while (!overlap && (i < size)) {
    if (grid(startRow + vert*i)(startCol + horiz*i) != '-')
      overlap = true
    else {
      grid(startRow + vert*i)(startCol + horiz*i) = ('0' + num).toChar
      i += 1
    }
  }
  if (overlap) {
    while (i > 0) {
      grid(startRow + vert*(i-1))(startCol + horiz*(i-1)) = '-'
      i -= 1
    }
  }
}






println("Enter width of matrix: ")
var width = readInt

println("Enter height of matrix: ")
var height = readInt

println("Enter number of mines in matrix: ")
var mines = readInt

type Row = Array[Int]
type Matrix = Array[Row]

var grid: Matrix = new Matrix (height)


(math.random * width).toInt
(math.random * height).toInt


for (r <- 0 until height) 
  grid(r) = new Row (width)

for (r <- 0 until height) {
  for (c <- 0 until width)
    print("\t" + grid(r)(c))
    println
}

