package o1.adventure

import scala.collection.mutable.*
import scala.io.StdIn.*
import scala.util.Random
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
  val title = "Mörtin zombipeli"

  private val bunkkeri  = Area("Bunkkeri", "Olet bunkkerissa.")
  private val aula      = Area("Aula", "Aulasta pääsee moneen suuntaan.")
  private val piha      = Area("Piha", "Pihalla on paljon omenapuita ja herkullisen näköistä raikasta vettä.")
  private val kaytava   = Area("Käytävä", "Käytävän seinillä on paljon lappuja ja ilmoituksia. Erääseen papereista on kirjoitettu numerosarja 2396.")
  private val lab       = Area("Laboratorio", "Laboratoriossa on kaikenlaisia leluja.")
  private val n1        = Area("Työhuone", "Mahonkisella työpöydällä on paljon tärkeän näköisiä asiakirjoja.")
  private val klubi     = Area("Klubi", "Klubin pöydän ääressä istuu joku.")
  private val tekniikka = Area("Tekniikkahuone", "Täällä on paljon laitteita.")
  private val kvantti   = Area("Kvantti", "Tämä on universumin keskus.")
  private val n3        = Area("Makuuhuone", "Huoneen lattialla on verta ja revenneitä vaatekappaleita. Joku on varmaankin muuttunut täällä zombiksi.")
  private val asehuone  = Area("Asevarasto", "Pum pum")
  private val n2        = Area("Keittiö", "Keittiössä tuoksuu hyvältä, mutta nyt ei ole aikaa jäädä nautiskelemaan.")
  private val vault     = Area("Holvi", "Kultaharkot kimaltavat, partavesi tuoksuu.")
  private val destination = lab

  bunkkeri .setNeighbors(Vector("ei minnekään" -> bunkkeri ,                      "oikea" -> aula))
  aula     .setNeighbors(Vector("ei minnekään" -> aula     , "ylös" -> piha,      "oikea" -> n1,         "alas" -> kaytava,  "vasen" -> bunkkeri))
  piha     .setNeighbors(Vector("ei minnekään" -> piha     ,                                             "alas" -> aula))
  kaytava  .setNeighbors(Vector("ei minnekään" -> kaytava  , "ylös" -> aula,                             "alas" -> lab))
  lab      .setNeighbors(Vector("ei minnekään" -> lab      , "ylös" -> kaytava,   "oikea" -> n2))
  n1       .setNeighbors(Vector("ei minnekään" -> n1       ,                      "oikea" -> klubi,                          "vasen" -> aula))
  klubi    .setNeighbors(Vector("ei minnekään" -> klubi    , "ylös" -> tekniikka, "oikea" -> n3,         "alas" -> asehuone, "vasen" -> n1))
  tekniikka.setNeighbors(Vector("ei minnekään" -> tekniikka,                                             "alas" -> klubi,    "vasen" -> kvantti))
  kvantti  .setNeighbors(Vector("ei minnekään" -> kvantti  ,                      "oikea" -> tekniikka))
  n3       .setNeighbors(Vector("ei minnekään" -> n3       ,                                                                 "vasen" -> klubi))
  asehuone .setNeighbors(Vector("ei minnekään" -> asehuone , "ylös" -> klubi,                            "alas" -> n2))
  n2       .setNeighbors(Vector("ei minnekään" -> n2       , "ylös" -> asehuone,  "oikea" -> vault,                          "vasen" -> lab))
  vault    .setNeighbors(Vector("ei minnekään" -> vault    ,                                                                 "vasen" -> n2))


  piha.addItem(Item("omena", "omena, äbbyl."))
  vault.addItem(Item("kultaharkko", "painaa paljon, melkeen yhtä paljon ku mä mutsiis."))
  tekniikka.addItem(Item("skanneri", "Skanneri kertoo, onko zombi jossain viereisistä huoneista."))
  asehuone.addItem(Item("ase", "tekee ase asioita"))
  kvantti.addItem(Item("arduino","                                      .-----.                     \n" +
                                 "         .----[PWR]-------------------| USB |--.                  \n" +
                                 "         |                            '-----'  |                  \n" +
                                 "         |         GND/RST2  [ ][ ]            |                  \n" +
                                 "         |       MOSI2/SCK2  [ ][ ]  A5/SCL[ ] |                  \n" +
                                 "         |          5V/MISO2 [ ][ ]  A4/SDA[ ] |                  \n" +
                                 "         |                             AREF[ ] |                  \n" +
                                 "         |                             AREF[ ] |                  \n" +
                                 "         |                              GND[ ] |                  \n" +
                                 "         | [ ]NC                     SCK/13[ ] |                  \n" +
                                 "         | [ ]v.ref                 MISO/12[ ] |                  \n" +
                                 "         | [ ]RST                   MOSI/11[ ]~|                  \n" +
                                 "         | [ ]3V3    +---+               10[ ]~|                  \n" +
                                 "         | [ ]5v     | A |                9[ ]~|                  \n" +
                                 "         | [ ]GND   -| R |-               8[ ] |                  \n" +
                                 "         | [ ]GND   -| D |-                    |                  \n" +
                                 "         | [ ]Vin   -| U |-               7[ ] |                  \n" +
                                 "         |          -| I |-               6[ ]~|                  \n" +
                                 "         | [ ]A0    -| N |-               5[ ]~|                  \n" +
                                 "         | [ ]A1    -| O |-               4[ ] |                  \n" +
                                 "         | [ ]A2     +---+           INT1/3[ ]~|                  \n" +
                                 "         | [ ]A3                     INT0/2[ ] |                  \n" +
                                 "         | [ ]A4/SDA  RST SCK MISO     TX>1[ ] |                  \n" +
                                 "         | [ ]A5/SCL  [ ] [ ] [ ]      RX<0[ ] |                  \n" +
                                 "         |            [ ] [ ] [ ]              |                  \n" +
                                 "         '--.                         .--------'                  \n" +
                                 "             \\_______________________/                              " ))

  kvantti.addItem(Item("oskilloskooppi", "Tämän pinta on omituisen tahmea..."))
  aula.addItem(Item("kartta", "kertoo missä paikat ovat"))
  lab.addItem(Item("lääkkeen resepti", "Valmistaaksesi zombinparannuslääkkeen, tarvitset omenan, kultaharkon ja weakness potionin."))

  klubi.addNpc(NPC("Teemu Teekkari", Buffer[String]("Vedä viinaa!")))
  val zombi = Zombi(n3)
  val player = Player(bunkkeri, zombi)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 200


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = (this.player.location == this.destination) && hasNeededItems

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit || this.player.onKuollut

  
  private def hasNeededItems: Boolean = (this.player.inventory.contains("omena") && this.player.inventory.contains("weakness potion") && this.player.inventory.contains("kultaharkko"))

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "Heräät erään tiedemiehen pelottavan kartanon harmaasta bunkkerista.\nKarmivassa kartanossa tiedemies on tutkinut erilaisten virusten \nvaikutusta ihmiskehoon ja vahingossa onnistunut luomaan zombiviruksen!\nTuo virus on valitettavasti tarttunut johonkuhun kartanossa, ja viruksen\nleviäminen tulee estää kaikilla mahdollisilla keinoilla, tai ihmiskunta tulee\ntuhoutumaan.\n\nTavoitteenasi on siis liikkua kartanon sisällä, valmistaa lääkeaine zombivirusta\nvastaan ja pelastaa ihmiskunta."


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage =
    if this.player.inventory.contains("arduino") && this.isComplete then
       println("Sait lääkkeen valmistettua ja maailma pelastui. Hurraa! Pääset nyt leikkimään arduinolla")
       arduinopeli()
    else if this.isComplete then
      "Sait lääkkeen valmistettua ja maailma pelastui. Hurraa! Kuitenkin olet pettynyt, ettet löytänytkään Arduinoa"
    else if this.turnCount == this.timeLimit then
      "Käytit liian monta vuoroa.\n Game over!"
    else if this.player.hasQuit then // game over due to player quitting
      "Luovuttaja!"
    else
      "Kuolit tappelussa zombin kanssa."

  def arduinopeli() : String =
    var komento = readLine("mikä komennolla määritetään digitaalinen pin 12 annoksi?")
    komento match
      case "pinMode(12, OUTPUT);" => "oikein! hyvä :)"
      case "pinMode(12,OUTPUT);" => "oikein! hyvä :)"
      case _ => "väärin" + arduinopeli()

  /** Plays a turn by executing the given in-game Yo, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player, this.zombi)
    if outcomeReport.isDefined then
      this.turnCount += 1
    outcomeReport.getOrElse(s"""Tuntematon komento: "$command".""")


end Adventure