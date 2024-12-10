import java.io.File

fun main() {
	val reader = File("input.txt").bufferedReader()
	val lines = reader.readLines()

	val gridChars = lines.map { it.toCharArray() }.toTypedArray()

	// convert grid to 2D array of integers
	val grid = gridChars.map { it: CharArray -> it.map { it.toString().toInt() } }
}

fun findZeroElevations(grid: Array<IntArray>): List<Pair<Int, Int>> {
	val zeroElevations = mutableListOf<Pair<Int, Int>>()
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			if (grid[i][j] == 0) {
				zeroElevations.add(Pair(i, j))
			}
		}
	}
	return zeroElevations
}

fun partOne(grid: Array<IntArray>): Int {
	val zeroElevations = findZeroElevations(grid)
}