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

	//val result = partOne(rules, printOrders)
	val result = partTwo(rules, printOrders)
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

fun findIncorrectLists(rules: Map<Int, List<Int>>, printOrders: List<String>): List<MutableList<Int>> {
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

		//If the order is invalid, add it to the list
		if (failedOrder) {
			incorrectOrders.add(items.toMutableList())
		}
	}

	return incorrectOrders
}

//Depth-first search to find the post-order traversal of the subgraph
fun dfs(page: Int, subgraph: Map<Int, List<Int>>, visited: MutableSet<Int>, postOrder: MutableList<Int>) {
	visited.add(page)

	for (mustProceed in subgraph[page] ?: return) {
		if (mustProceed !in visited) {
			dfs(mustProceed, subgraph, visited, postOrder)
		}
	}

	postOrder.add(page)
}

fun partTwo(rules: Map<Int, List<Int>>, printOrders: List<String>): Int {
	val incorrectOrders = findIncorrectLists(rules, printOrders)
	val correctedMiddleValues = mutableListOf<Int>()

	for (order in incorrectOrders) {
		//Using rules as an adjacency matrix, create a subgraph that represents only rules for this order.
		val subgraph = mutableMapOf<Int, MutableList<Int>>()

		for((page, mustProceed) in rules) {
			if (page in order) {
				//Only include rules that are in the order
				subgraph[page] = mustProceed.filter { it in order }.toMutableList()
			}
		}

		//Using a depth-first search, we can ensure all rules are followed.
		//The post-order traversal of the DFS will be the corrected order.
		val visited = mutableSetOf<Int>()
		val postOrder = mutableListOf<Int>()

		for (page in order) {
			if (page !in visited) {
				dfs(page, subgraph, visited, postOrder)
			}
		}

		//To convert this to a post-order traversal, we need to reverse the list
		postOrder.reverse()

		correctedMiddleValues.add(postOrder[postOrder.size / 2])
	}

	return correctedMiddleValues.sum()
}