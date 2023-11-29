package o1.adventure

import scala.collection.mutable.*
import o1.*
//import smcl.colors.rgb.ColorComponents.Opacity

import scala.util.Random

class Zombi(startingArea : Area) extends Character(startingArea):

  private var destinationNextRound = this.startingArea.neighbor("vasen")

  def zombiIsHealed = false

  /** Liikuttaa zombia satunnaisesti valittuun suuntaan. Metodi ensin käy läpi kaikki
    * mahdolliset suunnat, jonka jälkeen arpoo näistä jonkun, johon zombi liikkuu. */
  def zombiGo() =
    this.currentLocation.zombiLeaves()
    this.newLocation(destinationNextRound.getOrElse(this.currentLocation))
    this.currentLocation.zombiMovesHere() // liikkuu ennalta valittuun suuntaan

    var onValinnutValidinSuunnan = false
    var possibleDirections = Buffer[String]() // valitsee suunnan, johon liikkuu seuraavalla kierroksella
    for i <- this.suunnat do
      if this.currentLocation.neighbor(i).isDefined then
        possibleDirections += i
    val possibleDirSize = possibleDirections.size
    var newRandomDirection = possibleDirections(Random.nextInt(possibleDirSize))

    while !onValinnutValidinSuunnan do // tarkistetaan, ettei suunta ole kvantti tai holvi
      val potentialDestination = this.currentLocation.neighbor(newRandomDirection)
      if potentialDestination == Some("Kvantti") || potentialDestination == Some("Holvi") then
        newRandomDirection = possibleDirections(Random.nextInt(possibleDirSize))
      else
        onValinnutValidinSuunnan = true
    destinationNextRound = this.currentLocation.neighbor(newRandomDirection)

    println("Zombi menee: " + this.currentLocation.name)

  def nextLocation = this.destinationNextRound

  /** Palauttaa zombin sijainnin */
  override def toString = "Zombin sijainti: " + this.currentLocation.name

end Zombi

