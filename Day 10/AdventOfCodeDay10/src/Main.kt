import java.io.File

fun main() {
	val reader = File("input.txt").bufferedReader()
	val lines = reader.readLines()

	val gridChars = lines.map { it.toCharArray() }.toTypedArray()

	// convert grid to 2D array of integers
	val grid = gridChars.map { it: CharArray -> it.map { it.toString().toInt() } }

	val result = partTwo(grid)
	println(result)
}

fun findZeroElevations(grid: List<List<Int>>): List<Pair<Int, Int>> {
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

fun canWalk(current: Pair<Int, Int>, check: Pair<Int, Int>, grid: List<List<Int>>) =
		grid[check.first][check.second] == grid[current.first][current.second] + 1

fun partOne(grid: List<List<Int>>): Int {
	val zeroElevations = findZeroElevations(grid)

	var trailheadCount = 0

	// from each 0, determine how many paths to a 9 are possible
	for (zero in zeroElevations) {
		val viableNodes = mutableListOf(zero)
		val foundNines = mutableSetOf<Pair<Int, Int>>()
		while (viableNodes.isNotEmpty()) {
			val current = viableNodes.removeAt(0)

			// if the current node is a 9, add it to the set of found nines
			if (grid[current.first][current.second] == 9) {
				foundNines.add(current)
				continue
			}
			// walk all directions
			val (x, y) = current
			val neighbors = listOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
			for (neighbor in neighbors) {
				if (neighbor.first in grid.indices && neighbor.second in grid[0].indices) {
					if (canWalk(current, neighbor, grid)) {
						viableNodes.add(neighbor)
					}
				}
			}
		}
		trailheadCount += foundNines.size
	}
	return trailheadCount
}

fun partTwo(grid: List<List<Int>>): Int {
	val zeroElevations = findZeroElevations(grid)

	var trailheadCount = 0

	// from each 0, determine how many paths to a 9 are possible
	for (zero in zeroElevations) {
		val viableNodes = mutableListOf(zero)
		while (viableNodes.isNotEmpty()) {
			val current = viableNodes.removeAt(0)

			// if the current node is a 9, increment the trailhead count
			if (grid[current.first][current.second] == 9) {
				trailheadCount++
				continue
			}
			// walk all directions
			val (x, y) = current
			val neighbors = listOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
			for (neighbor in neighbors) {
				if (neighbor.first in grid.indices && neighbor.second in grid[0].indices) {
					if (canWalk(current, neighbor, grid)) {
						viableNodes.add(neighbor)
					}
				}
			}
		}
	}
	return trailheadCount
}