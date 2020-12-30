import java.io.File

fun main(args: Array<String>) {
    val rangeMap = hashMapOf<String, ArrayList<IntRange>>()
    val nearbyTicketList = arrayListOf<List<Int>>()
    val validTicketList = arrayListOf<List<Int>>()
    var myTicket = arrayListOf<Int>()
    var isRule = true
    var isNearbyTicket = false
    var isMyTicket = false
    File("src/main/resources/data.16.txt").forEachLine { s ->
        if (isRule) {
            if (s.isNotEmpty()) {
                val key = s.split(":")[0]
                val rangeStrings = s.split(":")[1].split("or").map { it.trim() }

                for (r in rangeStrings) {
                    val split = r.split('-').map { it.toInt() }
                    val intRange = IntRange(split[0], split[1])

                    val existingRange = rangeMap.getOrDefault(key, arrayListOf())
                    existingRange.add(intRange)
                    rangeMap[key] = existingRange
                }
            } else {
                isRule = false // end of rule
            }
        } else if (isNearbyTicket) {
            nearbyTicketList.add(s.split(',').map { it.toInt() })
        } else if (isMyTicket) {
            myTicket = s.split(',').map { it.toInt() } as ArrayList<Int>
            isMyTicket = false
        } else if (s == "your ticket:") {
            isMyTicket = true
        } else if (s == "nearby tickets:") {
            isNearbyTicket = true
        }
    }

    var errorRate = 0
    for (ticket in nearbyTicketList) {
        for (index in ticket.indices) {
            val value = ticket[index]
            var matchesOneRange = false
            for (rangeKey in rangeMap.keys) {
                val rangeList = rangeMap[rangeKey]
                if (rangeList != null) {
                    for (range in rangeList) {
                        matchesOneRange = matchesOneRange || value in range
                    }
                }
            }
            if (!matchesOneRange) {
                errorRate += value
                break
            }
            else {
                validTicketList.add(ticket)
            }
        }
    }

    println("Part 1: $errorRate")

    var fieldMult = 0
    println("Part 2: $fieldMult")
}
