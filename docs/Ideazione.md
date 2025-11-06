# Documento di Ideazione - BiblioShare

## Scenari di Utilizzo

### Scenario 1: Prestito di Libri
Un utente desidera leggere un libro ma non vuole acquistarlo. Utilizza l'app da smartphone o computer per cercare il libro nella sua area geografica e richiederlo in prestito.

### Scenario 2: Gestione Biblioteca Personale
Un utente ha molti libri a casa che legge raramente. Usa l'app da computer o tablet per catalogare la propria collezione e renderla disponibile per il prestito.

### Scenario 3: Gruppi di Lettura
Un gruppo di amici o conoscenti vuole organizzare un gruppo di lettura. Utilizzano l'app da qualsiasi dispositivo per creare il gruppo, scegliere il libro da leggere insieme e coordinare gli incontri.

## Attori Principali

### 1. Lettore (User)
- Persona che desidera leggere libri e partecipare alla comunità
- Può prestare e prendere in prestito libri
- Può partecipare ai gruppi di lettura

### 2. Amministratore (Admin)
- Gestisce la piattaforma
- Modera le recensioni e i contenuti
- Risolve dispute sui prestiti

## Casi d'Uso

### CU1: Registrazione e Login
**Scenario**: Tutti
**Attore**: Lettore, Amministratore
**Obiettivo**: Accedere alle funzionalità dell'applicazione

**Procedimento**:
1. L'utente accede alla pagina di login
2. Inserisce username e password
3. Il sistema verifica le credenziali
4. L'utente accede alla dashboard personalizzata

### CU2: Aggiunta Libro alla Propria Biblioteca
**Scenario**: Gestione Biblioteca Personale
**Attore**: Lettore
**Obiettivo**: Rendere disponibile un proprio libro per il prestito

**Procedimento**:
1. L'utente accede alla sezione "La mia biblioteca"
2. Clicca su "Aggiungi libro"
3. Inserisce i dati del libro (titolo, autore, ISBN, genere, condizione)
4. Indica se è disponibile per il prestito
5. Il sistema salva il libro e lo rende visibile nella ricerca

### CU3: Ricerca e Richiesta Prestito
**Scenario**: Prestito di Libri
**Attore**: Lettore
**Obiettivo**: Trovare e richiedere un libro in prestito

**Procedimento**:
1. L'utente cerca un libro tramite titolo, autore o genere
2. Visualizza i risultati con informazioni su disponibilità e distanza
3. Seleziona un libro disponibile
4. Richiede il prestito al proprietario
5. Il sistema notifica il proprietario
6. Il proprietario accetta o rifiuta la richiesta

### CU4: Gestione Richieste di Prestito
**Scenario**: Prestito di Libri
**Attore**: Lettore (proprietario)
**Obiettivo**: Gestire le richieste ricevute per i propri libri

**Procedimento**:
1. L'utente riceve una notifica di richiesta prestito
2. Visualizza i dettagli della richiesta (chi, quale libro, quando)
3. Accetta o rifiuta la richiesta
4. Se accetta, il sistema marca il libro come "in prestito"
5. Gli utenti si accordano per la consegna

### CU5: Creazione Gruppo di Lettura
**Scenario**: Gruppi di Lettura
**Attore**: Lettore
**Obiettivo**: Creare un gruppo per leggere insieme un libro

**Procedimento**:
1. L'utente accede alla sezione "Gruppi di lettura"
2. Clicca su "Crea nuovo gruppo"
3. Inserisce nome, descrizione, libro da leggere, date degli incontri
4. Il sistema crea il gruppo e lo rende visibile
5. Altri utenti possono richiedere di partecipare

### CU6: Partecipazione a Gruppo di Lettura
**Scenario**: Gruppi di Lettura
**Attore**: Lettore
**Obiettivo**: Unirsi a un gruppo di lettura esistente

**Procedimento**:
1. L'utente naviga la lista dei gruppi di lettura
2. Trova un gruppo di interesse
3. Richiede di partecipare
4. Il creatore del gruppo approva la richiesta
5. L'utente può vedere i dettagli e partecipare alle discussioni

### CU7: Recensione Libro
**Scenario**: Tutti
**Attore**: Lettore
**Obiettivo**: Condividere la propria opinione su un libro letto

**Procedimento**:
1. L'utente cerca il libro nella piattaforma
2. Clicca su "Scrivi recensione"
3. Inserisce voto (1-5 stelle) e commento
4. Il sistema salva la recensione e la rende visibile

### CU8: Visualizzazione Storico Prestiti
**Scenario**: Tutti
**Attore**: Lettore
**Obiettivo**: Tenere traccia dei libri presi/dati in prestito

**Procedimento**:
1. L'utente accede alla sezione "I miei prestiti"
2. Visualizza due liste: libri prestati e libri ricevuti
3. Può vedere lo stato (attivo, completato, scaduto)
4. Può segnare un prestito come completato

### CU9: Moderazione Contenuti
**Scenario**: Tutti
**Attore**: Amministratore
**Obiettivo**: Mantenere la qualità della piattaforma

**Procedimento**:
1. L'amministratore accede al pannello di amministrazione
2. Visualizza recensioni e segnalazioni
3. Può rimuovere contenuti inappropriati
4. Può bloccare utenti problematici

## Casi d'Uso Selezionati per l'Implementazione

Per il proof-of-concept, implementeremo:

1. **CU2: Aggiunta Libro alla Propria Biblioteca** - Caso d'uso fondamentale che dimostra l'interazione con il database e la gestione dei form
2. **CU3: Ricerca e Richiesta Prestito** - Caso d'uso complesso che coinvolge ricerca, visualizzazione risultati, e comunicazione tra componenti

Questi due casi d'uso permettono di dimostrare:
- Autenticazione e gestione utenti
- CRUD operations su entità principali (Libri, Prestiti)
- Interazione complessa tra componenti React
- Comunicazione asincrona client-server
- Gestione di relazioni tra entità nel database
