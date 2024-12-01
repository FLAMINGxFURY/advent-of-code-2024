import java.io.File
import java.io.BufferedReader

fun main() {
    val reader: BufferedReader = File("./input.txt").bufferedReader()
    val lines: List<String> = reader.readLines()

    for (line in lines) {
        println(line)
    }
}