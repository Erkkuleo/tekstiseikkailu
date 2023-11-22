package o1.adventure

/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of “hard-coded” information that pertains to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Adventure:

  /** the name of the game */
  val title = "mörtin zonbipeli"

  private val bunkkeri  = Area("Bunkkeri", "Olet bunkkerissa.")
  private val aula      = Area("Aula", "Aulasta pääsee moneen suuntaan.")
  private val piha      = Area("Piha", "Pihalla on paljon omenapuita.")
  private val kaytava   = Area("Käytävä", "Käytävän seinillä on paljon tekstiä.")
  private val lab       = Area("Laboratorio", "Laboratoriossa on kaikenlaisia leluja.")
  private val n1        = Area("Null 1", "Tällä ei ole mitään.")
  private val klubi     = Area("Klubi", "Klubin pöydän ääressä istuu joku.")
  private val tekniikka = Area("Tekniikkahuone", "Täällä on paljon laitteita.")
  private val kvantti   = Area("Kvantti", "Tämä on universumin keskus.")
  private val n3        = Area("Null 3", "Zombi aloittaa tästä huoneesta.")
  private val asehuone  = Area("Asehuone", "Pum pum")
  private val n2        = Area("Null 2", "Täälläkään ei ole mitään.")
  private val vault     = Area("Holvi", "Kultaharkot kimaltavat, partavesi tuoksuu.")
  private val destination = lab

  bunkkeri .setNeighbors(Vector(                     "oikea" -> aula))
  aula     .setNeighbors(Vector("ylös" -> piha,      "oikea" -> n1,         "alas" -> kaytava,  "vasen" -> bunkkeri))
  piha     .setNeighbors(Vector(                                            "alas" -> aula))
  kaytava  .setNeighbors(Vector("ylös" -> aula,                             "alas" -> lab))
  lab      .setNeighbors(Vector("ylös" -> kaytava,   "oikea" -> n2))
  n1       .setNeighbors(Vector(                     "oikea" -> klubi,                          "vasen" -> aula))
  klubi    .setNeighbors(Vector("ylös" -> tekniikka, "oikea" -> n3,         "alas" -> asehuone, "vasen" -> n1))
  tekniikka.setNeighbors(Vector(                                            "alas" -> klubi,    "vasen" -> kvantti))
  kvantti  .setNeighbors(Vector(                     "oikea" -> tekniikka))
  n3       .setNeighbors(Vector(                                                                "vasen" -> klubi))
  asehuone .setNeighbors(Vector("ylös" -> klubi,                            "alas" -> n2))
  n2       .setNeighbors(Vector("ylös" -> asehuone,  "oikea" -> vault,                          "vasen" -> lab))
  vault    .setNeighbors(Vector(                                                                "vasen" -> n2))


  piha.addItem(Item("omena", "omena, äbbyl."))
  klubi.addItem(Item("weakness potion", "minecraftista tuttu, kyljessä lukee jotain korvien koskettelusta."))
  vault.addItem(Item("kultaharkko", "painaa paljon, melkeen yhtä paljon ku mä mutsiis."))
  tekniikka.addItem(Item("skanneri", "Skanneri kertoo, onko zombi jossain viereisistä huoneista."))
  asehuone.addItem(Item("ase", "tekee ase asioita"))
  kvantti.addItem(Item("arduino", "läähkistä"))
  aula.addItem(Item("kartta", "kertoo missä paikat ovat"))
  lab.addItem(Item("crafting recipe", "jotai"))

  /** The character that the player controls in the game. */
  val player = Player(bunkkeri)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 200


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = (this.player.location == this.destination) && hasNeededItems

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit

  private def hasNeededItems: Boolean = (this.player.inventory.contains("battery") && this.player.inventory.contains("remote"))

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "Heräät harmaasta bunkkerista. Päähäsi sattuu, etkä ole varma mitä on tapahtunut."


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage =
    if this.isComplete then
      "Sait lääkkeen valmistettua ja maailma pelastui. Hurraa! Kuitenkin olet pettynyt, ettet löytänytkään Arduinoa"
    else if this.turnCount == this.timeLimit then
      "Käytit liian monta vuoroa.\n Game over!"
    else  // game over due to player quitting
      "Luovuttaja!"


  /** Plays a turn by executing the given in-game Yo, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      this.turnCount += 1
    outcomeReport.getOrElse(s"""Tuntematon komento: "$command".""")

end Adventure

