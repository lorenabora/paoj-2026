1.1. Lista cu cel putin 10 interogari / actiuni posibile in sistem
- Creare programare (input: client, medic, data, ora; se va verifica disponibilitatea datei)
- Verificare disponibilitate medic (creez exceptie in cazul in care un interval selectat este invalid pentru un doctor)
- Listarea tuturor medicilor spitalului (pot adauga filtre dupa specializare, tip de medic)
- Afisare istoric programari (sortare dupa data)
- Anulare programare ( schimb statusul in ANULATA si sterg programarea)
- Reprogramare (schimb data/ora si verific disponibilitatea)
- Calculare detalii plata ( daca clientul are abonament aplic o reducere, daca nu, pret total)
- Afisare rezultate analize ( lista din clasa Analiza)
- Exportare istoric medical (simulare prin afisarea in consola a unui mesaj)
- Afisare cont cleint (afisare a detaliilor unui client, de la detalii personale pana la istoricul medical)
- Afisare orar medic ( selectez un medic si afisez in consola intervalele in care este disponibil in mod obisnuit la spital)
- Calculare oferte/reduceri ( in functie de tipul de abonament)

1.2. Lista cu cel putin 8 tipuri de obiecte din domeniu (cabinet medical)
- Clase: User(superclasa), Client, Medic, MedicSpecialist, Programare, Abonament, Analiza, Consultatie, CodProgramare
- Enum: TipMedic
- Servicii: ServiciuProgramari, ServiciuClienti, ServiciuMedici
- Exceptii: DataIndisponibilaException, MedicIndisponibilException
- Interfata: IPlata