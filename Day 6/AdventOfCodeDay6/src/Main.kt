import java.io.File
import java.io.BufferedReader

fun main() {
	// read in input
	val reader = File("input.txt").bufferedReader()
	val lines = reader.readLines()

	// convert lines to 2D array of characters
	val grid = lines.map { it.toCharArray() }.toTypedArray()

	// print dimensions of grid
	println("Grid dimensions: ${grid.size} x ${grid[0].size}")

	val result = partOne(grid)
	println(result)
}

fun partOne(grid: Array<CharArray>): Int {
	val upChar = '^'
	val downChar = 'v'
	val leftChar = '<'
	val rightChar = '>'

	// find starting position
	var (guardX, guardY) = Pair(0, 0)
	var direction = ' '

	for (i in grid.indices) {
		for (j in grid[i].indices) {
			if (grid[i][j] == upChar || grid[i][j] == downChar || grid[i][j] == leftChar || grid[i][j] == rightChar) {
				guardX = i
				guardY = j
				direction = grid[i][j]
				break
			}
		}
	}

	// guard moves in direction it's facing until it meets a wall ('#'). Then, it turns right.
	val visited = mutableSetOf<Pair<Int, Int>>()
	visited += Pair(guardX, guardY)

	// while the guard has not exited the grid...
	while (guardX >= 0 && guardX < grid.size && guardY >= 0 && guardY < grid[0].size) {
		// if the guard is facing any boundary, break
		if (guardX == 0 || guardX == grid.size - 1 || guardY == 0 || guardY == grid[0].size - 1)
			break

		// if the guard is facing a wall, turn right
		if (direction == upChar && grid[guardX - 1][guardY] == '#') {
			direction = rightChar
		} else if (direction == downChar && grid[guardX + 1][guardY] == '#') {
			direction = leftChar
		} else if (direction == leftChar && grid[guardX][guardY - 1] == '#') {
			direction = upChar
		} else if (direction == rightChar && grid[guardX][guardY + 1] == '#') {
			direction = downChar
		}

		// move the guard in the direction it's facing
		when (direction) { // This is some fun new syntax. It's essentially the new switch from C#.
			upChar -> {
				guardX--
			}
			downChar -> {
				guardX++
			}
			leftChar -> {
				guardY--
			}
			rightChar -> {
				guardY++
			}
		}

		// add the new position to the set of visited positions
		visited += Pair(guardX, guardY)
	}

	return visited.size
}