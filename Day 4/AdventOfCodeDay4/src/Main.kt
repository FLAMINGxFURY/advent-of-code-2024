import java.io.BufferedReader
import java.io.File

fun main() {
    // read in input
	val reader = File("input.txt").bufferedReader()
	val input = reader.readLines()

	// Convert input into 2D array of characters
	val grid = input.map { it.toCharArray() }.toTypedArray()

	val result = partOne(grid)
	println(result)
}

fun partOne(grid: Array<CharArray>): Int {
	val validStr = "XMAS"

	var foundItems = 0

	// check all 8 directions of the word search
	for (i in 0..<grid.size) {
		for (j in 0..<grid[0].size) {
			// if there is room to the left...
			if (j >= validStr.length - 1) {
				var foundToLeft = true
				for (k in validStr.indices) {
					if (grid[i][j - k] != validStr[k]) {
						foundToLeft = false
						break
					}
				}
				if (foundToLeft) foundItems++

				//... and if there is room above
				if (i >= validStr.length - 1) {
					var foundToTopLeft = true
					for (k in validStr.indices) {
						if (grid[i - k][j - k] != validStr[k]) {
							foundToTopLeft = false
							break
						}
					}
					if (foundToTopLeft) foundItems++
				}

				//... and if there is room below
				if (i <= grid.size - validStr.length) {
					var foundToBottomLeft = true
					for (k in validStr.indices) {
						if (grid[i + k][j - k] != validStr[k]) {
							foundToBottomLeft = false
							break
						}
					}
					if (foundToBottomLeft) foundItems++
				}
			}

			// if there is room to the right...
			if (j <= grid[0].size - validStr.length) {
				var foundToRight = true
				for (k in validStr.indices) {
					if (grid[i][j + k] != validStr[k]) {
						foundToRight = false
						break
					}
				}
				if (foundToRight) foundItems++

				//... and if there is room above
				if (i >= validStr.length - 1) {
					var foundToTopRight = true
					for (k in validStr.indices) {
						if (grid[i - k][j + k] != validStr[k]) {
							foundToTopRight = false
							break
						}
					}
					if (foundToTopRight) foundItems++
				}

				//... and if there is room below
				if (i <= grid.size - validStr.length) {
					var foundToBottomRight = true
					for (k in validStr.indices) {
						if (grid[i + k][j + k] != validStr[k]) {
							foundToBottomRight = false
							break
						}
					}
					if (foundToBottomRight) foundItems++
				}
			}

			// if there is room above
			if (i >= validStr.length - 1) {
				var foundToTop = true
				for (k in validStr.indices) {
					if (grid[i - k][j] != validStr[k]) {
						foundToTop = false
						break
					}
				}
				if (foundToTop) foundItems++
			}

			// if there is room below
			if (i <= grid.size - validStr.length) {
				var foundToBottom = true
				for (k in validStr.indices) {
					if (grid[i + k][j] != validStr[k]) {
						foundToBottom = false
						break
					}
				}
				if (foundToBottom) foundItems++
			}
		}
	}

	return foundItems
}