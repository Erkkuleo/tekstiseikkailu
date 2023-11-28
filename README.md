# tekstiseikkailu
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
  - recipe ( )
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
- poistettu "Constants" tiedosto koska se ei toiminut kunnolla xd
