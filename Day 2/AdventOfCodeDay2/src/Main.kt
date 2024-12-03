import java.io.File
import java.io.BufferedReader

fun main() {
    val input: BufferedReader = File("input.txt").bufferedReader()
    val allLines: List<String> = input.readLines()

    val result: Int = partTwo(allLines)

    println(result)
}

fun partOne(lines: List<String>): Int {
    var safeCount = 0

    for (line in lines) {
        val values = line.split(" ").map { it.toInt() }
        var isSafe = true

        // Determine if the report is ascending or descending
        val isAscending = values[1] > values[0]

        for (i in 0..<values.size - 1) {
            val diff = values[i + 1] - values[i]

            // Check if the difference is out of bounds (not between 1 and 3)
            if (diff !in 1..3 && isAscending || diff !in -3..-1 && !isAscending) {
                isSafe = false
                break
            }

            // Check if the sequence breaks the order (ascending/descending)
            if (isAscending && values[i + 1] <= values[i] || !isAscending && values[i + 1] >= values[i]) {
                isSafe = false
                break
            }
        }

        if (isSafe) {
            safeCount += 1
        }
    }
    return safeCount
}

fun partTwo(lines: List<String>): Int {
    var safeCount = 0

    for (line in lines) {
        val values = line.split(" ").map { it.toInt() }
        if (isReportSafe(values)) {
            safeCount += 1
        } else {
            // Check if removing one level makes the report safe
            for (i in values.indices) {
                val modifiedValues = values.filterIndexed { index, _ -> index != i }
                if (isReportSafe(modifiedValues)) {
                    safeCount += 1
                    break
                }
            }
        }
    }

    return safeCount
}

// Helper function to check if a report is safe
fun isReportSafe(values: List<Int>): Boolean {
    if (values.size < 2) return true // A single level or empty report is safe

    val isAscending = values[1] > values[0]

    for (i in 0..<values.size - 1) {
        val diff = values[i + 1] - values[i]

        // Check if the difference is out of bounds
        if (isAscending && diff !in 1..3 || !isAscending && diff !in -3..-1) {
            return false
        }

        // Check if the sequence breaks the order (ascending/descending)
        if (isAscending && values[i + 1] <= values[i] || !isAscending && values[i + 1] >= values[i]) {
            return false
        }
    }

    return true
}
