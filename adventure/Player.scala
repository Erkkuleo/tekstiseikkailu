package o1.adventure
import scala.collection.mutable
import scala.collection.mutable.*
import scala.io.StdIn.*
import scala.util.Random
import o1.*


/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingArea  the player’s initial location */
class Player(startingArea: Area, enemy: Zombi) extends Character(startingArea):

  private var quitCommandGiven = false              // one-way flag
  private val playerInventory = Map[String, Item]()
  private var juomapeliAloitettu = false
  private var juomapeliLopetettu = false
  private var kierroksiaPelattu = 0
  private var kuollut = false
  private var shotRounds = 0
  private var sammumisenTn = 10

  var hasWon : Boolean = false

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

  def juo() =
    if this.currentLocation.name == "Piha" then
      if sammumisenTn == 100 then
        sammumisenTn = 1
        "Juot vettä, vesi on hyvää. Tunnet, kuinka alkoholin vaikutus välittömästi pienenee."
      else
        "Juot vettä, vesi on hyvää."
    else
      "Et voi juoda täällä."

  def shotti() =
    if this.juomapeliAloitettu && !this.juomapeliLopetettu && (this.currentLocation.name == "Klubi") then
      if this.kierroksiaPelattu < this.shotRounds then
        val r = Random.nextInt(100)
          if r < this.sammumisenTn then
            this.kierroksiaPelattu = 0
            this.sammumisenTn = 100
            this.juomapeliAloitettu = false
            "Yritit juoda shottia, mutta se tulikin saman tien ylös ja hävisit pelin. Nyt kannattaa käydä pihalla juomassa vettä."
          else
              this.kierroksiaPelattu += 1
              this.sammumisenTn += Random.nextInt(21)
              s"Juot shotin. Maistuu ihan hirveältä, mutta lämmittää mukavasti vatsassa."
      else
        val weaknessPotion = Item("weakness potion", "Minecraftista tuttu, kyljessä lukee jotain korvien koskettelusta.")
        juomapeliLopetettu = true
        this.playerInventory += weaknessPotion.name -> weaknessPotion
        "Nostat viimeisen hörpyn huulillesi, joka menee enää vain juuri ja juuri alas. \n" +
          "Maksasi onneksi huomaat, että vastapelaajasi ei kestänyt enää n + 1 juomaa \n" +
          "ja on tuupertunut lattialle. Palkinnoksi suorituksestasi saat loput weakness potionista, jos enää niitä haluat."
    else
      s"Juomapeli täytyy aloittaa \"pelaa\"-komennolla ennen kuin sitä voi pelata. Pelin voi pelata ainoastaan kerran."

  def pelaa() =
    if this.currentLocation.name != "Klubi" then
      "Täällä ei voi pelata mitään."
    else
      if juomapeliLopetettu || juomapeliAloitettu then
        "Olet jo voittanut pelin tai peli on jo aloitettu."
      else
        juomapeliAloitettu = true
        this.shotRounds = Random.between(3, 7)
        "Pelataan shottikisaa!!!\n\nPeli on helppo, joka kierroksella ota shotti! Katsotaan kumpi sammuu ensimmäisenä." +
          "\nPelataksesi juomapeliä kirjoita kometo \"shotti\"."

  def onKuollut = this.kuollut

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
      case None    => s"Täällä ei ole ketään, kenen kanssa jutella."
      case Some(n) => s"${n.name}: ${n.liners}"

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
      "#  BUNKKERI ####  AULA     #### TYÖHUONE  ####  KLUBI    #### MAKUUHUONE#\n" +
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
      "               #LABORATORIO###################  KEITTIÖ  #### HOLVI     #\n" +
      "               #           #                 #           #  #           #\n" +
      "               #############                 #############  #############\n"
    else
      "Sinulla ei ole karttaa"

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String): String =
    val destination = this.currentLocation.neighbor(direction)
    destination match
      case Some(validDestination) =>
        if enemy.nextLocation.isDefined && (validDestination == enemy.nextLocation.head) && playerInventory.contains("golden apple") then
          hasWon = true
          ""
        else if enemy.nextLocation.isDefined && (validDestination == enemy.nextLocation.head) then
          var outcome = "Jouduit samaan huoneeseen zombin kanssa! Kiivaan tappelun seurauksena "
          this.battle
          if !this.onKuollut then
            val sallitutSuunnat = this.currentLocation.validDirections -= "ei minnekään"
            val satunnainenSuunta = sallitutSuunnat(Random.nextInt(sallitutSuunnat.size))
            this.newLocation(validDestination.neighbor(satunnainenSuunta).getOrElse(this.currentLocation))
            enemy.zombiGo()
            outcome +=  "selvisit. Pakenet nopeasti paikalta sattumanvaraiseen huoneeseen."
            outcome
          else
            outcome += "kuolit."
            outcome
        else
          if validDestination.name != "Holvi" then //holvin salaus systeemi
            this.newLocation(validDestination) // tämä toteutetaan jos kyseessä ei ole holvi
            enemy.zombiGo()
            "Menet " + direction + "."
          else
            val input = readLine("\nAnna salasana:\n").toIntOption // jos kyseessä on holvi
            input match
              case Some(number) =>
                if number == 2396 then // vaihda salasana haluamaasi
                  this.newLocation(validDestination) // jos salasana oikein siirrytään huoneeseen
                  enemy.zombiGo()
                  "Salasana oli oikein.\nMenet " + direction + "."
                else
                  enemy.zombiGo()
                  "Salasana oli väärä, etkä päässyt holviin sisään." // muuten pidetään tämä lokaatio
              case None =>
                enemy.zombiGo()
                "Salasana oli väärä, etkä päässyt holviin sisään."
      case None =>
        "Ei ole mahdollista mennä suuntaan " + direction + "."
  def meetsZombie: Boolean = this.currentLocation.zombiIsHere

  def zombiLocation =
    if has("skanneri") then
      var zombisuunta: Option[Area] = None
      val suunnat = this.currentLocation.validDirections.map(n => this.currentLocation.neighbor(n))
      for i <- suunnat do
        if i == enemy.nextLocation then
          zombisuunta = enemy.nextLocation
      if zombisuunta.isDefined then
        s"Zombi siirtyy seuraavalla kierroksella suuntaan ${zombisuunta.head.name}."
      else
        "Zombi ei näkynyt skannerissa."
    else
      "Sinulla ei ole skanneria."

  def battle =
    val r = Random.nextInt(100)
    if this.has("ase") then
      if r < 10 then
        this.kuollut = true
    else
      if r < 50 then
        this.kuollut = true

  def craftGoldenApple =
    if this.inventory.contains("omena") && this.inventory.contains("kultaharkko") && this.inventory.contains("weakness potion") && this.location.name == "Laboratorio" then
      this.playerInventory += "golden apple" -> Item("golden apple", "Kultainen omena, jolla voi parantaa zombin.\nParantaaksesi zombin, mene samaan huoneeseen zombin kanssa.")
      this.playerInventory.remove("omena")
      this.playerInventory.remove("weakness potion")
      this.playerInventory.remove("kultaharkko")
      "Sait valmistettua golden applen! Tällä voit parantaa zombin."
    else if this.location.name != "Laboratorio" then
      "Mene laboratorioon, jotta voit valmistaa asioita."
    else
      "Sinulla ei ole tarvittavia tarvikkeita."

  def help : String =
    "Tässä kaikki komennot:\n" +
      s"${Console.GREEN} help ${Console.RESET}- Tulostaa tämän tekstin.\n" +
      s"${Console.GREEN} mene [suunta] ${Console.RESET}- Liiku tässä suunnassa olevaan huoneeseen.\n" +
      s"${Console.GREEN} poimi [esine] ${Console.RESET}- Poimi huoneesta löytyvä esine.\n" +
      s"${Console.GREEN} tiputa [esine] ${Console.RESET}- Tiputa tavaraluettelossasi ollut esine.\n" +
      s"${Console.GREEN} lopeta ${Console.RESET}- Lopeta peli.\n" +
      s"${Console.GREEN} tutkti [esine] ${Console.RESET}- Tutki esinettä. Esineet sisältävät paljon hyödyllistä tietoa.\n" +
      s"${Console.GREEN} tavaraluettelo ${Console.RESET}- Listaa tavaraluettelostasi löytyvät esineet.\n" +
      s"${Console.GREEN} kartta ${Console.RESET}- Tulostaa alueen kartan mikäli sinulla on sellainen.\n" +
      s"${Console.GREEN} syö ${Console.RESET}- Voit syödä omenan. Syö omena :) SYÖ OMENA!  \n" +
      s"${Console.GREEN} juttele [NPC] ${Console.RESET}- Juttele NPC:n kanssa. \n" +
      s"${Console.GREEN} pelaa ${Console.RESET}- Aloita juomapelin pelaaminen tällä komennolla. Toimii ainoastaan Klubissa. \n" +
      s"${Console.GREEN} skanneri ${Console.RESET}- Käytä skanneria, jos sinulla on skanneri. \n" +
      s"${Console.GREEN} shotti ${Console.RESET}- Kun olet Klubissa ja juomapeli on aloitettu, voit pelata kierroksen juomapeliä tällä komennolla. \n" +
      s"${Console.GREEN} juo ${Console.RESET}- Jos häviät juomapelin, täytyy käydä pihalla juomassa välivesi ennen kuin sinulla on mahdollisuus voittaa peli. \n" +
      s"${Console.GREEN} kräftää ${Console.RESET}- Valmista asioita, kun sinulla on tarvittavat esineet. \n"

  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""

  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Nyt olet paikassa: " + this.location.name

end Player
