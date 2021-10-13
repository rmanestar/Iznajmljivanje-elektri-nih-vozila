/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmanestar_zadaca_3;

import java.text.DecimalFormat;
import java.text.ParseException;
import rmanestar_zadaca_3.Singleton.PostavkeSingleton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import rmanestar_zadaca_3.Builder.StrukturaBuilder;
import rmanestar_zadaca_3.Builder.StrukturaBuilderImplementacija;
import rmanestar_zadaca_3.Composite.Struktura;
import rmanestar_zadaca_3.Iterator.Iterator;
import rmanestar_zadaca_3.Iterator.StrukturaRepozitorij;

/**
 *
 * @author Mane
 */
public class Aktivnost {

    private int id;
    private Date vrijeme;
    private int korisnik;
    private int lokacija;
    private int vrstaVozila;
    private int brojKM;
    private String opis;

    private String parametar1;
    private String parametar2;
    private String parametar3;
    private int idOrgJedinice = -1;
    private Date pocetniDatum;
    private Date krajnjiDatum;
    private float iznos;

    public Aktivnost(int id) {
        this.id = id;
    }

    public Aktivnost(int id, int korisnikId, float iznos) {
        this.id = id;
        this.korisnik = korisnikId;
        this.iznos = iznos;
    }

    public Aktivnost(int id, int korisnik, Date pocetni, Date krajnji) {
        this.id = id;
        this.korisnik = korisnik;
        this.pocetniDatum = pocetni;
        this.krajnjiDatum = krajnji;
    }

    public Aktivnost(int id, Date vrijeme) {
        this.id = id;
        this.vrijeme = vrijeme;
    }

    public Aktivnost(int id, String parametar1) {
        this.id = id;
        this.parametar1 = parametar1;
    }

    public Aktivnost(int id, String parametar1, String parametar2, int idOJ) {
        this.id = id;
        this.parametar1 = parametar1;
        this.parametar2 = parametar2;
        this.idOrgJedinice = idOJ;
    }

    public Aktivnost(int id, String parametar1, String parametar2, String parametar3) {
        this.id = id;
        this.parametar1 = parametar1;
        this.parametar2 = parametar2;
        this.parametar3 = parametar3;
    }

    public Aktivnost(int id, String parametar1, Date pocetniDatum, Date krajnjiDatum) {
        this.id = id;
        this.parametar1 = parametar1;
        this.pocetniDatum = pocetniDatum;
        this.krajnjiDatum = krajnjiDatum;
    }

    public Aktivnost(int id, String parametar1, String parametar2, Date pocetniDatum, Date krajnjiDatum, int idOrgJedinice) {
        this.id = id;
        this.parametar1 = parametar1;
        this.parametar2 = parametar2;
        this.pocetniDatum = pocetniDatum;
        this.krajnjiDatum = krajnjiDatum;
        this.idOrgJedinice = idOrgJedinice;
    }

    public Aktivnost(int id, String parametar1, String parametar2, String parametar3, Date pocetniDatum, Date krajnjiDatum, int idOrgJedinice) {
        this.id = id;
        this.parametar1 = parametar1;
        this.parametar2 = parametar2;
        this.parametar3 = parametar3;
        this.pocetniDatum = pocetniDatum;
        this.krajnjiDatum = krajnjiDatum;
        this.idOrgJedinice = idOrgJedinice;
    }

    public Aktivnost(int id, Date vrijeme, int korisnik, int lokacija, int vrstaVozila, int brojKM) {
        this.id = id;
        this.vrijeme = vrijeme;
        this.korisnik = korisnik;
        this.lokacija = lokacija;
        this.vrstaVozila = vrstaVozila;
        this.brojKM = brojKM;
    }

    public Aktivnost(int id, Date vrijeme, int korisnik, int lokacija, int vrstaVozila, int brojKM, String opis) {
        this.id = id;
        this.vrijeme = vrijeme;
        this.korisnik = korisnik;
        this.lokacija = lokacija;
        this.vrstaVozila = vrstaVozila;
        this.brojKM = brojKM;
        this.opis = opis;
    }

    public Aktivnost(int id, Date vrijeme, int korisnik, int lokacija, int vrstaVozila) {
        this.id = id;
        this.vrijeme = vrijeme;
        this.korisnik = korisnik;
        this.lokacija = lokacija;
        this.vrstaVozila = vrstaVozila;
    }

    public static void dohvatiBrojVozilaVrsteNaLokaciji(Osoba osoba, String vrijeme, Lokacija lokacijaSifra, ElektricnoVozilo vrstaVozila) {
        if (osoba == null || vrijeme == null || lokacijaSifra == null || vrstaVozila == null) {
            System.out.println("Ne postoje raspoloziva vozila na lokaciji na temelju danih parametara\n");
            return;
        }

        if (osoba.isAktivniNajam()) {
            System.out.println("Obavijest: Osoba ima aktivni najam i ne moze iznajmiti drugo vozilo\n");
        }
        Date novoVirtualnoVrijeme = null;
        try {
            novoVirtualnoVrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vrijeme);
        } catch (Exception e) {
            return;
        }

        if (novoVirtualnoVrijeme.compareTo(PostavkeSingleton.virtualnoVrijeme) <= 0) {
            System.out.println("Akcija ne smije imati vrijeme izvrsavanja koje se odvija prije trenutnog virtualnog vremena\n");
            return;
        } else {
            PostavkeSingleton.virtualnoVrijeme = novoVirtualnoVrijeme;
        }

        System.out.println("U \u201e" + vrijeme + "\u201c korisnik " + osoba.getImePrezime() + " trazi na lokaciji " + lokacijaSifra.getNazivLokacije()
                + " broj raspolozivih vozila vrste: " + vrstaVozila.getNaziv());

        int trenutnoRaspolozivo = 0;
        for (ElektricnoVozilo ev : KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(), vrstaVozila.getId())) {
            if ("SLOBODNO".equals(ev.getContext().getState().toString())) {
                trenutnoRaspolozivo++;
            }
        }

        System.out.println("Broj raspolozivih vozila na lokaciji: " + trenutnoRaspolozivo + "\n");

        return;
    }

    public static void brojRaspolozivihMjestaVrsteVozilaNaLokaciji(Osoba osoba, String vrijeme, Lokacija lokacijaSifra, ElektricnoVozilo vrstaVozila) {

        if (osoba == null || vrijeme == null || lokacijaSifra == null || vrstaVozila == null) {
            System.out.println("Ne postoje raspoloziva vozila na lokaciji na temelju danih parametara\n");
            return;
        }

        Date novoVirtualnoVrijeme = null;
        try {
            novoVirtualnoVrijeme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vrijeme);
        } catch (Exception e) {
            return;
        }

        if (novoVirtualnoVrijeme.compareTo(PostavkeSingleton.virtualnoVrijeme) <= 0) {
            System.out.println("Akcija ne smije imati vrijeme izvrsavanja koje se odvija prije trenutnog virtualnog vremena\n");
            return;
        } else {
            PostavkeSingleton.virtualnoVrijeme = novoVirtualnoVrijeme;
        }

        KapacitetLokacije kl = KapacitetLokacije.dohvatiKapacitetVrsteVozilaZaLokaciju(lokacijaSifra.getId(), vrstaVozila.getId());

        System.out.println("U \u201e" + vrijeme + "\u201c korisnik " + osoba.getImePrezime() + " trazi na lokaciji " + lokacijaSifra.getNazivLokacije() + " "
                + "broj raspolozivih mjesta za vrstu vozila " + vrstaVozila.getNaziv());
        System.out.println("Ukupan broj mjesta za punjenje za vrstu: " + kl.getMjestaZaPunjenje() + "\nBroj slobodnih mjesta: "
                + (kl.getMjestaZaPunjenje() - KapacitetLokacije.listaRaspoloziveVrsteVozilaNaLokaciji(lokacijaSifra.getId(), vrstaVozila.getId()).size()) + "\n");

        return;
    }

    public static void ispisiRacune(PostavkeSingleton postavkeSingleton, int idOrganizacijskeJedinice, int racuni, Date pocetni, Date krajnji) {

        if (pocetni.compareTo(krajnji) > 0) {
            System.out.println("Pogreska aktivnost 8: Pocetni datum ne smije biti veci do krajnjeg datuma!");
            return;
        }

        if (ispisiStrukturu(postavkeSingleton, idOrganizacijskeJedinice, 0) == 0) {
            System.out.println("Greska kod kreiranja i ispisa strukture!\n");
            return;
        }

        System.out.println();

        if (racuni == 1) {
            String decimalFormatString = "";
            for (int i = 0; i < postavkeSingleton.dc; i++) {
                decimalFormatString += "0";
            }
            decimalFormatString += ".";
            for (int i = 0; i < postavkeSingleton.dd; i++) {
                decimalFormatString += "0";
            }
            DecimalFormat df = new DecimalFormat(decimalFormatString);

            SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Struktura s : Struktura.strukturePoRazinama) {
                for (Lokacija l : s.getLokacije()) {
                    System.out.println("Ispis najmova za lokaciju " + l.getNazivLokacije() + " u organizacijskoj jedinici " + s.getNaziv());
                    System.out.println("{Prvi stupac: ID racuna} {Drugi stupac: ID osobe} {Treci stupac: ID lokacije} {Cetvrti stupac: Ime i prezime} \n{Peti stupac: Datum izdavanja} {Sesti stupac: Iznos} {Sedmi stupac: Struktura racuna}");

                    for (Racun r : Racun.dohvatiListuRacunaFiltriranuPoDatumima(PostavkeSingleton.getRacunLista(), pocetni, krajnji)) {
                        if (l.getId() == r.getLokacijaId()) {
                            tablicaFormatirajBroj(postavkeSingleton, (int) r.getJIDracuna());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajBroj(postavkeSingleton, r.getOsobaId());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajBroj(postavkeSingleton, r.getLokacijaId());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajTekst(postavkeSingleton, Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), r.getOsobaId()).getImePrezime());
                            tablicaFormatirajRazmak(postavkeSingleton);

                            if (r.getDatumIzdavanja() != null) {
                                tablicaFormatirajTekst(postavkeSingleton, vrijemeFormat.format(r.getDatumIzdavanja()));
                                tablicaFormatirajRazmak(postavkeSingleton);
                            } else {
                                tablicaFormatirajTekst(postavkeSingleton, "");
                                tablicaFormatirajRazmak(postavkeSingleton);
                            }

                            System.out.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc) + "s", String.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc) + "s ", df.format(r.getIznos())).substring(0, (postavkeSingleton.dd + postavkeSingleton.dc)));
                            System.out.format("%" + postavkeSingleton.razmakStupci + "s", " ");
                            tablicaFormatirajTekst(postavkeSingleton, r.getOpis());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            System.out.println();
                        }
                    }
                    System.out.println();
                }
                System.out.println();
            }

            System.out.println("Kumulativni ispis racuna ");
            System.out.println("{Prvi stupac: ID racuna} {Drugi stupac: ID osobe} {Treci stupac: ID lokacije} {Cetvrti stupac: Ime i prezime} \n{Peti stupac: Datum izdavanja} {Sesti stupac: Iznos} {Sedmi stupac: Struktura racuna}");

            float ukupnaZarada = 0;
            for (Struktura s : Struktura.strukturePoRazinama) {
                for (Lokacija l : s.getLokacije()) {
                    for (Racun r : Racun.dohvatiListuRacunaFiltriranuPoDatumima(PostavkeSingleton.getRacunLista(), pocetni, krajnji)) {
                        if (l.getId() == r.getLokacijaId()) {

                            tablicaFormatirajBroj(postavkeSingleton, (int) r.getJIDracuna());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajBroj(postavkeSingleton, r.getOsobaId());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajBroj(postavkeSingleton, r.getLokacijaId());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajTekst(postavkeSingleton, Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), r.getOsobaId()).getImePrezime());
                            tablicaFormatirajRazmak(postavkeSingleton);

                            if (r.getDatumIzdavanja() != null) {
                                tablicaFormatirajTekst(postavkeSingleton, vrijemeFormat.format(r.getDatumIzdavanja()));
                                tablicaFormatirajRazmak(postavkeSingleton);
                            } else {
                                tablicaFormatirajTekst(postavkeSingleton, "");
                                tablicaFormatirajRazmak(postavkeSingleton);
                            }

                            System.out.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc) + "s", String.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc) + "s ", df.format(r.getIznos())).substring(0, (postavkeSingleton.dd + postavkeSingleton.dc)));
                            System.out.format("%" + postavkeSingleton.razmakStupci + "s", " ");
                            ukupnaZarada += r.getIznos();
                            tablicaFormatirajTekst(postavkeSingleton, r.getOpis());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            System.out.println();
                        }
                    }
                }
            }
            System.out.println("Ukupna zarada: " + df.format(ukupnaZarada) + " kn\n");
        }

        Struktura.strukturePoRazinama.clear();
        for (Lokacija l : PostavkeSingleton.getLokacijaLista()) {
            l.setJeSastavniDioOJ(false);
        }
        //PostavkeSingleton.getRacunLista().clear();;
        //Racun.setIdBrojac(0);
    }

    public static void ispisiNajmove(PostavkeSingleton postavkeSingleton, int idOrganizacijskeJedinice, int najam, int zarada, Date pocetni, Date krajnji) {

        if (pocetni.compareTo(krajnji) > 0) {
            System.out.println("Pogreska aktivnost 7: Pocetni datum ne smije biti veci do krajnjeg datuma!");
            return;
        }

        if (ispisiStrukturu(postavkeSingleton, idOrganizacijskeJedinice, 0) == 0) {
            System.out.println("Greska kod kreiranja i ispisa strukture!\n");
            return;
        }

        if (najam == 1) {

            ArrayList<Lokacija> listaLokacijaTemp = new ArrayList<Lokacija>();
            for (Struktura s : Struktura.strukturePoRazinama) {
                for (Lokacija l : s.getLokacije()) {
                    listaLokacijaTemp.add(l);
                }
            }

            for (Najam n : Najam.dohvatiListuNajmovaFiltriranuPoDatumima(PostavkeSingleton.getNajamLista(), pocetni, krajnji)) {
                for (Lokacija l : listaLokacijaTemp) {
                    for (KapacitetLokacije kl : l.getKapacitetLokacije()) {
                        if (l.getId() == n.getLokacijaId() && kl.getVozilo().getId() == n.getVoziloVrstaId()) {
                            kl.setBrojNajmova(kl.getBrojNajmova() + 1);
                        }
                    }
                }
            }

            for (Lokacija l : listaLokacijaTemp) {
                System.out.println("Ispis broja najmova po vozilu " + l.getNazivLokacije() + " na lokaciji " + l.getNazivLokacije());
                System.out.println("{Prvi stupac: ID vozila} {Drugi stupac: Naziv vozila} {Treci stupac: Broj najmova}");
                for (KapacitetLokacije kl : l.getKapacitetLokacije()) {
                    tablicaFormatirajBroj(postavkeSingleton, kl.getVozilo().getId());
                    tablicaFormatirajRazmak(postavkeSingleton);
                    tablicaFormatirajTekst(postavkeSingleton, kl.getVozilo().getNaziv());
                    tablicaFormatirajRazmak(postavkeSingleton);
                    tablicaFormatirajBroj(postavkeSingleton, kl.getBrojNajmova());
                    tablicaFormatirajRazmak(postavkeSingleton);
                    System.out.println();
                }
                System.out.println();
            }

            ArrayList<KapacitetLokacije> kumulativnaLista = new ArrayList<KapacitetLokacije>();
            for (ElektricnoVozilo ev : PostavkeSingleton.getElektricnoVoziloLista()) {
                kumulativnaLista.add(new KapacitetLokacije(ev));
            }

            for (Struktura s : Struktura.strukturePoRazinama) {
                for (Lokacija l : s.getLokacije()) {
                    for (Najam n : Najam.dohvatiListuNajmovaFiltriranuPoDatumima(PostavkeSingleton.getNajamLista(), pocetni, krajnji)) {
                        if (l.getId() == n.getLokacijaId()) {
                            for (KapacitetLokacije kl : kumulativnaLista) {
                                if (kl.getVozilo().getId() == n.getVoziloVrstaId()) {
                                    kl.setBrojNajmova(kl.getBrojNajmova() + 1);
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("Kumulativni broja najmova: ");
            System.out.println("{Prvi stupac: ID vozila} {Drugi stupac: Naziv vozila} {Treci stupac: Broj najmova}");

            for (KapacitetLokacije kl : kumulativnaLista) {
                tablicaFormatirajBroj(postavkeSingleton, kl.getVozilo().getId());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajTekst(postavkeSingleton, kl.getVozilo().getNaziv());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajBroj(postavkeSingleton, kl.getBrojNajmova());
                tablicaFormatirajRazmak(postavkeSingleton);
                System.out.println();
            }
            System.out.println();

        }

        if (zarada == 1) {

            String decimalFormatString = "";
            for (int i = 0; i < postavkeSingleton.dc; i++) {
                decimalFormatString += "0";
            }
            decimalFormatString += ".";
            for (int i = 0; i < postavkeSingleton.dd; i++) {
                decimalFormatString += "0";
            }
            DecimalFormat df = new DecimalFormat(decimalFormatString);

            SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Struktura s : Struktura.strukturePoRazinama) {
                for (Lokacija l : s.getLokacije()) {
                    System.out.println("Ispis najmova za lokaciju " + l.getNazivLokacije() + " u organizacijskoj jedinici " + s.getNaziv());
                    System.out.println("{Prvi stupac: Jedinstveni ID najma} {Drugi stupac: ID vrste vozila} {Treci stupac: ID korisnika} {Cetvrti stupac: Ime i prezime korisnika} \n{Peti stupac: Pocetak najma} {Sesti stupac: Kraj najma} {Sedmi stupac: Trajanje najma} {Osmi stupac: zarada}");
                    for (Najam n : Najam.dohvatiListuNajmovaFiltriranuPoDatumima(PostavkeSingleton.getNajamLista(), pocetni, krajnji)) {
                        if (l.getId() == n.getLokacijaId()) {
                            tablicaFormatirajBroj(postavkeSingleton, (int) n.getJIDnajam());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajBroj(postavkeSingleton, n.getVoziloVrstaId());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajBroj(postavkeSingleton, n.getOsosbaId());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajTekst(postavkeSingleton, Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), n.getOsosbaId()).getImePrezime());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajTekst(postavkeSingleton, vrijemeFormat.format(n.getPocetakNajma()));
                            tablicaFormatirajRazmak(postavkeSingleton);
                            if (n.getKrajNajma() != null) {
                                tablicaFormatirajTekst(postavkeSingleton, vrijemeFormat.format(n.getKrajNajma()));
                                tablicaFormatirajRazmak(postavkeSingleton);
                            } else {
                                tablicaFormatirajTekst(postavkeSingleton, "");
                                tablicaFormatirajRazmak(postavkeSingleton);
                            }
                            if (n.getKrajNajma() != null && n.getPocetakNajma() != null) {
                                tablicaFormatirajTekst(postavkeSingleton, n.ispisiTrajanje(n.getPocetakNajma(), n.getKrajNajma()));
                                tablicaFormatirajRazmak(postavkeSingleton);
                            } else {
                                tablicaFormatirajTekst(postavkeSingleton, "");
                                tablicaFormatirajRazmak(postavkeSingleton);
                            }
                            if (n.getKrajNajma() != null && n.getPocetakNajma() != null) {
                                //provjeriti
                                System.out.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc + 1) + "s", String.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc + 1) + "s ", df.format(n.getZarada())).substring(0, (postavkeSingleton.dd + postavkeSingleton.dc + 1)));
                                System.out.format("%" + postavkeSingleton.razmakStupci + "s", " ");
                            } else {
                                tablicaFormatirajTekst(postavkeSingleton, "Nije definirano");
                                tablicaFormatirajRazmak(postavkeSingleton);
                            }
                            System.out.println();
                        }
                    }
                    System.out.println();
                }
                System.out.println();
            }

            System.out.println("Kumulativni ispis najmova ");
            System.out.println("{Prvi stupac: Jedinstveni ID najma} {Drugi stupac: ID vrste vozila} {Treci stupac: ID korisnika} {Cetvrti stupac: Ime i prezime korisnika} \n{Peti stupac: Pocetak najma} {Sesti stupac: Kraj najma} {Sedmi stupac: Trajanje najma} {Osmi stupac: zarada}");
            float ukupnaZarada = 0;
            for (Struktura s : Struktura.strukturePoRazinama) {
                for (Lokacija l : s.getLokacije()) {
                    for (Najam n : Najam.dohvatiListuNajmovaFiltriranuPoDatumima(PostavkeSingleton.getNajamLista(), pocetni, krajnji)) {
                        if (l.getId() == n.getLokacijaId()) {
                            tablicaFormatirajBroj(postavkeSingleton, (int) n.getJIDnajam());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajBroj(postavkeSingleton, n.getVoziloVrstaId());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajBroj(postavkeSingleton, n.getOsosbaId());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajTekst(postavkeSingleton, Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), n.getOsosbaId()).getImePrezime());
                            tablicaFormatirajRazmak(postavkeSingleton);
                            tablicaFormatirajTekst(postavkeSingleton, vrijemeFormat.format(n.getPocetakNajma()));
                            tablicaFormatirajRazmak(postavkeSingleton);

                            if (n.getKrajNajma() != null) {
                                tablicaFormatirajTekst(postavkeSingleton, vrijemeFormat.format(n.getKrajNajma()));
                                tablicaFormatirajRazmak(postavkeSingleton);
                            } else {
                                tablicaFormatirajTekst(postavkeSingleton, "");
                                tablicaFormatirajRazmak(postavkeSingleton);
                            }
                            if (n.getKrajNajma() != null && n.getPocetakNajma() != null) {
                                tablicaFormatirajTekst(postavkeSingleton, n.ispisiTrajanje(n.getPocetakNajma(), n.getKrajNajma()));
                                tablicaFormatirajRazmak(postavkeSingleton);
                            } else {
                                tablicaFormatirajTekst(postavkeSingleton, "");
                                tablicaFormatirajRazmak(postavkeSingleton);
                            }
                            if (n.getKrajNajma() != null && n.getPocetakNajma() != null) {
                                //provjeriti
                                System.out.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc + 1) + "s", String.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc + 1) + "s ", df.format(n.getZarada())).substring(0, (postavkeSingleton.dd + postavkeSingleton.dc + 1)));
                                System.out.format("%" + postavkeSingleton.razmakStupci + "s\n", " ");
                                ukupnaZarada += n.getZarada();
                            } else {
                                tablicaFormatirajTekst(postavkeSingleton, "Nije definirano");
                                tablicaFormatirajRazmak(postavkeSingleton);
                            }
                        }
                    }
                }
            }
            System.out.println("Ukupna zarada: " + df.format(ukupnaZarada) + " kn\n");
        }

        Struktura.strukturePoRazinama.clear();
        for (Lokacija l : PostavkeSingleton.getLokacijaLista()) {
            l.setJeSastavniDioOJ(false);
        }
        //PostavkeSingleton.getNajamLista().clear();
        //Najam.setBrojacId(0);
    }

    public static int ispisiStrukturu(PostavkeSingleton postavkeSingleton, int idOrganizacijskeJedinice, int stanje) {
        StrukturaBuilder sb = new StrukturaBuilderImplementacija();
        Struktura izvorisnaOJ = new Struktura(OrganizacijskaJedinica.dohvatiIzvorisnuOJ(PostavkeSingleton.getOrganizacijskaJedinicaLista()));

        if (izvorisnaOJ == null) {
            System.out.println("Nije moguce konstruirati strukturu jer ne postoji izvorisna organizacijska jedinica!");
            System.exit(0);
        } else {
            // sb.saPodredenomOrganizacijskomJedinicom(izvorisnaOJ);
            sb.definirajIzvorisnuOj(izvorisnaOJ);
            Struktura s = sb.build();

            if (idOrganizacijskeJedinice != -1) {
                s = Struktura.dohvatiStrukturuPoId(s, idOrganizacijskeJedinice);
                if (s == null) {
                    System.out.println("Organizacijska jedinica s id " + idOrganizacijskeJedinice + " nije dio strukture organizacije\n");
                    Struktura.strukturePoRazinama.clear();
                    for (Lokacija l : PostavkeSingleton.getLokacijaLista()) {
                        l.setJeSastavniDioOJ(false);
                    }
                    //CODE 0 NEUSPJESNO
                    return 0;
                }
            }

            System.out.println("\n{Prvi stupac: ID lokacije} {Drugi stupac: Naziv organizacijske jedinice} {Treci stupac: Nadredena organizacijska jedinica} {Cetvrti stupac: Strukturna razina (po hijerarhiji)}");
            int temp = 0;
            s.setStrukturnaRazina(temp);
            Struktura.strukturePoRazinama.add(s);

            if (s.getNadredenaJedinica() != 0) {
                tablicaFormatirajBroj(postavkeSingleton, s.getId());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajTekst(postavkeSingleton, s.getNaziv());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajBroj(postavkeSingleton, s.getNadredenaJedinica());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajBroj(postavkeSingleton, temp);
                System.out.println();
            } else {
                tablicaFormatirajBroj(postavkeSingleton, s.getId());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajTekst(postavkeSingleton, s.getNaziv());
                tablicaFormatirajRazmak(postavkeSingleton);
                System.out.format("%" + postavkeSingleton.dc + "s", String.format("%" + postavkeSingleton.dc + "s ", "").substring(0, postavkeSingleton.dc));
                System.out.println();
            }

            for (Struktura struktura : s.getPodredeneOJ()) {
                StrukturaRepozitorij sr = new StrukturaRepozitorij(struktura);
                temp = 1;
                sr.getS().setStrukturnaRazina(temp);
                Struktura.strukturePoRazinama.add(sr.getS());
                tablicaFormatirajBroj(postavkeSingleton, sr.getS().getId());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajTekst(postavkeSingleton, sr.getS().getNaziv());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajBroj(postavkeSingleton, sr.getS().getNadredenaJedinica());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajBroj(postavkeSingleton, temp);
                tablicaFormatirajRazmak(postavkeSingleton);
                System.out.println();

                Iterator iter = sr.getIterator();
                while (iter.hasNext()) {
                    temp++;
                    for (Struktura sa : (ArrayList<Struktura>) iter.next()) {
                        sr.setS(sa);
                        iter = sr.getIterator();
                        sr.getS().setStrukturnaRazina(temp);
                        Struktura.strukturePoRazinama.add(sr.getS());
                        tablicaFormatirajBroj(postavkeSingleton, sr.getS().getId());
                        tablicaFormatirajRazmak(postavkeSingleton);
                        tablicaFormatirajTekst(postavkeSingleton, sr.getS().getNaziv());
                        tablicaFormatirajRazmak(postavkeSingleton);
                        tablicaFormatirajBroj(postavkeSingleton, sr.getS().getNadredenaJedinica());
                        tablicaFormatirajRazmak(postavkeSingleton);
                        tablicaFormatirajBroj(postavkeSingleton, temp);
                        tablicaFormatirajRazmak(postavkeSingleton);
                        System.out.println();
                    }
                }
            }
        }
        System.out.println();
        //StrukturaComparator sc = new StrukturaComparator();
        /*
        for(Struktura s : Struktura.strukturePoRazinama){
            System.out.println(s.getNaziv());
        }

        Collections.sort(Struktura.strukturePoRazinama,sc);
        /*
        for(Struktura sa : Struktura.strukturePoRazinama){
            System.out.println(sa.getStrukturnaRazina());
        }
         */
        for (Struktura s : Struktura.strukturePoRazinama) {
            Struktura.provjeriLokacije(s, s.getLokacijeTemp());
        }

        for (Struktura s : Struktura.strukturePoRazinama) {
            System.out.println("Ispis lokacija za organizacijsku jedinicu " + s.getNaziv());
            System.out.println("{Prvi stupac: ID} {Drugi stupac: Naziv lokacije} {Treci stupac: adresa} {Cetvrti stupac: GPS)}");
            for (Lokacija l : s.getLokacije()) {
                tablicaFormatirajBroj(postavkeSingleton, l.getId());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajTekst(postavkeSingleton, l.getNazivLokacije());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajTekst(postavkeSingleton, l.getAdresaLokacije());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajGPSkoordinate(postavkeSingleton, l.getGps());
                System.out.println();
            }
            System.out.println();
        }

        if (stanje == 1) {
            ArrayList<KapacitetLokacije> kumulativnaLista = new ArrayList<KapacitetLokacije>();
            for (ElektricnoVozilo ev : PostavkeSingleton.getElektricnoVoziloLista()) {
                kumulativnaLista.add(new KapacitetLokacije(ev));
            }

            for (Struktura s : Struktura.strukturePoRazinama) {
                for (Lokacija l : s.getLokacije()) {
                    System.out.println("Ispis stanja za lokaciju " + l.getNazivLokacije() + " u organizacijskoj jedinici " + s.getNaziv());
                    System.out.println("{Prvi stupac: ID vozila} {Drugi stupac: Naziv vozila} {Treci stupac: Broj raspolozivih vozila} {Cetvrti stupac: Broj punjenja za vrstu vozila)} {Peti stupac: Broj neispravnih vozila)");
                    for (KapacitetLokacije kl : l.getKapacitetLokacije()) {
                        for (KapacitetLokacije k : kumulativnaLista) {
                            if (k.getVozilo().idVrstaVozila == kl.getVozilo().getId()) {
                                k.setRaspolozivo(k.getRaspolozivo() + KapacitetLokacije.dohvatiListuRaspolozivihVozilaOdredeneVrsteNaLokaciji(kl.getVozilo().getId(), l.getId()).size());
                                k.setMjestaZaPunjenje(k.getMjestaZaPunjenje() + kl.getMjestaZaPunjenje());
                                k.setBrojNeispravnih(k.getBrojNeispravnih() + kl.getBrojNeispravnih());
                            }
                        }
                        tablicaFormatirajBroj(postavkeSingleton, kl.getVozilo().getId());
                        tablicaFormatirajRazmak(postavkeSingleton);
                        tablicaFormatirajTekst(postavkeSingleton, kl.getVozilo().getNaziv());
                        tablicaFormatirajRazmak(postavkeSingleton);
                        tablicaFormatirajBroj(postavkeSingleton, KapacitetLokacije.dohvatiListuRaspolozivihVozilaOdredeneVrsteNaLokaciji(kl.getVozilo().getId(), l.getId()).size());
                        tablicaFormatirajRazmak(postavkeSingleton);
                        tablicaFormatirajBroj(postavkeSingleton, kl.getMjestaZaPunjenje());
                        tablicaFormatirajRazmak(postavkeSingleton);
                        tablicaFormatirajBroj(postavkeSingleton, kl.getBrojNeispravnih());
                        tablicaFormatirajRazmak(postavkeSingleton);
                        System.out.println();
                    }
                    System.out.println();
                }
            }

            System.out.println("Kumulativan ispis podataka za trazene organizacijske jedinice");
            System.out.println("{Prvi stupac: ID vozila} {Drugi stupac: Naziv vozila} {Treci stupac: Broj raspolozivih vozila} {Cetvrti stupac: Broj punjenja za vrstu vozila)} {Peti stupac: Broj neispravnih vozila)");

            for (KapacitetLokacije k : kumulativnaLista) {
                tablicaFormatirajBroj(postavkeSingleton, k.getVozilo().getId());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajTekst(postavkeSingleton, k.getVozilo().getNaziv());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajBroj(postavkeSingleton, k.getRaspolozivo());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajBroj(postavkeSingleton, k.getMjestaZaPunjenje());
                tablicaFormatirajRazmak(postavkeSingleton);
                tablicaFormatirajBroj(postavkeSingleton, k.getBrojNeispravnih());
                tablicaFormatirajRazmak(postavkeSingleton);
                System.out.println();
            }
        }

        for (Lokacija l : PostavkeSingleton.getLokacijaLista()) {
            l.setJeSastavniDioOJ(false);
        }
        //CODE 1 USPJESNO
        return 1;
    }

    public static void ispisiPodatkeRacuniKorisnika(PostavkeSingleton postavkeSingleton, int korisnik, Date pocetni, Date krajnji) throws ParseException {

        if (pocetni.compareTo(krajnji) > 0) {
            System.out.println("Pogreska aktivnost 8: Pocetni datum ne smije biti veci do krajnjeg datuma!");
            return;
        }
        
        SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ArrayList<Racun> listaNeplacenihRacunaZaKorisnika = new ArrayList<Racun>();
        ArrayList<Racun> listaPlacenihRacunaZaKorisnika = new ArrayList<Racun>();

        for (Racun r : Racun.dohvatiListuRacunaFiltriranuPoDatumima(PostavkeSingleton.getRacunLista(), pocetni, krajnji)) {
            if (r.getOsobaId() == korisnik && r.isPlacen() == false) {
                listaNeplacenihRacunaZaKorisnika.add(r);
            }
        }
        Collections.sort(listaNeplacenihRacunaZaKorisnika, new SortByDate());

        for (Racun r : Racun.dohvatiListuRacunaFiltriranuPoDatumima(PostavkeSingleton.getRacunLista(), pocetni, krajnji)) {
            if (r.getOsobaId() == korisnik && r.isPlacen() == true) {
                listaNeplacenihRacunaZaKorisnika.add(r);
            }
        }
        Collections.sort(listaPlacenihRacunaZaKorisnika, new SortByDate());

        String decimalFormatString = "";
        for (int i = 0; i < postavkeSingleton.dc; i++) {
            decimalFormatString += "0";
        }
        decimalFormatString += ".";
        for (int i = 0; i < postavkeSingleton.dd; i++) {
            decimalFormatString += "0";
        }
        DecimalFormat df = new DecimalFormat(decimalFormatString);

        
        System.out.println("Ispis neplacenih i placenih racuna za osobu: " + Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), korisnik).getImePrezime());
        System.out.println("{Prvi stupac: ID racuna} {Drugi stupac: Ime i prezime} {Treci stupac: Naziv lokacije} {Cetvrti stupac: Iznos} \n{Peti stupac: Placen(da/ne)} {Sesti stupac: Opis} {Sedmi stupac: Datum izdavanja}");

        for (Racun r : listaNeplacenihRacunaZaKorisnika) {
            tablicaFormatirajBroj(postavkeSingleton, (int) r.getJIDracuna());
            tablicaFormatirajRazmak(postavkeSingleton);
            tablicaFormatirajTekst(postavkeSingleton, Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), r.getOsobaId()).getImePrezime());
            tablicaFormatirajTekst(postavkeSingleton, Lokacija.dohvatiLokacijuIzListe(PostavkeSingleton.getLokacijaLista(), r.getLokacijaId()).getNazivLokacije());
            System.out.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc + 1) + "s", String.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc + 1) + "s ", df.format(r.getIznos())).substring(0, (postavkeSingleton.dd + postavkeSingleton.dc + 1)));
            System.out.format("%" + postavkeSingleton.razmakStupci + "s", " ");

            if (r.isPlacen() == false) {
                tablicaFormatirajTekst(postavkeSingleton, "ne");
                tablicaFormatirajRazmak(postavkeSingleton);
            } else if (r.isPlacen() == true) {
                tablicaFormatirajTekst(postavkeSingleton, "da");
                tablicaFormatirajRazmak(postavkeSingleton);
            }

            tablicaFormatirajTekst(postavkeSingleton, r.getOpis());
            tablicaFormatirajRazmak(postavkeSingleton);

            if (r.getDatumIzdavanja() != null) {
                tablicaFormatirajTekst(postavkeSingleton, vrijemeFormat.format(r.getDatumIzdavanja()));
                tablicaFormatirajRazmak(postavkeSingleton);
            } else {
                tablicaFormatirajTekst(postavkeSingleton, "");
                tablicaFormatirajRazmak(postavkeSingleton);
            }
            tablicaFormatirajRazmak(postavkeSingleton);
            System.out.println();
        }
        System.out.println();
    }
    
    public static void platiDugovanje(PostavkeSingleton postavkeSingleton, int korisnik, float iznos){
        
        if(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), korisnik).getUgovor()==0){
            System.out.println("Osoba "+Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), korisnik).getImePrezime()+" nije u sustavu ugovora.");
            return;
        }
        
        float ukupanIznos = (iznos + Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), korisnik).getOstatak());
        
        System.out.println("Ukupan iznos za podmirivanje racuna: " + iznos + "kn (uplaceni iznos) + "+Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), korisnik).getOstatak()+"kn (prethodni ostaci)");
        
        ArrayList<Racun> listaNeplacenihRacunaZaKorisnika = new ArrayList<Racun>();

        for (Racun r : PostavkeSingleton.getRacunLista()) {
            if (r.getOsobaId() == korisnik && r.isPlacen() == false) {
                listaNeplacenihRacunaZaKorisnika.add(r);
            }
        }
        Collections.sort(listaNeplacenihRacunaZaKorisnika, new SortByDate());
        
        for(Racun r : listaNeplacenihRacunaZaKorisnika){
            if(ukupanIznos>=r.getIznos()){
                ukupanIznos=ukupanIznos-r.getIznos();
                r.setPlacen(true);
                System.out.println("Racun ID: "+r.getJIDracuna()+" PODMIREN");
            }else System.out.println("Racun ID: "+r.getJIDracuna()+" NEDOVOLJAN IZNOS - "+ukupanIznos+"/"+r.getIznos()+" kn");
        }
        
        Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), korisnik).setOstatak(Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), korisnik).getOstatak()+ukupanIznos);
        System.out.println("Ostatak: "+ukupanIznos+" kn vracen korisniku "+Osoba.dohvatiOsobuIzListe(PostavkeSingleton.getOsobaLista(), korisnik).getImePrezime());       
        System.out.println();
    }
    
    static class SortByDate implements Comparator<Racun> {

        @Override
        public int compare(Racun a, Racun b) {
            return a.getDatumIzdavanja().compareTo(b.getDatumIzdavanja());
        }
    }

    public static void ispisiStanjeOsobe(PostavkeSingleton postavkeSingleton) {
        String decimalFormatString = "";
        for (int i = 0; i < postavkeSingleton.dc; i++) {
            decimalFormatString += "0";
        }
        decimalFormatString += ".";
        for (int i = 0; i < postavkeSingleton.dd; i++) {
            decimalFormatString += "0";
        }
        DecimalFormat df = new DecimalFormat(decimalFormatString);

        SimpleDateFormat vrijemeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("Ispis financijskog stanja korisnika");
        System.out.println("{Prvi stupac: ID osobe} {Drugi stupac: Ime i prezime} {Treci stupac: Stanje (Iznos se azurira kod vracanja vozila)} {Cetvrti stupac: Datum i vrijeme zadnjeg najma} {Peti stupac: U sustavu ugovora}");

        for (Osoba o : PostavkeSingleton.getOsobaLista()) {
            tablicaFormatirajBroj(postavkeSingleton, o.getId());
            tablicaFormatirajRazmak(postavkeSingleton);
            tablicaFormatirajTekst(postavkeSingleton, o.getImePrezime());
            tablicaFormatirajRazmak(postavkeSingleton);
            System.out.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc + 1) + "s", String.format("%" + (postavkeSingleton.dd + postavkeSingleton.dc + 1) + "s ", df.format(o.getDugovanje())).substring(0, (postavkeSingleton.dd + postavkeSingleton.dc + 1)));
            System.out.format("%" + postavkeSingleton.razmakStupci + "s", " ");
            if (o.getDatumVrijemeZadnjegNajma() != null) {
                tablicaFormatirajTekst(postavkeSingleton, vrijemeFormat.format(o.getDatumVrijemeZadnjegNajma()));
                tablicaFormatirajRazmak(postavkeSingleton);
            } else {
                tablicaFormatirajTekst(postavkeSingleton, "Nema najmova");
                tablicaFormatirajRazmak(postavkeSingleton);
            }

            if (o.getUgovor() == 0) {
                tablicaFormatirajTekst(postavkeSingleton, "ne");
                tablicaFormatirajRazmak(postavkeSingleton);
            } else if (o.getUgovor() == 1) {
                tablicaFormatirajTekst(postavkeSingleton, "da");
                tablicaFormatirajRazmak(postavkeSingleton);
            }

            System.out.println();
        }
        System.out.println();
    }

    private static void tablicaFormatirajBroj(PostavkeSingleton postavkeSingleton, int broj) {
        System.out.format("%" + postavkeSingleton.dc + "s", String.format("%" + postavkeSingleton.dc + "d ", broj).substring(0, postavkeSingleton.dc));
    }

    private static void tablicaFormatirajTekst(PostavkeSingleton postavkeSingleton, String tekst) {
        System.out.format("%-" + postavkeSingleton.dt + "s", String.format("%-" + postavkeSingleton.dt + "s ", tekst).substring(0, postavkeSingleton.dt));
    }

    private static void tablicaFormatirajRazmak(PostavkeSingleton postavkeSingleton) {
        System.out.format("%" + postavkeSingleton.razmakStupci + "s", " ");
    }

    private static void tablicaFormatirajGPSkoordinate(PostavkeSingleton postavkeSingleton, String GPS) {
        String[] gps = GPS.replace(" ", "").split(",");
        String[] prvi = gps[0].split("\\.");
        String[] drugi = gps[1].split("\\.");
        if (prvi[0].length() > postavkeSingleton.dc) {
            prvi[0] = prvi[0].substring(0, postavkeSingleton.dc);
        }
        if (prvi[1].length() > postavkeSingleton.dd) {
            prvi[1] = prvi[1].substring(0, postavkeSingleton.dd);
        }
        if (drugi[0].length() > postavkeSingleton.dc) {
            drugi[0] = drugi[0].substring(0, postavkeSingleton.dc);
        }
        if (drugi[1].length() > postavkeSingleton.dd) {
            drugi[1] = drugi[1].substring(0, postavkeSingleton.dd);
        }
        System.out.format("%" + postavkeSingleton.dt + "s", String.format("%-" + postavkeSingleton.dt + "s ", prvi[0] + "." + prvi[1] + "," + drugi[0] + "." + drugi[1]).substring(0, postavkeSingleton.dt));
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Date vrijeme) {
        this.vrijeme = vrijeme;
    }

    public int getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(int korisnik) {
        this.korisnik = korisnik;
    }

    public int getLokacija() {
        return lokacija;
    }

    public void setLokacija(int lokacija) {
        this.lokacija = lokacija;
    }

    public int getVrstaVozila() {
        return vrstaVozila;
    }

    public void setVrstaVozila(int vrstaVozila) {
        this.vrstaVozila = vrstaVozila;
    }

    public int getBrojKM() {
        return brojKM;
    }

    public void setBrojKM(int brojKM) {
        this.brojKM = brojKM;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getParametar1() {
        return parametar1;
    }

    public void setParametar1(String parametar1) {
        this.parametar1 = parametar1;
    }

    public String getParametar2() {
        return parametar2;
    }

    public void setParametar2(String parametar2) {
        this.parametar2 = parametar2;
    }

    public String getParametar3() {
        return parametar3;
    }

    public void setParametar3(String parametar3) {
        this.parametar3 = parametar3;
    }

    public int getIdOrgJedinice() {
        return idOrgJedinice;
    }

    public void setIdOrgJedinice(int idOrgJedinice) {
        this.idOrgJedinice = idOrgJedinice;
    }

    public Date getPocetniDatum() {
        return pocetniDatum;
    }

    public void setPocetniDatum(Date pocetniDatum) {
        this.pocetniDatum = pocetniDatum;
    }

    public Date getKrajnjiDatum() {
        return krajnjiDatum;
    }

    public void setKrajnjiDatum(Date krajnjiDatum) {
        this.krajnjiDatum = krajnjiDatum;
    }

    public float getIznos() {
        return iznos;
    }

    public void setIznos(float iznos) {
        this.iznos = iznos;
    }

}
