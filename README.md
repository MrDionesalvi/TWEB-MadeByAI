# BiblioShare - Piattaforma di Condivisione Libri

**Progetto TWEB - Tecnologie Web**

BiblioShare è un'applicazione web full-stack per la condivisione di libri tra membri di una comunità. L'applicazione permette agli utenti di catalogare la propria biblioteca personale, richiedere libri in prestito, e gestire le richieste di prestito.

## 📚 Ambito Benefit: Apprendimento Continuativo, Conoscenza e Cultura

L'applicazione promuove:
- Accesso democratico alla cultura attraverso la condivisione
- Sostenibilità ambientale riducendo gli sprechi
- Creazione di comunità di lettori
- Valorizzazione delle biblioteche private

## 🏗️ Architettura

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **Database**: H2 (in-memory)
- **ORM**: JPA/Hibernate
- **API**: RESTful con JSON

### Frontend (React + TypeScript)
- **Framework**: React 18
- **Language**: TypeScript
- **Build Tool**: Vite
- **Styling**: CSS puro

## 📋 Requisiti Implementati

### Backend
✅ **Entità**: 6 entità (User, Book, Genre, LoanRequest, Review, ReadingGroup)  
✅ **Repository**: 6 repository JPA  
✅ **Service**: 3 servizi (AuthService, BookService, LoanService)  
✅ **Controller**: 3 controller REST (AuthController, BookController, LoanController)  
✅ **Route**: 12+ endpoint API  
✅ **Autenticazione**: Login/logout con sessioni HTTP  
✅ **Ruoli**: 2 ruoli utente (USER, ADMIN)  

### Frontend
✅ **Componenti**: 9 componenti React  
✅ **TypeScript**: Tutti i componenti in TypeScript  
✅ **Forms POST**: 2 form (Login, Aggiungi Libro)  
✅ **Single Page App**: Navigazione senza reload  
✅ **Comunicazione Componenti**: Gestione stato condiviso tramite App component  
✅ **Comunicazione Asincrona**: Fetch API per chiamate al backend  
✅ **Gestione Errori**: Validazione e messaggi di errore  

## 🚀 Setup e Avvio

### Prerequisiti
- Java 17 o superiore
- Maven 3.6+
- Node.js 18+ e npm
- Un browser moderno

### 1. Avvio Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Il backend sarà disponibile su `http://localhost:8080`

### 2. Avvio Frontend

In un nuovo terminale:

```bash
cd frontend
npm install
npm run dev
```

Il frontend sarà disponibile su `http://localhost:5173`

### 3. Accesso all'Applicazione

Apri il browser su `http://localhost:5173`

**Utenti Demo:**
- **Utente 1**: mario / password123 (Ruolo: USER)
- **Utente 2**: anna / password123 (Ruolo: USER)
- **Admin**: admin / admin123 (Ruolo: ADMIN)

## 🎯 Funzionalità Principali

### Per Tutti gli Utenti
1. **Login/Logout**: Autenticazione con username e password
2. **Ricerca Libri**: Cerca per titolo o autore tra i libri disponibili
3. **Visualizza Dettagli**: Vedi informazioni complete su ogni libro
4. **Richiedi Prestito**: Richiedi in prestito libri disponibili

### Per Proprietari di Libri
1. **Aggiungi Libro**: Cataloga i tuoi libri nella biblioteca personale
2. **Gestisci Biblioteca**: Visualizza ed elimina i tuoi libri
3. **Gestisci Richieste**: Approva o rifiuta richieste di prestito
4. **Completa Prestiti**: Segna prestiti come completati quando il libro viene restituito

### Per Amministratori
1. **Tutte le funzionalità utente**
2. **Elimina Qualsiasi Libro**: Può rimuovere libri da qualsiasi biblioteca

## 📁 Struttura del Progetto

```
BiblioShare/
├── backend/                    # Spring Boot Backend
│   ├── src/main/java/com/biblioshare/
│   │   ├── entity/            # Entità JPA (6 entities)
│   │   ├── repository/        # Repository JPA (6 repositories)
│   │   ├── service/           # Business logic (3 services)
│   │   ├── controller/        # REST Controllers (3 controllers)
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── config/            # Configurazione e inizializzazione
│   │   └── BiblioShareApplication.java
│   └── pom.xml
│
├── frontend/                   # React + TypeScript Frontend
│   ├── src/
│   │   ├── components/        # React Components (9 components)
│   │   ├── services/          # API service layer
│   │   ├── types/             # TypeScript type definitions
│   │   ├── App.tsx            # Main App component
│   │   └── main.tsx
│   └── package.json
│
└── docs/                       # Documentazione
    ├── Visione.md             # Documento di visione
    ├── Ideazione.md           # Documento di ideazione
    └── Considerazioni.md      # Considerazioni conclusive
```

## 🔧 Tecnologie Utilizzate

### Backend
- Spring Boot 3.1.5
- Spring Data JPA
- Spring Web (REST)
- H2 Database
- Lombok
- Jakarta Validation

### Frontend
- React 18
- TypeScript
- Vite
- CSS3
- Fetch API

## 📊 Modello Dati

### Entità Principali

**User**
- Informazioni utente (username, email, nome, città)
- Ruolo (USER o ADMIN)
- Relazioni: libri posseduti, richieste prestito, recensioni

**Book**
- Dettagli libro (titolo, autore, ISBN, genere)
- Stato (disponibile/non disponibile)
- Condizione fisica
- Relazioni: proprietario, richieste prestito, recensioni

**LoanRequest**
- Stato richiesta (PENDING, APPROVED, REJECTED, COMPLETED)
- Date (richiesta, approvazione, restituzione)
- Note
- Relazioni: libro, richiedente

**Genre**
- Categorie libri (Fiction, Non-Fiction, Sci-Fi, Mystery, Biography)

**Review**
- Valutazione (1-5 stelle)
- Commento
- Data recensione

**ReadingGroup**
- Gruppi di lettura
- Membri e creatore
- Libro da leggere

## 🌐 API Endpoints

### Autenticazione
- `POST /api/auth/login` - Login utente
- `POST /api/auth/logout` - Logout
- `GET /api/auth/current` - Utente corrente

### Libri
- `GET /api/books` - Tutti i libri
- `GET /api/books/available` - Libri disponibili
- `GET /api/books/search/title/{title}` - Cerca per titolo
- `GET /api/books/search/author/{author}` - Cerca per autore
- `GET /api/books/user/{userId}` - Libri di un utente
- `GET /api/books/{id}` - Dettagli libro
- `POST /api/books` - Aggiungi libro
- `PUT /api/books/{id}` - Modifica libro
- `DELETE /api/books/{id}` - Elimina libro

### Prestiti
- `POST /api/loans` - Crea richiesta prestito
- `GET /api/loans/my-requests` - Le mie richieste
- `GET /api/loans/received` - Richieste ricevute
- `PUT /api/loans/{id}/approve` - Approva richiesta
- `PUT /api/loans/{id}/reject` - Rifiuta richiesta
- `PUT /api/loans/{id}/complete` - Completa prestito

## 🎨 Componenti React

1. **App** - Componente principale con gestione stato
2. **Header** - Navigazione e informazioni utente
3. **LoginForm** - Form di autenticazione (POST)
4. **BookList** - Lista di libri
5. **BookCard** - Card singolo libro
6. **BookForm** - Form aggiunta libro (POST)
7. **BookSearch** - Ricerca libri
8. **LoanRequestList** - Lista richieste prestito
9. **LoanRequestCard** - Card singola richiesta

## 🔐 Sicurezza

⚠️ **Nota**: Questa è un'applicazione didattica con semplificazioni per scopi educativi:

- Le password sono salvate in chiaro (in produzione usare BCrypt)
- Sessioni HTTP semplici (in produzione usare JWT)
- CORS permissivo per sviluppo locale
- Nessuna validazione avanzata lato server

Per un ambiente di produzione, implementare:
- Password hashing (BCrypt)
- HTTPS obbligatorio
- CSRF protection
- Rate limiting
- Input validation rigorosa

## 📝 Dati di Test

Il database viene inizializzato automaticamente con:
- 3 utenti (mario, anna, admin)
- 5 generi letterari
- 6 libri di esempio
- 3 recensioni
- 1 richiesta di prestito pendente

## 🧪 Testing

### Test Backend
```bash
cd backend
mvn test
```

### Test Frontend
```bash
cd frontend
npm test
```

### Build Production
```bash
# Backend
cd backend
mvn clean package

# Frontend
cd frontend
npm run build
```

## 📚 Documentazione Aggiuntiva

Consulta la cartella `docs/` per:
- **Visione.md**: Documento di visione del progetto
- **Ideazione.md**: Analisi scenari e casi d'uso
- **Considerazioni.md**: Considerazioni conclusive e miglioramenti futuri

## 🤝 Contributi

Progetto sviluppato per il corso di Tecnologie Web.

## 📄 Licenza

Questo progetto è stato creato per scopi educativi.

---

**Buona lettura con BiblioShare! 📚**
