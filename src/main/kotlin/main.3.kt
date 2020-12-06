import java.io.File

fun main(args: Array<String>) {
    var countOne = 0
    var countThree = 0
    var countFive = 0
    var countSeven = 0
    var countEveryOther = 0

    var row = 0
    File("src/main/resources/data.3.txt").forEachLine {
        if (isTree(it, row)) countOne++
        if (isTree(it, row * 3)) countThree++
        if (isTree(it, row * 5)) countFive++
        if (isTree(it, row * 7)) countSeven++

        if (row % 2 == 0 && isTree(it, row / 2)) countEveryOther++

        // move location
        row++
    }

    val product = countOne * countThree * countFive * countSeven * countEveryOther

    println("Count Trees, 1 over: $countOne")
    println("Count Trees, 3 over: $countThree")
    println("Count Trees, 5 over: $countFive")
    println("Count Trees, 7 over: $countSeven")
    println("Count Trees, every other: $countEveryOther")
    println("Count Trees, multiplied, 1/3/5/7/skps: $product")
}

fun isTree(row: String, index: Int): Boolean {
    return row[index % row.length] == '#'
}