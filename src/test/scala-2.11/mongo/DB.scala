package mongo

/**
 * Created by egorgorodov on 21.12.15.
 */

import com.mongodb.{DB => MongoDB}
import scala.collection.convert.Wrappers._

class DB private(val underlying: MongoDB) {
  private def collection(name: String) = underlying.getCollection(name)

  def collectionNames = for(name <- new JSetWrapper(underlying.getCollectionNames)) yield name

  def readOnlyCollection(name: String) = new DBCollection(collection(name)) with Memoizer

  def administrableCollection(name: String) = new DBCollection(collection(name)) with Administrable with Memoizer

  def updatableCollection(name: String) = new DBCollection(collection(name)) with Updatable with Memoizer
}

object DB {
  def apply(underlying: MongoDB) = new DB(underlying)
}
