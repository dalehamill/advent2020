import java.io.File


enum class CommandEnum {
    NOP, ACC, JMP
}

fun main(args: Array<String>) {
    val commands = arrayListOf<Pair<CommandEnum, Int>>()
    File("src/main/resources/data.8.txt").forEachLine {
        val parsedLine = it.split(" [+|-]".toRegex())
        var cntParam = parsedLine.last().toInt()
        if (it.contains('-')) cntParam *= -1
        commands.add(Pair(CommandEnum.valueOf(parsedLine.first().toString().toUpperCase()), cntParam))
    }

    println("Accumulator with original commands at: ${executeCommands(commands)}")

    println("Accumulator with edited commands at: ${executeCommands(commands, mustFinish = true, hasChanged = false)}")
}

fun executeCommands(
    commands: ArrayList<Pair<CommandEnum, Int>>,
    visitedIndexes: HashSet<Int> = hashSetOf(),
    startingIndex: Int = 0,
    mustFinish: Boolean = false,
    hasChanged: Boolean = false
): Int {
    var currCommandIndex = startingIndex
    var accum = 0
    while (currCommandIndex < commands.size) {
        visitedIndexes.add(currCommandIndex)
        val commandPair = commands[currCommandIndex]

        val recurseVisitedIndexes = hashSetOf<Int>()
        recurseVisitedIndexes.addAll(visitedIndexes)

        when (commandPair.first) {
            CommandEnum.NOP -> {
                if (mustFinish && !hasChanged) {
                    val runWithChange = executeCommands(
                        commands,
                        recurseVisitedIndexes,
                        currCommandIndex + commandPair.second,
                        true,
                        true
                    )
                    if (runWithChange >= 0) return accum + runWithChange
                }
                currCommandIndex++
            }
            CommandEnum.JMP -> {
                if (mustFinish && !hasChanged) {
                    val runWithChange =
                        executeCommands(commands, recurseVisitedIndexes, currCommandIndex + 1, true, true)
                    if (runWithChange >= 0) return accum + runWithChange
                }
                currCommandIndex += commandPair.second
            }
            CommandEnum.ACC -> {
                accum += commandPair.second
                currCommandIndex++
            }
        }

        // test for repeated command
        if (visitedIndexes.contains(currCommandIndex)) {
            return if (mustFinish) -1 else accum
        }
    }
    return accum // finished!
}