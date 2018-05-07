import com.twitter.scalding._


class WordCountJob(args: Args) extends Job(args) {

  TypedPipe.from(TextLine(args("input")))
    .flatMap { line => line.split("""\s+""") }
    .groupby { word => word }
    .size
    .write(TypedTsv(args("output")))

}