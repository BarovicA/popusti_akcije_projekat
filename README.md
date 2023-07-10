Specifikacija projekta

Cilj projekta je da se implementira veb aplikacija koja pruža mogućnost postavljanja raznih ponuda na Internet. Ponuda predstavlja robu ili usluge koje se nude po povoljnim cenama. Aplikacija treba da omogući pregled, pretragu i kupovinu ponuda. Projekat ima sledeće zadatke:

• upravljanje podacima,
• izveštavanje i
• bezbednost.

Upravljanje podacima

Entiteti

U sistemu (veb aplikaciji) postoje tri tipa korisnika: administratori, prodavci i kupci. Korisnik je opisan sledećim podacima:

• ime,
• prezime
• korisničko ime,
• lozinka,
• email i
• uloga u sistemu (moguće vrednosti su: ROLE_ADMIN, ROLE_SELLER i ROLE_CUSTOMER).

Pored korisnika, aplikacija rukuje sa sledećim entitetima:

• kategorija,
• ponuda,
• račun i
• vaučer.

Kategorija predstavlja kategoriju kojoj ponuda pripada i opisana je sledećim podacima:

• naziv i
• opis.

Ponuda predstavlja konkretnu ponudu koju prodavac postavlja na sajt. Pored kategorije kojoj pripada, ponuda ima i sledeće podatke:

• naziv,
• datum kreiranja ponude,
• datum do kada ponuda važi,
• redovna cena,
• akcijska cena,
• slika ponude (putanja do slike),
• broj dostupnih ponuda,
• koliko je trenutno kupljeno i
• status ponude (moguće vrednosti su: WAIT_FOR_APPROVING, APPROVED, DECLINED i EXPIRED).


Račun predstavlja kupovinu određenih ponuda, odnosno sadrži informacije koji kupac je kupio koju ponudu. Pored informacija o kupcu i kupljenoj ponudi, račun ima i sledeće podatke:

• datum kreiranja računa,
• da li je kupac uplatio novac i
• da li je kupovina otkazana.
Vaučer se kreira nakon uspešne kupovine, odnosno, nakon što je kupac uplatio novac. Pored kupca i ponude, vaučer sadrži i sledeće podatke:
• datum do kada važi i
• da li je iskorišćen.

Veze između entiteta

Prodavac može da kreira više ponuda, dok jednu ponudu kreira tačno jedan prodavac. Ponuda sadrži tačno jednu kategoriju, dok jedna kategorija ima više ponuda koje joj pripadaju. Račun predstavlja kupovinu jedne ponude od strane jednog kupca. Jedna ponuda se može naći na više računa. Kupac može imati više različitih računa. Vaučer predstavlja račun na kome je uplata novca izvršena, pa se poput računa, vaučer odnosi na kupovinu jedne ponude od strane jednog kupca. Jedna ponuda se može naći na više vaučera i jedan kupac može imati više vaučera na svoje ime.

Izveštavanje

Aplikacija treba da omogući generisanje izveštaja namenjenih administratorima i prodavcima. Postoje dva tipa izveštaja:

• Izveštaj o ukupnoj prodaji po danima – administrator unosi period za koji želi da dobije izveštaj (primer izveštaja dat je na slici 1). Izveštaj treba da sadrži sledeće podatke:
o datum,
o zarada za taj datum
o i na kraju ukupna zarada za taj period.

• Izveštaj o prodaji po kategorijama ponuda – administrator unosi kategoriju za koju želi da dobije izveštaj, kao i period (primer izveštaja dat je na slici 2). Izveštaj treba da sadrži sledeće podatke:
o naziv kategorije,
o datum,
o broj prodatih ponuda te kategorije za taj datum (DODATNI ZADATAK),
o zarada za taj datum
o i na kraju ukupna zarada za taj period za odabranu kategoriju i ukupan broj prodatih ponuda te kategorije.

Bezbednost

Aplikacija treba da podrži rad sa prijavljenim korisnicima (kupci, prodavci i administratori) i sa neprijavljenim korisnicima. Svaki od korisnika ima tačno određene akcije koje sme da obavlja.
Potrebno je kreirati sledeće REST endpoint-e:

o putanja /project/bills/findNonExpiredVoucher
o metoda je rezervisana samo za administratora
