package o1.adventure
import scala.collection.mutable
import scala.collection.mutable.*
import scala.io.StdIn.*
import scala.util.Random


/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingArea  the player’s initial location */
class Player(startingArea: Area) extends Character(startingArea):

  private var quitCommandGiven = false              // one-way flag
  private val playerInventory = Map[String, Item]()

  def get(itemname: String) =
    if this.currentLocation.contains(itemname) then
      val removedItem = this.currentLocation.removeItem(itemname)
      removedItem.foreach( n => this.playerInventory += n.name -> n )
      s"Poimit ${itemname}."

    else
      s"Täällä ei ole ${itemname} poimittavaksi."

  def drop(itemname: String) =
    val itemFromMap = this.playerInventory.get(itemname)
    itemFromMap match
      case None => s"Sinulla ei ole tuota!"
      case Some(n) =>
        this.playerInventory.remove(n.name)
        this.currentLocation.addItem(n)
        s"Tiputat ${itemname}."

  def syö(itemName:  String) =
    val itemFromInv = this.playerInventory.get(itemName)
    itemFromInv match
      case None => s"Sinulla ei ole tuota!"
      case Some(n) =>
        if n.name == "omena" then
          this.playerInventory.remove(n.name)
          s"syöt omenan"
        else
          s"Ei tätä voi syödä!"
      case _ => "Ei tätä voi syödä!"

  def inventory =
    if this.playerInventory.isEmpty then
      "Tavaraluettelo on tyhjä"
    else
      "Tavaraluettelossasi on: " + s"\n${this.playerInventory.keys.mkString("\n")}"

  def examine(itemname: String): String =
    val itemFromInventory = this.playerInventory.get(itemname)
    itemFromInventory match
      case None    => "Jos haluat tutkia jotain poimi se ensin ylös."
      case Some(n) => s"Katsot tarkasti ${n.name}.\n${n.description}"


  def juttele(NPC: String): String =
    val npc = this.location.getNpc.get(NPC)
    npc match
      case None    => "Jos haluat tutkia jotain poimi se ensin ylös."
      case Some(n) => s"${n.name}: ${n.liners.head}"


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

  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) =
    val destination = this.currentLocation.neighbor(direction)
    if destination.getOrElse(this.currentLocation).name != "Holvi" then //holvin slaus systeemi
      this.newLocation(destination.getOrElse(this.currentLocation)) // tämä toteutetaan jos kyseessä ei ole holvi
      if destination.isDefined then
        "Menet " + direction + "."
      else "Ei ole mahdollista mennä suuntaan " + direction + "."
    else
      val input = readLine("\n Anna salasana:\n").toIntOption // jos kyseessä on holvi
      if input.get == 2396 then // vaihda salasana haluamaasi
        this.newLocation(destination.getOrElse(this.currentLocation)) // jos salasana oikein siirrytään huoneeseen
        "Menet " + direction + "."
      else // muuten pidetään tämä lokaatio
        "Ei ole mahdollista mennä suuntaan " + direction + "."


  def meetsZombie: Boolean = this.currentLocation.zombiIsHere

  def zombiLocation =
    if has("skanneri") then
      val suunnat = this.currentLocation.validDirections.map(n => this.currentLocation.neighbor(n))
      val zombisuunta = suunnat.find(n => n.get.zombiIsHere).flatten
      if zombisuunta.isDefined then
        s"zombi on viereisessä huoneessa ${zombisuunta.head.name}"
      else
        "zombi ei ole viereisessä huoneessa"
    else
      "sinulla ei ole scanneria"

  def battle =
    if meetsZombie then
      var r = Random()
      var zombieRandom = r.nextInt(100)
      var playerRandom = r.nextInt(100)
      if (zombieRandom - playerRandom) <= 0 then
        true
      else
        false
    else
      false

  def help : String =
    "Tässä kaikki komennot:\n" +
      s"${Console.GREEN} help ${Console.RESET}- Tulostaa tämän tekstin.\n" +
      s"${Console.GREEN} mene (suunta) ${Console.RESET}- liiku tässä suunnassa olevaan huoneeseen.\n" +
      s"${Console.GREEN} poimi (esine) ${Console.RESET}- poimi huoneesta löytyvä esine.\n" +
      s"${Console.GREEN} tiputa (esine) ${Console.RESET}- Tiputa tavaraluettelossasi ollut esine.\n" +
      s"${Console.GREEN} tavaraluettelo ${Console.RESET}- Listaa tavaraluettelostasi löytyvät esineet.\n" +
      s"${Console.GREEN} kartta ${Console.RESET}- Tulostaa alueen kartan mikäli sinulla on sellainen.\n" +
      s"${Console.GREEN} syö ${Console.RESET}- Voit syödä omenan. Syö omena :) SYÖ OMENA!  \n"

  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""

  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Nyt olet paikassa: " + this.location.name

end Player