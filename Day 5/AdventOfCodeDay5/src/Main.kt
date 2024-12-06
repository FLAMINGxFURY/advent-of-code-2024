import java.io.File
import java.io.BufferedReader

fun main() {
	//Read in input
	val reader = File("input.txt").bufferedReader()
	val lines = reader.readLines()

	//Items before the blank line are the rules
	val rules = mutableMapOf<Int, MutableList<Int>>()
	val ruleLines = lines.takeWhile { it.isNotBlank() }
	for (line in ruleLines) {
		val (page, mustProceed) = line.split('|').map { it.toInt() }

		//If rule has not been added yet, add it
		if (!rules.containsKey(page)) {
			rules[page] = mutableListOf()
		}
		//Add the rule
		rules[page]!!.add(mustProceed) //This is some C# null assertive bullshit. I thought I could get away from this :(
	}

	//Items after the blank line are the print orders
	val printOrders = lines.drop(ruleLines.size + 1)

	val result = partOne(rules, printOrders)
	println(result)
}

fun partOne(rules: Map<Int, List<Int>>, printOrders: List<String>): Int{
	val correctListMiddleValues = mutableListOf<Int>()
	val incorrectOrders = mutableListOf<MutableList<Int>>()

	for (order in printOrders) {
		val items = order.split(",").map { it.toInt() }

		var failedOrder = false

		//For each value in the order, ensure nothing preceding that value is in its mustProceed list
		for (i in 1..<items.size) {
			val item = items[i]
			val mustProceed = rules[item] ?: continue

			//If any of the preceding values are in the mustProceed list, the order is invalid
			for (j in 0..<i) {
				if (mustProceed.contains(items[j])) {
					failedOrder = true
					break
				}
			}
		}

		//If the order is valid, add the middle value to the list
		if (!failedOrder) {
			correctListMiddleValues.add(items[items.size / 2])
		}
	}

	return correctListMiddleValues.sum()
}

