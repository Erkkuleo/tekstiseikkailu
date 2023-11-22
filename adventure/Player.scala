package o1.adventure

import scala.collection.mutable.Map

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingArea  the player’s initial location */
class Player(startingArea: Area):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val playerInventory = Map[String, Item]()

  def get(itemname: String) =
    if this.currentLocation.contains(itemname) then
      val removedItem = this.currentLocation.removeItem(itemname)
      removedItem.foreach( n => this.playerInventory += n.name -> n )
      s"You pick up the ${itemname}."
    else
      s"There is no ${itemname} here to pick up."

  def drop(itemname: String) =
    val itemFromMap = this.playerInventory.get(itemname)
    itemFromMap match
      case None => s"You don't have that!"
      case Some(n) =>
        this.playerInventory.remove(n.name)
        this.currentLocation.addItem(n)
        s"You drop the ${itemname}."

  def inventory =
    if this.playerInventory.isEmpty then
      "You are empty-handed"
    else
      "You are carrying: " + s"\n${this.playerInventory.keys.mkString("\n")}"

  def examine(itemname: String): String =
    val itemFromInventory = this.playerInventory.get(itemname)
    itemFromInventory match
      case None    => "If you want to examine something, you need to pick it up first."
      case Some(n) => s"You look closely at the ${n.name}.\n${n.description}"

  def has(itemname: String): Boolean =
    this.playerInventory.contains(itemname)

  def map: String =
    if this.has("kartta") then
      "                 #############                 #############             \n"+
      "                 #           #                 #           #             \n" +
      "                 #  PIHA     #                 # TEKNIIKKA #             \n" +
      "                 #           #                 #           #             \n" +
      "                 #############                 #############             \n" +
      "                     #                             #                     \n" +
      "                     #                             #                     \n" +
      "#############  #############  #############  #############  #############\n" +
      "#           #  #           #  #           #  #           #  #           #\n" +
      "#  BUNKKERI ####  AULA     #### NULL      ####  KLUBI    #### NULL      #\n" +
      "#           #  #           #  #           #  #           #  #           #\n" +
      "#############  #############  #############  #############  #############\n" +
      "                     #                             #                     \n" +
      "                     #                             #                     \n" +
      "               #############                 #############               \n" +
      "               #           #                 #           #               \n" +
      "               #  KÄYTÄVÄ  #                 #ASEVARASTO #               \n" +
      "               #           #                 #           #               \n" +
      "               #############                 #############               \n" +
      "                     #                             #                     \n" +
      "                     #                             #                     \n" +
      "               #############                 #############  #############\n" +
      "               #           #                 #           #  #           #\n" +
      "               #LABORATORIO###################  NULL     #### HOLVI     #\n" +
      "               #           #                 #           #  #           #\n" +
      "               #############                 #############  #############\n"
    else
      "Sinulla ei ole karttaa"

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the player’s current location. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) =
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if destination.isDefined then "You go " + direction + "." else "You can't go " + direction + "."



  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() =
    "You rest for a while. Better get a move on, though."


  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""

  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

end Player

