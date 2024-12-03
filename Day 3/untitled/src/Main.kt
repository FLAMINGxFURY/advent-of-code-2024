import java.io.BufferedReader
import java.io.File

fun main() {
	// Read in values
	val reader: BufferedReader = File("./input.txt").bufferedReader()
	val lines: List<String> = reader.readLines()

	// Concatenate all lines into a single string
	var memory = ""
	for (line in lines) {
		memory += line
	}

	val result = partTwo(memory)
	println("Part 1: $result")
}

fun partOne(memory: String): Int {
	// regex to match "mul(dd?d?,dd?d?)"
	val mulRegex = Regex("mul\\((\\d+),(\\d+)\\)")

	// step through the string and find all matches
	val matches = mulRegex.findAll(memory)

	// Convert the matches to a list of strings
	val matchList = matches.map { it.value }.toList()

	// perform the mul operation and aggregate the result
	var result = 0
	for (match in matchList) {
		// value 1 is between ( and ,
		val value1 = match.substring(match.indexOf("(") + 1, match.indexOf(",")).toInt()
		// value 2 is between , and )
		val value2 = match.substring(match.indexOf(",") + 1, match.indexOf(")")).toInt()

		result += value1 * value2
	}

	return result
}

fun partTwo(memory: String): Int {
	// regex to match "mul(dd?d?,dd?d?)"
	val mulRegex = Regex("mul\\((\\d+),(\\d+)\\)")
	val doRegex = Regex("do\\(\\)")
	val dontRegex = Regex("don't\\(\\)")

	// step through the string and find all matches
	var matches = mulRegex.findAll(memory)
	matches += doRegex.findAll(memory)
	matches += dontRegex.findAll(memory)

	// order the matches by their index
	matches = matches.sortedBy { it.range.first }

	// Convert the matches to a list of strings
	val matchList = matches.map { it.value }.toList()

	// perform the mul operation and aggregate the result
	var result = 0
	var doFlag = true
	for (match in matchList) {
		if (match == "do()") doFlag = true
		else if (match == "don't()") doFlag = false

		else if (doFlag) {
			// value 1 is between ( and ,
			val value1 = match.substring(match.indexOf("(") + 1, match.indexOf(",")).toInt()
			// value 2 is between , and )
			val value2 = match.substring(match.indexOf(",") + 1, match.indexOf(")")).toInt()

			result += value1 * value2
		}
	}

	return result
}