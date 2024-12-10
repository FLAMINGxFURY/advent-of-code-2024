import java.io.File
import java.io.BufferedReader

fun main() {
	val reader = File("input.txt").bufferedReader()
	val input = reader.readLine()

	val result = partTwo(input)
	println(result)
}

fun checksum(values : List<Int>) : Long {
	var sum: Long = 0
	for (index in values.indices) {
		if (values[index] == -1) continue
		sum += values[index] * index
	}
	return sum
}

fun createInitialDisk(input: String) : MutableList<Int> {
	val disk = mutableListOf<Int>()

	var isFreeBlock = false
	var fileID = 0
	for (char in input) {
		// add appropriate representation of the file to the disk
		for (i in 0..<"$char".toInt()) {
			if (isFreeBlock) {
				// -1 is a flag for free space.
				disk.add(-1)
			} else {
				disk.add(fileID)
			}
		}
		// if this wasn't a free block, increment the fileID
		if (!isFreeBlock) fileID++

		// toggle the flag
		isFreeBlock = !isFreeBlock
	}
	return disk
}

fun partOne(input: String): Long {
	// create a mapping for the input string
	val disk = createInitialDisk(input)

	// from back to front, place the nodes in the first available free space
	// until the disk is contiguous
	for (i in disk.size - 1 downTo 0) {
		// If the disk is contiguous at this point, break
		var contiguous = true
		var foundFreeSpace = false
		for (block in disk) {
			// if we've previously found a free space, and we're not at a free space...
			if (foundFreeSpace && block != -1) {
				contiguous = false
				break
			}

			if (block == -1) foundFreeSpace = true
		}

		// if the disk is contiguous, break
		if (contiguous) break

		// if the item at this point is not a free space, move it
		if (disk[i] != -1) {
			val temp = disk[i]
			disk[i] = -1
			// find the first free space and move the item there
			for (j in 0..<disk.size) {
				if (disk[j] == -1) {
					disk[j] = temp
					break
				}
			}
		}
	}
	return checksum(disk)
}

fun partTwo(input: String): Long {
	val disk = createInitialDisk(input)

	// find the start and end indices of each file
	val filePositions = mutableMapOf<Int, Pair<Int, Int>>()

	var currentFile = disk[0]
	var startIndex = 0

	for (i in disk.indices) {
		if (disk[i] != currentFile) {
			// we reached a new file or free space block
			if (currentFile != -1) {
				filePositions[currentFile] = Pair(startIndex, i - 1)
			}
			currentFile = disk[i]
			startIndex = i
		}
	}
	// trailing file
	if (currentFile != -1) {
		filePositions[currentFile] = Pair(startIndex, disk.size - 1)
	}

	// from back to front, place consecutive blocks of the same node in the first
	// block of free space where they will fit. If there is no appropriate block, do nothing.
	// Never move a block to the right, only move blocks to the left.
	val largestFileID = filePositions.keys.max()
	for (fileID in largestFileID downTo 0) {
		val (start, end) = filePositions[fileID] ?: continue
		val length = end - start + 1

		for (i in 0..<start) {
			if (disk[i] == -1) {
				// check if there is enough space to fit the file
				var canFit = true
				for (j in 0..<length) {
					if (i + j >= disk.size || disk[i + j] != -1) {
						canFit = false
						break
					}
				}
				if (canFit) {
					// if we found a free space, move the file
					for (k in 0..<length) {
						disk[i + k] = fileID
					}
					// clear the old file
					for (k in 0..<length) {
						disk[start + k] = -1
					}
					break
				}
			}
		}
		println("$fileID done")
	}
	return checksum(disk)
}