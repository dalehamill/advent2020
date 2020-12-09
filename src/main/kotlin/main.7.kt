import java.io.File

val TO_MAP = hashMapOf<String, ArrayList<String>>()
val COUNT_MAP = hashMapOf<String, Int>()

fun main(args: Array<String>) {

  val map = hashMapOf<String, HashSet<String>>() // keys (bags) to what holds them
  File("src/main/resources/data.7.txt").forEachLine { it: String ->
    val parsedRule = it.split(" contain ")
    if (!parsedRule.last().contains("no other bags")) {
      val parsedKey = parseKeys(parsedRule.first())

      parsedRule.last().split(',').forEach() {
        val parsedContainedBag = parseKeys(it)

        // extract count, add to our toMap for part 2
        val cnt = "[0-9]+".toRegex().find(it)?.value?.toInt() ?: 0
        (1..cnt).forEach() {
          TO_MAP[parsedKey] = TO_MAP[parsedKey] ?: arrayListOf()
          TO_MAP[parsedKey]?.add(parsedContainedBag)
        }

        // add map from contained bag back to what type can contain it
        val containingList = map.getOrDefault(parsedContainedBag, hashSetOf())
        containingList.add(parsedKey)
        map[parsedContainedBag] = containingList
      }
    }
  }

  // now find paths to at least one bag holding shiny gold
  val loopingSet = hashSetOf<String>()
  loopingSet.addAll(map["shiny gold"].orEmpty())
  val set = hashSetOf<String>()

  while (loopingSet.isNotEmpty()) {
    val key = loopingSet.first()
    loopingSet.remove(key)

    if (!set.contains(key)) {
      set.add(key)
      loopingSet.addAll(map[key].orEmpty())
    }
  }

  println("Found bag options: ${set.size}")

  val totalBagCount = countBagsForBag("shiny gold")
  println("Total bags needed: $totalBagCount")
}

fun parseKeys(source: String): String {
  return source.replace("[0-9]+".toRegex(), "").replace("bag[s]?\\.?".toRegex(), "").trim()
}

fun countBagsForBag(bag: String): Int {
  var count = 0
  TO_MAP[bag]?.forEach {
    if (COUNT_MAP.containsKey(it)) {
      count += COUNT_MAP[it] ?: 0
    } else {
      val cnt = countBagsForBag(it) + 1
      count += cnt
      COUNT_MAP[it] = cnt
    }
  }
  return count
}