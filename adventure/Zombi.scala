package o1.adventure

import scala.collection.mutable.*
import o1.*
import scala.util.Random

class Zombi(startingArea : Area) extends Character(startingArea):

  private var destinationNextRound = this.startingArea.neighbor("vasen")
  
  /** Liikuttaa zombia satunnaisesti valittuun suuntaan. Metodi ensin käy läpi kaikki
    * mahdolliset suunnat, jonka jälkeen arpoo näistä jonkun, johon zombi liikkuu. */
  def zombiGo(): String =
    this.currentLocation.zombiLeaves()
    this.newLocation(destinationNextRound.getOrElse(this.currentLocation))
    this.currentLocation.zombiMovesHere() // liikkuu ennalta valittuun suuntaan
    
    var possibleDirections = Buffer[String]() // valitsee suunnan, johon liikkuu seuraavalla kierroksella
    for i <- this.suunnat do
      if this.currentLocation.neighbor(i).isDefined then
        possibleDirections += i
    val possibleDirSize = possibleDirections.size
    val newRandomDirection = possibleDirections(Random.nextInt(possibleDirSize))
    destinationNextRound = this.currentLocation.neighbor(newRandomDirection)
    
    "Zombi menee: " + this.currentLocation.name

  def nextLocation = this.destinationNextRound
  
  /** Palauttaa zombin sijainnin */
  override def toString = "Zombin sijainti: " + this.currentLocation.name

end Zombi

