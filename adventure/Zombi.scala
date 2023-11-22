package o1.adventure

import scala.collection.mutable.*
import o1.*
import scala.util.Random

class Zombi(startingArea : Area):

  private val suunnat = Vector[String]("ylös", "oikea", "alas", "vasen")

  private var currentLocation = startingArea

  /** Palauttaa zombin sijainnin */
  def location = this.currentLocation


  /** Liikuttaa zombia satunnaisesti valittuun suuntaan. Metodi ensin käy läpi kaikki
    * mahdolliset suunnat, jonka jälkeen arpoo näistä jonkun, johon zombi liikkuu. */
  def zombiGo(): Unit =
    var possibleDirections = Buffer[String]()
    for i <- suunnat do
      if location.neighbor(i).isDefined then
        possibleDirections += i
    val possibleDirSize = possibleDirections.size
    val newRandomDirection = possibleDirections(Random.nextInt(possibleDirSize - 1))
    val destination = this.location.neighbor(newRandomDirection)
    this.currentLocation = destination.getOrElse(this.currentLocation)

  /** Palauttaa zombin sijainnin */
  override def toString = "Zombin sijainti: " + this.location.name

end Zombi

