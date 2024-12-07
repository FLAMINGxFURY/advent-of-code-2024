import java.io.File

fun main() {
	val reader = File("input.txt").bufferedReader()
	val lines = reader.readLines()

	val resultMap = mutableMapOf<Int, List<Int>>()

	// separate input into result: list of int
	for (line in lines) {
		val parts = line.split(": ")
		val result = parts[0].toInt()
		val inputs = parts[1].split(" ").map { it.toInt() }
		resultMap[result] = inputs
	}
}

fun add(a: Int, b: Int): Int = a + b
fun mul(a: Int, b: Int): Int = a * b

fun partOne(resultMap: Map<Int, List<Int>>): Int {
	var validComboSum = 0


}
