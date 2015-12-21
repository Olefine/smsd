package mongo

/**
 * Created by egorgorodov on 21.12.15.
 */
import com.mongodb.{DBCollection => MongoDBCollection}
import com.mongodb.DBObject

trait ReadOnly {
  val underlying: MongoDBCollection

  def name = underlying.getName
  def fullName = underlying.getFullName
  def find(doc: DBObject) = underlying.find(doc)
  def findOne(doc: DBObject) = underlying.findOne(doc)
  def findOne = underlying.findOne
  def getCount(doc: DBObject) = underlying.getCount(doc)
}

trait Administrable extends ReadOnly {
  def drop: Unit = underlying.drop
  def dropIndex: Unit = underlying.dropIndexes()
}

trait Updatable extends ReadOnly {
  def -=(doc: DBObject): Unit = underlying.remove(doc)
  def +=(doc: DBObject): Unit = underlying.save(doc)
}

trait Memoizer extends ReadOnly {
  private val history = scala.collection.mutable.Map[Int, DBObject]()

  override def findOne = {
    history.getOrElseUpdate(-1, { super.findOne })
  }

  override def findOne(doc: DBObject) = {
    history.getOrElseUpdate(doc.hashCode, { super.findOne(doc) })
  }
}

class DBCollection(override val underlying: MongoDBCollection) extends ReadOnly
