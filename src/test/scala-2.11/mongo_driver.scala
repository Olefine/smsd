/**
 * Created by egorgorodov on 21.12.15.
 */

//just to demonstate import mapping
import java.util.Date
import java.sql.{Date => SqlDate}
import com.mongodb.BasicDBObject
import mongo._

object mongo_driver extends App {
  val client = new MongoClient()
  val db = client.createDb("mydb")

  for(name <- db.collectionNames) println(name)

  val col = db.readOnlyCollection("my_collection")
  println(col.name)

  val adminCol = db.administrableCollection("my_collection")
  adminCol.drop

  val updatableCol = db.updatableCollection("my_collection")

  val doc = new BasicDBObject()
  doc.put("name", "MongoDB")
  doc.put("type", "database")
  doc.put("count", 1)

  val info = new BasicDBObject()
  info.put("x", 203)
  info.put("y", 102)
  doc.put("info", info)
  updatableCol += doc

  println(updatableCol.findOne)

  updatableCol -= doc

  println(updatableCol.findOne)

  for(i <- 1 to 100) updatableCol += new BasicDBObject("i", i)

  val query = new BasicDBObject()
  query.put("i", 71)

  val cursor = col.find(query)

  while(cursor.hasNext) {
    println(cursor.next())
  }
}
