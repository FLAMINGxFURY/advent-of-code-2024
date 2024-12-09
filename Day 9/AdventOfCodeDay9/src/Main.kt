import java.io.File
import java.io.BufferedReader

fun main() {
	val reader = File("input.txt").bufferedReader()
	val input = reader.readLine()

	val result = partOne(input)
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

fun partOne(input: String): Long {
	// create a mapping for the input string
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