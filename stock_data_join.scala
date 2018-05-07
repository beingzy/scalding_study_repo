import com.twitter.scalding._


class StockDivsJoin(args: Args) extends Job(args) {
  val stockSchema = ('symb, 'close, 'volume)
  val divsSchema = ('dymd, 'dividend)

  val stocksPipe = new Tsv(args("stocks"), stockSchema)
    .read
    .project('symd, 'close)
  val divsPipe = new Tsv(args("dividends"), divsSchema)
    .read

  stocksPipe
    .joinWithTiny('symb -> 'dymd, divsPipe)
    .project('symd, 'close, 'dividend)
    .write(Tsv(args("output")))
}