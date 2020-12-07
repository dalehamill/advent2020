import java.io.File

fun main(args: Array<String>) {
    var sumTotalAnyone = 0
    var sumTotalEveryone = 0
    var countGroupMembers = 0
    val qSet = hashSetOf<Char>()
    val qMap = hashMapOf<Char, Int>()
    File("src/main/resources/data.6.txt").forEachLine {
        if (it.trim().isEmpty()) {
            sumTotalAnyone += qSet.size

            qMap.forEach() {
                if (it.value == countGroupMembers) sumTotalEveryone++
            }

            qSet.clear()
            qMap.clear()
            countGroupMembers = 0
        } else {
            it.trim().forEach {
                qSet.add(it) // add that someone answered this

                qMap.put(it, qMap.getOrDefault(it, 0) + 1)
            }
            countGroupMembers++
        }
    }
    sumTotalAnyone += qSet.size
    qMap.forEach() {
        if (it.value == countGroupMembers) sumTotalEveryone++
    }

    println("Sum of total answers: $sumTotalAnyone")
    println("Sum everyone answered: $sumTotalEveryone")
}