# Iznajmljivanje električnih vozila
Projekt izrađen u sklopu kolegija: Uzorci dizajna

## Opis projekta

Izrada sustava za iznajmljivanje električnih vozila u obliku konzolne aplikacije. U aplikaciji je potrebno koristiti uzorke dizajna koji su opisani u GoF (Gang of Four) knjizi. Dopušteno je koristiti razne uzorke, međutim, cilj je na temelju opisa projekta identificirati i implementirati "najbolje" uzorke za opisane djelove projekta. Odabir programskog jezika koji će biti korišten za izradu aplikacije prepušten je studentima (Java ili C#). Projekt ima tri faze, a u svakoj fazi se aplikacija  nadograđuje s raznim funkcionalnostima i uzorcima. Kod predaje faze projekta potrebno je priložiti dijagram klasa koji prikazuje korištene uzorke dizajna i tablicu koja sadrži listu korištenih uzoraka. Za svaki uzorak u tablici potrebno je opisati zašto je baš taj uzorak korišten i u kojem dijelu aplikacije je implementiran.

### Prva faza
U ovoj fazi bilo je potrebno postaviti temelj aplikacije (učitavanje i sadržaja datoteka, definiranje potrebnih klasa, implementacija i provjera ispravnosti naredba koje se mogu izvršavati u aplikaciji i sl.). U opisu projekta je definirano da tvrtka iznajmljuje različite vrste vozila (električne bicikle, romobile, motore, automobile i sl.), svaka vrsta vozila ima određeni broj primjeraka što čini vozni park u određenoj lokaciji. Svako vozilo ima sljedeće osobine: vrijeme punjenja prazne baterije (0%-100%), domet. Tvrtka ima više poslovnica (lokacija) na kojima korisnici/klijenti mogu unajmiti ili vratiti vozilo. Svaka lokacija ima inicijalni broj raspoloživih primjeraka vozila za pojedinu vrstu vozila (definirano u konfiguracijskoj datoteci). Broj raspoloživih primjeraka ne može biti veći od broja mjesta za punjenje te vrste vozila na lokaciji. Korisnik može imati samo jedan aktivni najam, odnosno dok korisnik ne vrati prvo vozilo ne može unajmiti drugo vozilo. Vozilo se može vratiti na bilo koju lokaciju ukoliko na toj lokaciji postoji slobodno mjesto za tu vrstu vozila.

Kod vraćanja vozila s aktivnog najma se preuzima ukupan broj prijeđenih kilometara za određeno vozilo te se izračunava potrošnja baterije na bazi prijeđenog broja kilometara i dometa vrste vozila (u km). Nakon vraćanja vozila potrebno mu je napuniti bateriju. Punjenje baterije provodi se linearno tako da se potrebno vrijeme punjenja baterije vozila izračunava na temelju trenutnog stanja baterije (u %) i vremena punjenja prazne baterije. Vozilo nije moguće unajmiti ako mu baterija nije u potpunosti puna.

Za svaku vrstu vozila postoji cjenik koji se temelji na najmu, broju sati najma i broju prijeđenih kilometara. Kod vraćanja vozila izračunava se trošak najma (najam, broj započetih sati najma, broj prijeđenih kilometara).

Svaka aktivnost (najam, vraćanje, provjera) polazi od virtualnog vremena. Virtualno vrijeme jedne aktivnosti mora biti veće od virtualnog vremena prethodne aktivnosti. Virtualno vrijeme ažurira se nakon izvođenja aktivnosti.

Konfiguracijske datoteke:
1. Vozila - popis vrsta vozila i njihove osobine
2. Lokacije - popis lokacija i njihove osobine
3. Lokacije_Kapaciteti - broj mjesta za pojedine vrste vozila i broj raspoloživih vrsta vozila po lokaciji
4. Cjenik - iznos najma, iznos po satu najma i po prijeđenom km po pojedinoj vrsti vozila
5. Osobe - popis osoba s njihovim osobinama
6. Aktivnosti - aktivnosti za skupni način rada. Aktivnosti koje su zapisane u datoteci se mogu koristiti i kod interaktivnog načina rada (manualnim upisom aktivnosti).

Priložene datoteke su jedan primjer sa svojim sadržajem. Testiranje se može provoditi i na drugim datotekama sa drugačijim sadržajima.

Potrebno je provjeriti ispravnost podataka u datotekama i svaki zapis koji nije ispravan se preskače uz ispis informacije o tome.

Aplikacija ima dva načina rada:
- Interaktivni način rada
- Skupni način rada

Interaktivni način rada podrazumijeva manualno upisivanje naredba od strane korisnika u aplikaciju, dok se u skupnom načinu rada aktivnosti koje želimo izvršiti učitavaju iz datoteke.

Pri pokretanju aplikacije potrebno je inicijalizirati sustav iznajmljivanja električnih vozila tako da se učitaju datoteke: lokacija, vozila, kapaciteta lokacija, cjenika i osoba. Ako pojedini redak u datoteci nije ispravan potrebno ga je ispisati i opisati zašto je neispravan.

Inicijalno sva vozila imaju punu bateriju i ukupan broj prijeđenih kilometara svakog vozila je 0. Slijedi inicijalizacija virtualnog vremena na temelju opcije početnog vremena (parametra -t). Osnovni način izvršavanja aplikacije je u interaktivnom načinu, dok se skupni način provodi upisom parametra -s. Kod interaktivnog načina korisnik upisuje pojedinu aktivnost nakon čega mu se prikazuje rezultat te iste aktivnosti. Zatim može izvršiti sljedeću aktivnost. Kod skupnog načina aktivnosti se izvršavaju slijedno tako da se ispiše aktivnost, a zatim se prikaže rezultat pozvane aktivnosti. Kada se izvrše sve aktivnosti program završava s radom.

Program se može izvršavati sa svojeg izvornog direktorija, ali je također potrebno osigurati da će se pri izvršavanja također moći pozvati sa drugih lokacija. Pokretanje treba funkcionirati upisom relativne adrese/putanje ili upisom apsolutne putanje.

Detaljni opis aktivnosti/naredba i načina za pokretanje programa dostupan je ovdje.

Tablica uzoraka:
<p align="center">
  <img width="800" height="500" src="https://user-images.githubusercontent.com/45578967/134831258-ceae0f67-6d45-4f6a-a317-7872cb00b050.png">
</p>

Dijagram klasa:
<p align="center">
  <img width="1000" height="500" src="https://user-images.githubusercontent.com/45578967/134831218-56ec7094-1b9c-4830-bba1-46ba42a6c0e6.png">
</p>

### Druga faza
U ovoj fazi bilo je potrebno promijeniti ili dodati poslovna pravila aplikacije, te nadodati aktivnosti koje se mogu izvršavati u programu.

Svakom vozilu je potrebno dodijeliti jednoznačni cjelobrojni identifikator. Tvrtka je provlea reorganiziranje svog poslovanja. Uvedena je nova organizacijska struktura koja se temelji na organizacijskim jedinicama pri čemu postoji jedna koja je ishodišna (nema nadređenu) i ona predstavlja tvrtku. Jedna organizacijska jedinica može sadržavati druge organizacijske jedinice kao i lokacije. Jedna organizacijska jedinica može biti samo u jednoj organizacijskoj jedinici. Svaka lokacija (osim ishodišne) mora biti sastavni dio neke organizacijske jedinice. Jedna lokacija može biti samo u jednoj organizacijskoj jedinici. Svaka lokacija mora biti sastavni dio neke organizacijske jedinice. Svi podaci koji su označeni u datotekama kao (id organizacijske jedinice, lokacije, vozila, itd.) su cijeli brojevi.

Kod unajmljivanja vozila potrebno je odabrati raspoloživo vozilo koje je dosad imalo najmanji broj najmova. U slučaju istog broja najma kod više vozila bira se ono koje ima manji ukupan broj prijeđenih kilometara. U slučaju istog broja prijeđenih km konačan izbor će bit vozilo sa manjim id-om. U ovoj fazi bilo je potrebno omogućiti najam različitih vozila (za svaku vrstu vozila smije imati jedan aktivni najam). Dok korisnik ne vrati vozilo određene vrste ne smije unajmiti drugo vozilo te iste vrste. Vozilo koje je vraćeno u neispravnom stanju ne može se dalje iznajmljivati. Korisniku se bilježi broj vraćanja vozila u neispravnom stanju.

Korisniku se za najam vozila izdaje račun, a on predstavlja zaradu za lokaciju u kojoj je vozilo unajmljeno. Račune izdaje središnji sustav tako da svakom računu dodjeli redni broj. Kod računa ne smije se koristiti objektna veza prema lokaciji (ili organizacijskoj jedinici), tako da se bilježi samo id lokacije najma i vraćanja. Kod lokacije (ili organizacijskoj jedinici) ne smije se koristiti objekta veza prema računima koji se odnose na lokaciju najma ili vraćanja vozila.

Nadodane konfiguracijske datoteke:
- Konfiguracija_1 - podaci za interaktivni rad programa
- Konfiguracija_2 - podaci za skupni rad programa
- Cjenik - iznos najma, iznos po satu najma i po prijeđenom km po pojedinoj vrsti vozila
- Tvrtka - organizacijska struktura tvrtke

U ovoj fazi potrebno je omogućiti izmjenjivanje načina rada (iz interaktivnog u skupni i obratno). Ako u datoteci za skupni rad ne postoji aktivnost za kraj rada tada program prelazi u interaktivni način rada. Nadodane su nove aktivnosti koje su definirane na sljedećoj poveznici.

Detaljni opis aktivnosti/naredba i načina za pokretanje programa dostupan je ovdje.

Kod ispisa strukture poduzeća prvo se ispisuju podaci organizacijske jedinice najviše razine (tvrtka), odnosno ishodišne organizacijske jedinice. Nakon toga slijedi prijelaz u nižu razinu. Uzima se prvi sastavni dio (dijete) te organizacijske jedinice i ispisuju se njegovi podaci, itd. Kada završi ispis svih dijelova pojedine organizacijske jedinice potrebno je ispisati kumulativne podatke njenih dijelova. Zatim se vraća na prethodnu razinu i nastavlja s ispisom ostalih dijelova (braće) itd.

Kada se podaci ispisuju u obliku tablice tada se za stupce s tekstualnim podacima primjenjuje lijevo poravnanje, a za stupce s brojčanim podacima desno poravnanje. Broj znakova za formatiranje tekstualnih podataka određen je opcijom -dt, broj znakova za formatiranje cijelih brojeva (i cijelog dijela realnih brojeva) određen je opcijom -dc, a broj decimalnih mjesta za formatiranje realnih brojeva određen je opcijom -dd. Ako opcije nisu upisane tada se pretpostavlja da je -dt 30, -dc 5, -dd 2.

Potrebno je omogućiti izvršavanje:
- Putem opcija i naziva datoteka
- Putem konfiguracijske datoteke

U datoteci konfiguracije sustava (Konfiguracija_1 i Konfiguracija_2) postoje zapisi koji se tretiraju kao oblik ključ=vrijednost. Ključevi su ekvivalentni opcijama, a vrijednosti su nazivi datoteka ili brojevi za formatiranje ispisa podataka. Npr. za opciju -v postoji ključ vozila, a za opciju -l postoji ključ lokacije, za opciju -c postoji ključ cjenik, za opciju -k postoji ključ kapaciteti, za opciju -o postoji ključ osobe, za opciju -t postoji ključ vrijeme, za opciju -s postoji ključ aktivnosti, za opciju -os postoji ključ struktura, za opciju -dt postoji ključ tekst, za opciju -dc postoji ključ cijeli, za opciju -dd postoji ključ decimala. Za nazive datoteka koje se koriste unutar konfiguracijske datoteke vrijede ista pravila kao i kada se koriste opcije i nazivi datoteka (relativni ili apsolutni naziv).

Tablica uzoraka:
<p align="center">
  <img width="800" height="800" src="https://user-images.githubusercontent.com/45578967/134832445-7b992bee-6803-479b-bacd-68d923627282.png">
</p>

Dijagram klasa:
<p align="center">
  <img width="1000" height="600" src="https://user-images.githubusercontent.com/45578967/134832527-e0f7f755-8011-41fb-907d-5773d87def0d.png">
</p>

### Treća faza
U ovoj fazi je bilo navedeno da se kod zapisivanja organizacijske strukture tvrtke treba koristiti uzorak dizajna Composite. Za prolaz po elementima organizacijske strukture potrebno je koristiti vlastiti Iterator. Vozila moraju imati implementiran uzorak dizajna State kako bi se upravljalo stanjima vozila (slobodno, unajmljeno, na punjenju, neispravno). Obavljanje pojedinih aktivnosti s vozilom (na raspolaganju, najam...) treba biti preneseno s vozila na konkretna stanja čime se eliminiraju provjere na samom vozilu.

Tvrtka je uvela novost u svom poslovnom modelu koja se tiče osoba, tj. korisnika prema kojem oni koji žele mogu sklopiti ugovor s tvrtkom kako ne bi odmah morali plaćati račun nakon vraćanja vozila. Korisnik može pratiti svoje neplaćene račune kao i plaćene račune. Ako je ukupan iznos neplaćenih računa prekoračio dopušteni iznos dugovanja (ključ dugovanje u datoteci konfiguracije, opisano detaljnije u nastavku) tada više ne može unajmiti vozilo dok ne podmiri cijelo dugovanje ili dio dugovanja tako da je preostalo manje od dopuštenog iznosa. Inicijalno svi korisnici imaju stanje 0.00 kn tj. nemaju dugovanje.

Računi se evidentiraju, obrađuju i pretražuju pomoću uzorka dizajna Chain-of-Responsibility.

Aplikacija se u ovoj fazi izvodi u skupnom načinu rada ukoliko je u datoteci konfiguracije upisan ključ aktivnosti, inače se izvodi u interaktivnom. Ako je upisan ključ izlaz i ima pridružen naziv datoteke tada cjelokupni izlaz umjesto na ekran korisnika preusmjerava se u datoteku definiranog naziva. To vrijedi samo kada se koristi skupni način rada.


Nadodane konfiguracijske datoteke:
- Konfiguracija_3 - podaci za interaktivni rad programa
- Konfiguracija_2 - podaci za skupni rad programa

Postojeće rješenje je potrebno refaktorirati na način da bi se koristio uzorak MVC. Zadani elementi definiraju postojanje 3 pogleda, od kojih je jedan za interaktivni način rada, drugi je za skupni način rada s prikazom na ekran korisnika, a treći je za skupni način rada kada se podaci preusmjeravaju u datoteku. Svi ispisi moraju biti obavljeni pomoću jednog od 3 opisana pogleda. Kako se može prelaziti iz skupnog u interaktivni, tako je potrebno omogućiti i obratno, što znači da se mora promijeniti pogled. Korisnik programa mora vidjeti na ekranu u kojem pogledu se trenutno nalazi kod prijelaza.

Podaci se učitavaju iz datoteka (ne za aktivnosti) i provjerava se njihova ispravnog. Ako se ustanovi da se radi o neispranvom podatku potrebno je ispisati izvorni redak podatka i do njega razlog zašto je neispravan. Ako je aktivan jedan od pogleda koji se prikazuje na ekranu, tada se takav redak prikazuje u crvenoj boji. A ako je aktivan pogled s upisom u datoteku tada se takav redak prikazuje, a nakon njega se u sljedećem retku prikazuje znak x u istoj dužini kao i prethodni redak.

Učitavanje i obrada aktivnosti provodi se na način da se prvo ispiše izvorni redak aktivnosti, a u sljedećem retku se ispisuje komentar o aktivnosti, njenom izvršavanju ili ispis podatka koji sadrži aktivnost.

Napomena: kako bi se u CMD-u tekst prikazivao u crvenoj boji potrebno je nadodati registry zapis.

<p align="center">
  <img width="500" height="350" src="https://user-images.githubusercontent.com/45578967/135177561-93102055-bc21-49e1-9a04-dce955a77d08.png">
</p>

Tablica uzoraka:
<p align="center">
  <img width="800" height="1000" src="https://user-images.githubusercontent.com/45578967/134832767-2df7e317-e763-47de-a679-b9ab6de16f43.png">
</p>

Dijagram klasa:
<p align="center">
  <img width="1000" height="600" src="https://user-images.githubusercontent.com/45578967/134832831-56ecee8a-e9ba-4aee-80f4-f0ed66cbb26b.png">
</p>
