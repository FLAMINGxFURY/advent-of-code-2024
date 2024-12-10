import java.io.File

fun main() {
	val reader = File("input.txt").bufferedReader()
	val lines = reader.readLines()

	val grid = lines.map { it.toCharArray() }.toTypedArray()
}

fun findZeroElevations(grid: Array<CharArray>): List<Pair<Int, Int>> {
	val zeroElevations = mutableListOf<Pair<Int, Int>>()
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			if (grid[i][j] == '0') {
				zeroElevations.add(Pair(i, j))
			}
		}
	}
	return zeroElevations
}

fun partOne(grid: Array<CharArray>): Int {
	val zeroElevations = findZeroElevations(grid)
}