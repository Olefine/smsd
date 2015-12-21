/**
 * Created by egorgorodov on 19.12.15.
 */


object test_script extends App {

  def loopTill(cond: => Boolean)(block: => Unit): Unit = {
    if(cond) {
      block
      loopTill(cond)(block)
    }
  }

  val computers = Array(
    Map("name" -> "Mackbook", "color" -> "white"),
    Map("name" -> "HP Pavilion", "color" -> "black")
  )

  implicit class MyType(val self: Int) extends AnyVal {
    def apply(number: Int) {
      new MyType(number)
    }
    def times(block: => Unit) = {
      var i = self

      def callBlock(iter: Int): Unit = {
        if(iter > 0) {
          block
          callBlock(iter - 1)
        }
      }

      callBlock(i)
    }
  }

  val height = 1.82
  val name = "Egor"

//  println(f"$name has $height height")

  val breakExc = new RuntimeException("break exception")
  def break = throw breakExc

  def breakable(op: => Unit) = {
    try {
      op
    } catch { case _ => }
  }

//  breakable {
//    val env = System.getenv("RAILS_ENV")
//    if (env == null) break
//    println("found RAILS_ENV")
//  }

}

