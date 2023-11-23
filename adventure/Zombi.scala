package o1.adventure

import scala.collection.mutable.*
import o1.*
import scala.util.Random

class Zombi(startingArea : Area) extends Character(startingArea):

  /** Liikuttaa zombia satunnaisesti valittuun suuntaan. Metodi ensin käy läpi kaikki
    * mahdolliset suunnat, jonka jälkeen arpoo näistä jonkun, johon zombi liikkuu. */
  def zombiGo(): String =
    var possibleDirections = Buffer[String]()
    for i <- this.suunnat do
      if this.currentLocation.neighbor(i).isDefined then
        possibleDirections += i
    val possibleDirSize = possibleDirections.size
    val newRandomDirection = possibleDirections(Random.nextInt(possibleDirSize))
    val destination = this.currentLocation.neighbor(newRandomDirection)
    this.currentLocation.zombiLeaves()
    this.newLocation(destination.getOrElse(this.currentLocation))
    this.currentLocation.zombiMovesHere()
    "Zombi menee: " + this.currentLocation.name

  /** Palauttaa zombin sijainnin */
  override def toString = "Zombin sijainti: " + this.currentLocation.name

end Zombi

