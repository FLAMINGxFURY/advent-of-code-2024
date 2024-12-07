import java.io.File

const val upChar = '^'
const val downChar = 'v'
const val leftChar = '<'
const val rightChar = '>'

fun main() {
	// read in input
	val reader = File("input.txt").bufferedReader()
	val lines = reader.readLines()

	// convert lines to 2D array of characters
	val grid = lines.map { it.toCharArray() }.toTypedArray()

	// print dimensions of grid
	println("Grid dimensions: ${grid.size} x ${grid[0].size}")

	// val result = partOne(grid)
	val result = partTwo(grid)
	println(result)
}

fun findStart(grid: Array<CharArray>): GuardPathNode {
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			if (grid[i][j] == upChar || grid[i][j] == downChar || grid[i][j] == leftChar || grid[i][j] == rightChar) {
				return GuardPathNode(i, j, grid[i][j])
			}
		}
	}
	return GuardPathNode(0, 0, ' ')
}

fun partOne(grid: Array<CharArray>): Int {
	val guardPos = findStart(grid)
	var guardX = guardPos.x
	var guardY = guardPos.y
	var direction = guardPos.direction

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

class GuardPathNode(var x: Int, var y: Int, var direction: Char) {
	override fun equals(other: Any?): Boolean {
		if (other !is GuardPathNode) return false
		return x == other.x && y == other.y && direction == other.direction
	}

	override fun hashCode(): Int = javaClass.hashCode() // lambda is just "=" in Kotlin
}

fun getPath(grid: Array<CharArray>): Set<GuardPathNode> {
	// find starting position
	val guardPos = findStart(grid)

	// guard moves in direction it's facing until it meets a wall ('#'). Then, it turns right.
	val visited = mutableSetOf<GuardPathNode>()
	visited += guardPos

	// while the guard has not exited the grid...
	while (guardPos.x >= 0 && guardPos.x < grid.size && guardPos.y >= 0 && guardPos.y < grid[0].size) {
		// if the guard is facing any boundary, break
		if (guardPos.x == 0 || guardPos.x == grid.size - 1 || guardPos.y == 0 || guardPos.y == grid[0].size - 1)
			break

		// if the guard is facing a wall, turn right
		if (guardPos.direction == upChar && grid[guardPos.x - 1][guardPos.y] == '#') {
			guardPos.direction = rightChar
		} else if (guardPos.direction == downChar && grid[guardPos.x + 1][guardPos.y] == '#') {
			guardPos.direction = leftChar
		} else if (guardPos.direction == leftChar && grid[guardPos.x][guardPos.y - 1] == '#') {
			guardPos.direction = upChar
		} else if (guardPos.direction == rightChar && grid[guardPos.x][guardPos.y + 1] == '#') {
			guardPos.direction = downChar
		}

		// move the guard in the direction it's facing
		when (guardPos.direction) {
			upChar -> {
				guardPos.x--
			}
			downChar -> {
				guardPos.x++
			}
			leftChar -> {
				guardPos.y--
			}
			rightChar -> {
				guardPos.y++
			}
		}

		// add the new position to the set of visited positions
		visited += GuardPathNode(guardPos.x, guardPos.y, guardPos.direction)
	}

	return visited
}

fun detectCycle(grid: Array<CharArray>): Boolean {
	val visited = mutableListOf<GuardPathNode>()

	val guardPos = findStart(grid)

	// while the guard has not exited the grid...
	while (guardPos.x >= 0 && guardPos.x < grid.size && guardPos.y >= 0 && guardPos.y < grid[0].size) {
		// if the guard is facing any boundary, there is no cycle
		if (guardPos.x == 0 || guardPos.x == grid.size - 1 || guardPos.y == 0 || guardPos.y == grid[0].size - 1)
			break

		// if the guard is facing a wall, turn right
		if (guardPos.direction == upChar && grid[guardPos.x - 1][guardPos.y] == '#') {
			guardPos.direction = rightChar
			continue
		} else if (guardPos.direction == downChar && grid[guardPos.x + 1][guardPos.y] == '#') {
			guardPos.direction = leftChar
			continue
		} else if (guardPos.direction == leftChar && grid[guardPos.x][guardPos.y - 1] == '#') {
			guardPos.direction = upChar
			continue
		} else if (guardPos.direction == rightChar && grid[guardPos.x][guardPos.y + 1] == '#') {
			guardPos.direction = downChar
			continue
		}

		// move the guard in the direction it's facing
		when (guardPos.direction) {
			upChar -> {
				guardPos.x--
			}
			downChar -> {
				guardPos.x++
			}
			leftChar -> {
				guardPos.y--
			}
			rightChar -> {
				guardPos.y++
			}
		}

		// if a GuardPathNode with the same x, y, and *direction* has been visited before, there is a cycle
		if (visited.contains(GuardPathNode(guardPos.x, guardPos.y, guardPos.direction))) {
			return true
		}

		// add the new position to the set of visited positions
		visited += GuardPathNode(guardPos.x, guardPos.y, guardPos.direction)
	}

	// if the guard has exited the grid, there is no cycle
	return false
}

fun partTwo(grid: Array<CharArray>): Int {
	val originalGrid = grid.map { it.copyOf() }.toTypedArray()

	val originalPath = getPath(grid)

	// for each node visited, determine if placing a wall in front of the
	// guard would cause the guard to begin walking in a loop. If so, increment loopCount
	var loopCount = 0
	for (node in originalPath) {
		// if we are at the last node, break
		if (node == originalPath.last()) {
			break
		}

		// create a copy of the grid
		val newGrid = originalGrid.map { it.copyOf() }.toTypedArray()

		// place a wall in front of the guard
		when (node.direction) {
			upChar -> {
				newGrid[node.x - 1][node.y] = '#'
			}
			downChar -> {
				newGrid[node.x + 1][node.y] = '#'
			}
			leftChar -> {
				newGrid[node.x][node.y - 1] = '#'
			}
			rightChar -> {
				newGrid[node.x][node.y + 1] = '#'
			}
		}

		// if a cycle is detected, increment loopCount
		if (detectCycle(newGrid)) {
			loopCount++
		}
	}

	return loopCount
}
