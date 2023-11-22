package o1.adventure
import scala.collection.mutable.*

class NPC(val name: String, val liners: Buffer[String]):

  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name

end NPC

