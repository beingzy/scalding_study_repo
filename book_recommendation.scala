/* recommender: recommend similiar books to what a user likes as suggestions

   similiarity between book a and book b is measured with the
   correlation of ratiings

   author: Yi Zhang <yizhang@pinterst.com>
   date: 2018/04/28
*/
import scala.collection.mutable.{Map, ListBuffer}
import come.twitter.scalding._


// "data/BX-CSV-Dump/BX-Book-Ratings.csv"
// "data/BX-CSV-Dump/BX-Books.csv"
// "data/BX-CSV-Dump/BX-Users.csv"
val BASE_DATA_DIR = "data/BX-CSV-Dump/"
var data_path = Map[String, String]()
data_path ++= ("ratings" -> BASE_DATA_DIR + "BX-Book-Ratings.csv",
    "users" -> BASE_DATA_DIR + "BX-Books.csv",
    "books" -> BASE_DATA_DIR + "BX-Users.csv"
)
