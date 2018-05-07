import com.twitter.scalding._


class ContextNGrams(args: Args) extends Job(args) {
  val ngramPrefix =
    args.list("ngram-prefix").mkString(" ")
  val keepN = args.getOrElse("count", "10").toInt
  val ngramRE = (ngramPrefix + """\s+(\w+)""").r

  // sort (phrase, count) by count, descending
  val countReverseComparator =
    (tuple1: (String, Int), tupe2: (String, Int))
      => tuple1._2 > tuple2._2

  val lines = TextLine(args("input"))
    .read
    .flatMap('line -> 'ngram) {
      text: String => ngramRE.findAllIn(text).toIterable
    }
    .discard('num, 'line)
    .groupby('ngram) { g => g.size('count) }
    .groupAll { g =>
      g.sortWithTake[(String, Int)](
        ('ngram, 'count) -> 'sorted_ngrams, keepN)(
        countReverseComparator)
    }
    .write(Tsv(args("output")))
}