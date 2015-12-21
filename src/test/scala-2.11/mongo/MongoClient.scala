package mongo

/**
 * Created by egorgorodov on 21.12.15.
 */

import com.mongodb._

class MongoClient(val host: String, val port: Int) {
  require(host != null, "You have to provide host")

  private val underlying = new Mongo(host, port)

  def this() = this("127.0.0.1", 27017)

  def version = underlying.getVersion

  def dropDb(name: String) = underlying.dropDatabase(name)

  def createDb(name: String) = DB(underlying.getDB(name))

  def db(name: String) = DB(underlying.getDB(name))
}
