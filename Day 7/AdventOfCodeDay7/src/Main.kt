import java.io.File

fun main() {
    val reader = File("input.txt").bufferedReader()
    val lines = reader.readLines()

    val resultMap = mutableMapOf<Long, List<Long>>()

    // separate input into result: list of long
    for (line in lines) {
        val parts = line.split(": ")
        val result = parts[0].toLong()
        val inputs = parts[1].split(" ").map { it.toLong() }
        resultMap[result] = inputs
    }

    val result = partOne(resultMap)
    println(result)
}

fun add(a: Long, b: Long): Long = a + b
fun mul(a: Long, b: Long): Long = a * b

fun performCalculation(inputs: List<Long>, operations: List<((Long, Long) -> Long)>): Long {
    // starting with the first value...
    var result = inputs[0]

    for (i in 0..<inputs.size - 1) {
        // apply the operation to the result and the next input
        result = operations[i](result, inputs[i + 1])
    }
    return result
}

fun generateOps(numOps: Int): List<List<(Long, Long) -> Long>> {
    val operations = listOf(::add, ::mul) // list of callable function references (::) for both ops
    val allCombinations = mutableListOf<List<(Long, Long) -> Long>>()

    fun backtrack(current: MutableList<(Long, Long) -> Long>, depth: Int) {
        // Base case: If the current depth equals the number of operations, save the combination
        if (depth == numOps) {
            allCombinations.add(current.toList())
            return
        }

        for (op in operations) {
            current.add(op)
            backtrack(current, depth + 1) // recurse to fill the next position
            current.removeAt(current.lastIndex) // backtrack by removing the last added operation
        }
    }
    backtrack(mutableListOf(), 0)
    return allCombinations
}

fun partOne(resultMap: Map<Long, List<Long>>): Long {
    var validComboSum: Long = 0

    // for each list of inputs, try all permutations of add and mul between them
    for ((result, inputs) in resultMap) {
        // generate all possible combinations of add and mul
        val allOps = generateOps(inputs.size - 1)

        // for each combination of operations, calculate the result
        for (ops in allOps) {
            val calculatedResult = performCalculation(inputs, ops)
            if (calculatedResult == result) {
                validComboSum += calculatedResult
                break // break out of the loop if we find a valid combination
            }
        }
    }
    return validComboSum
}