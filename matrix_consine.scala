import com.twitter.scalding._
import com.twitter.scalding.mathematics.Matrix


// load a directed graph adjancy matrix where:
// a[i, j] = 1 if there is an edge from a[i] to a[j]
// and computes the cosine of the angle between every
// two pairs of vectors.
class MatrixCosine(args: Args) extends Job(args) {
  import Matrix._
  val schema = ('user1, 'user2, 'relation)
  val adjacencyMatrix = Tsv(args("input"), schema)
    .read
    .toMatrix[Long, Long, Double](schema)
  val normMatrix = adjancyMatrix.rowL2Normalize

  // Inner product is equivalent to the cosine:
  //   AA^T / (||A|| * ||A||)
  (normMatrix * normMatrix.tranpose)
    .write(Tsv(args("output")))
}