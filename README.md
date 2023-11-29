# tekstiseikkailu

# Ohjeet #

Tervetuloa pelaamaan Mörtin zombipeliä!

Pelissä hahmosi herää erään tiedemiehen pelottavan kartanon bunkkerista.
Karmivassa kartanossa tiedemies on tutkinut erilaisten virusten 
vaikutusta ihmiskehoon ja vahingossa onnistunut luomaan zombiviruksen!
Tuo virus on valitettavasti tarttunut johonkuhun kartanossa, ja viruksen
leviäminen tulee estää kaikilla mahdollisilla keinoilla, tai ihmiskunta tulee
tuhoutumaan.

Tavoitteenasi on siis liikkua kartanon sisällä, valmistaa lääkeaine zombivirusta
vastaan ja pelastaa ihmiskunta.

# Yleisiä neuvoja #

Peliä pelataan kirjoittamalla komentoja suomeksi. Listan kaikista komennoista saa
pelin aikana näkyviin kirjoittamalla komennon "help" tai tästä ohjeesta.
- mene (suunta) - Liiku tässä suunnassa olevaan huoneeseen, jos sinne on mahdollista mennä. 
  Jos haluat pysyä samassa ruudussa, kirjoita suunnaksi "ei minnekään".
- poimi (esine) - Ota huoneesta löytyvä esine.
- tiputa (esine) - Tiputa sinulla oleva esine.
- tavaraluettelo - Listaus sinulla olevista esineistä.
- kartta - Tulosta kartanon kartta, jos kartta on tavaraluettelossasi.
- syö - Syö omena, jos omena on tavaraluettelossasi.
- juo - Juo vettä, jos olet pihalla. Vettä täytyy juoda, jos häviää juomapelin.
- pelaa - Jos olet Klubi-huoneessa, voit aloittaa juomapelin tällä komennolla.
- skanneri - Käytä skanneria, jos skanneri on tavaraluettelossasi. Skanneri tarkistaa kaikki 
  viereiset huoneet, ja kertoo, tuleeko zombi olemaan liikkumaan joihinkin niistä.
- shotti - Jos juomapeli on aloitettu, juo shotti tällä komennolla.
- juttele (NPC) - Jos tapaat NPC:n, voit jutella hänelle tällä komennolla.
- kräftää - (Craft) Valmistaa lääkkeen.

Peli suositellaan pelaamaan Text-UI:lla, jotta ASCII-taide näyttää oikealta.

# Tarkat ohjeet läpäisyyn, älä lue jos haluat autenttisen pelikokemuksen #

Pelin läpäisemiseksi seuraa seuraavia vaiheita:

- Pelin lääke valmistetaan kolmesta esineestä, weakness potionista, kultaharkosta ja
  omenasta, kuten on kerrottu Laboratiorio-huoneessa olevassa "lääkkeen resepti"-esineessä.
  Resepti-esinettä ei tarvitse kerätä, jotta pelin voi voittaa.
- Pelissä tulee liikkua huoneiden välillä ja samalla varoa, ettet kulje samaan huoneeseen zombin
  kanssa. Jos saavut samaan huoneeseen, seuraa tappelu ja todennäköisyytesi kuolla on 50%. Peli päättyy kuolemiseen.
  Jos selviät, siirryt huoneesta, jossa tappelu tapahtui, johonkin satunnaisesti valittuun suuntaan.
- Asehuoneessa on mahdollista poimia ase. Jos joudut tappeluun niin, että ase on tavaraluettelossasi, 
  todennäköisyytesi kuolla on vain 10%.
- Tekniikkahuoneessa voit poimia itsellesi skannerin, joka kertoo, jos zombi tulee olemaan jossakin viereisistä
  huoneista seuraavalla kierroksella.
- Omenan voi hakea pihalta poimimalla. Älä syö omenaa, se poistaa omenan pelistä, eikä sitä
  ole mahdollista saada takaisin.
- Weakness potionin saa klubista pelaamalla juomapeliä. Peli aloitetaan olemalla klubi-huoneessa
  "pelaa"-komennolla. Yhden kierroksen juomapeliä voi pelata komennolla "shotti". Jokaisella kierroksella
  todennäköisyytesi sammua kasvaa, ja jos sammut ennen kuin olet juonut tarpeeksi monta shottia, sinun tulee
  mennä pihalle juomaan vettä. Kun olet juonut vettä, voit aloittaa juomapelin uudelleen "pelaa"-komennolla.
  Peli on toki mahdollista aloittaa uudestaan juomatta välissä vettä, mutta häviät sen välittömästi.
  Kun juomapelin voittaa, saat tavaraluetteloosi weakness potionin.
- Kultaharkon saa holvista poimimalla. Holviin menemiseen vaaditaan salasana 2396, jonka saa selville myös menemällä
  käytävä-huoneeseen.
- Kun sinulla on kaikki tarvittavat esineet tavaraluettelossasi, mene laboratorioon ja valmista lääke
  (golden apple) komennolla "kräftää". Tämän jälkeen sinun tulee päästä samaan huoneeseen zombin kanssa, jolloin
  voitat pelin.
- Pelissä on myös eräs salainen huone, kvantti, jossa käyminen ei ole pelin läpäisemisen kannalta välttämätöntä. 
  Sieltä kuitenkin voi poimia muutamia salaisia esineitä...

# todo
- mappi (X)
- itemit (x)
  - omena (x)
    - mahdollista syödä(x)
  - weakness potion (x)
  - kulta (x)
  - skanneri (x)
    - komenon rajoittaminen vain silloin kun inventaariossa on skanneri(x)
    - skanneri ei ole tällä hetkellä kovin hyödyllinen, koska se kertoo missä zombi on nyt, eikä minne zombi on menossa. Jos siis skannaat huoneen, zombi saattaa silti mennä sinne.
    - zombin liikkumista muutettu niin, että zombilla on ennalta määritelty suunta, johon se liikkuu ja joka arvotaan liikkumisen jälkeen uudestaan.
      - arvotun suunnan pitäisi olla eri kuin suunta mihin zombi menee ( )
      - tappelu myös silloin, jos pelaaja ja zombi kohtaavat "huoneiden välissä" (esim. jos pelaaja menee aulasta pihalle ja zombi pihalta aulaan, tappelu tapahtuu myös silloin.)
  - ase (x)
    - tn kuolla 50% ilman asetta, aseen kanssa 10%
  - arduino (x)
  - map-item (x)
  - recipe (x)
    - ei tee mitään mutta on ohje pelaajalle
- zombi ( )
  - zombi liikkuu (X)
  - zombin kanssa tappeleminen (X)
    - muutettu niin, että battle tulee vain, jos pelaaja olisi menossa samaan ruutuun mihin zombi on menossa. Jos battle epäonnistuu, pelaajan "kuollut" muuttujasta tulee "true" ja peli päättyy.
    - arpoo luvun 0-99, jos 49 tai alle niin pelaaja kuolee. Muuten pelaaja siirtyy huoneesta random suuntaan.
  - player pystyy tarkistamaan, onko hän samassa huoneessa zombin kanssa, mutta mitään ei tapahdu(x)
  - pitää estää zombin pääsy kvanttiin(x) luultavasti, mutta en sano 100%
- shottikisa-minipeli (x)
  - NPC(x)
  - aloitettu. Jos on klubissa ja kirjoittaa komennon "pelaa", peli alkaa, mutta komento "juo" puuttuu. Pelissä "Constants" tiedosto, jonne peli arpoo kyseisen pelikerran voittoon vaadittavan kierrosmäärän (4-20)
  - kierrosmäärä on 3-6, koska "juo" komennon spämmääminen ei ole mielekästä gameplayta :D joka kierroksella tn sammua kasvaa satunnaisen määrän 0-20 %-yksikköä. jos häviää, pitää käydä pihalla juomassa vettä ja aloittaa shottikisa uudestaan.
- koodin kirjoitus vaultissa (x)
- golden apple -crafting-minipeli ( )
- voittoehto (kinda)
- komentojen kääntäminen suomeksi (x)
- lisäkomennot? ( )
  - help(x)
  - juo(x) jos häviää shottikisan, pitää käydä juomassa vettä
  - "mene ei minnekään" antaa pelaajan jäädä huoneeseen. zombin pitää aina liikkua uuteen huoneeseen (x)
- tekstien lisääminen ( )
- arduino minipeli( )
  - arduino-peli tulostaa "oikein" tai "väärin" vasta kun pelaaja kirjoittaa vastauksen oikein. esim. jos kirjoittaa kahdesti väärän vastauksen ja sitten oikean, peli tulostaa "väärinväärinoikein!"

# muuta

- Yritin luoda metodeita arealle, jotka pitäisivät kirjaa hahmoista, jotka ovat siellä, mutta se ei toiminut. Siellä nyt edelleen zombiIsHere ja muut. Tätä varten lisäsin "Character"-traitin, johon player ja zombi kuuluvat. Tällä hetkellä se on vähän turha, mutta ei siitä varsinaisesti haittaa ole. Huomaa uudet metodit traitissa.
- Playerilla "enemy" muuttuja, jonka kautta voi tutkia zombin asioita.