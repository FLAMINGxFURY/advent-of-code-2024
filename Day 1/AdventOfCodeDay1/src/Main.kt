import java.io.File
import java.io.BufferedReader
import java.util.zip.ZipEntry
import kotlin.math.abs

fun main() {
    // Read in values
    val reader: BufferedReader = File("./input.txt").bufferedReader()
    val lines: List<String> = reader.readLines()
    val firstValues = mutableListOf<Int>()
    val secondValues = mutableListOf<Int>()

    for (line in lines) {
        firstValues.add(line.split("   ")[0].toInt())
        secondValues.add(line.split("   ")[1].toInt())
    }

    //partOne(firstValues, secondValues)

    //lets add a funny little comment
    partTwo(firstValues, secondValues)
}

fun partOne(firstValues: MutableList<Int>, secondValues: MutableList<Int>) {
    firstValues.sort()
    secondValues.sort()

    val pairs = firstValues.zip(secondValues)

    var sum: Int = 0

    for (pair in pairs) {
        sum += abs(pair.second - pair.first)
    }

    println(sum)
}

fun partTwo(firstValues: MutableList<Int>, secondValues: MutableList<Int>) {
    var sum: Int = 0

    for (i in 0..<firstValues.size) {
        val count = secondValues.count { it == firstValues[i] }
        sum += count * firstValues[i]
    }

    println(sum)
}