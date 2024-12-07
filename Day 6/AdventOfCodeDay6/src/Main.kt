import java.io.File
import java.io.BufferedReader

fun main() {
	// read in input
	val reader = File("input.txt").bufferedReader()
	val lines = reader.readLines()

	// convert lines to 2D array of characters
	val grid = lines.map { it.toCharArray() }.toTypedArray()
}