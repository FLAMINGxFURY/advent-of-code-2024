import java.io.File
import java.io.BufferedReader
import kotlin.math.abs

fun main() {
	// read in input
	val reader = File("input.txt").bufferedReader()
	val lines = reader.readLines()

	// convert input to 2D array of characters
	val grid = lines.map { it.toCharArray() }.toTypedArray()

	val uniqueChars = mutableSetOf<Char>()
	for (row in grid) {
		for (char in row) {
			if (char != '.') {
				uniqueChars.add(char)
			}
		}
	}

	val result = partOne(grid, uniqueChars)
	println(result)
}

fun partOne(grid: Array<CharArray>, uniqueChars: Set<Char>): Int {
	// For each unique character, find the location of each point
	val locations = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
	for (char in uniqueChars) {
		locations[char] = mutableListOf()
		for (i in grid.indices) {
			for (j in grid[i].indices) {
				if (grid[i][j] == char) {
					locations[char]!!.add(Pair(i, j))
				}
			}
		}
	}

	// Antinodes exist on both sides of a pair, the exact distance from each side that the pair has between them.
	// For each pair, determine if:
	// 1. Each antinode can be held within the grid
	// 2. The antinode would be able to be placed (it cannot be placed on top of another antinode)

	val antinodeLocations = mutableSetOf<Pair<Int, Int>>()

	for ((char, pairs) in locations) {
		// permute through all pairs of points
		for (i in pairs.indices) {
			for (j in i + 1..<pairs.size) {

				val point1 = pairs[i]
				val point2 = pairs[j]

				val xDist = point1.first - point2.first
				val yDist = point1.second - point2.second

				// The two antinodes must be placed xDist and yDist away from the two points,
				// such that neither antinode is between the two points
				val antinode1 = Pair(point1.first + xDist, point1.second + yDist)
				val antinode2 = Pair(point2.first - xDist, point2.second - yDist)

				for (antinode in listOf(antinode1, antinode2)) {
					// check if antinode is within the grid
					if (antinode.first < 0 || antinode.first >= grid.size
						|| antinode.second < 0 || antinode.second >= grid[0].size)
						continue

					// place antinode
					antinodeLocations += antinode
				}
			}
		}
	}

	return antinodeLocations.size
}