import java.io.File

fun main(args: Array<String>) {
    var countFirst = 0
    var countSecond = 0
    File("src/main/resources/data.2.txt").forEachLine {
        val splitit = it.split(':')
        val ruleit = splitit.first().trim()
        val rulesplit = ruleit.split(' ')
        val countsplit = rulesplit.first().split('-')
        val numFirst = countsplit.first().toInt()
        val numSecond = countsplit.last().toInt()
        val ch = rulesplit.last().trim().toCharArray().first()

        val password = splitit.last().trim()

        val resultFirst = testPasswordFirst(password, ch, numFirst, numSecond)
        println("$it first test resolves to $resultFirst")
        if (resultFirst) countFirst++

        val resultSecond = testPasswordSecond(password, ch, numFirst - 1, numSecond - 1)
        println("$it second test resolves to $resultSecond")
        if (resultSecond) countSecond++
    }
    println("First result: $countFirst")
    println("Second result: $countSecond")
}

fun testPasswordFirst(password: String, ch: Char, minCnt: Int, maxCnt: Int): Boolean {
    // count occurances
    var cnt = 0
    password.forEach {
        if (it == ch) cnt++
    }

    return cnt in minCnt..maxCnt
}

fun testPasswordSecond(password: String, ch: Char, index1: Int, index2: Int): Boolean {
    return (password[index1] == ch).xor(password[index2] == ch)
}