package o1.adventure

import o1.*

trait Character(startingArea: Area):

  private val liikkumissuunnat = Vector[String]("ylös", "oikea", "alas", "vasen")

  private var characterCurrentLocation = startingArea

  def currentLocation = this.characterCurrentLocation

  def suunnat = this.liikkumissuunnat

  def newLocation(newLocation: Area) =
    this.characterCurrentLocation = newLocation

  def location = this.characterCurrentLocation

end Character
