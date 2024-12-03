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
}