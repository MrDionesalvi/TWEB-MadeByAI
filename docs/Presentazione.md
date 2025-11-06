# BiblioShare - Presentazione Progetto TWEB

## Slide 1: Introduzione
**Titolo:** BiblioShare - Piattaforma di Condivisione Libri

**Contenuto:**
- Applicazione web full-stack per la condivisione di libri
- Ambito Benefit: Apprendimento Continuativo, Conoscenza e Cultura
- Tecnologie: Spring Boot + React TypeScript

## Slide 2: Visione del Progetto
**Obiettivo:** Democratizzare l'accesso alla cultura attraverso la condivisione di libri

**Benefici:**
- 📚 Accesso gratuito a libri tramite prestito tra privati
- 🌱 Sostenibilità: riduzione sprechi e riutilizzo risorse
- 👥 Creazione di comunità locali di lettori
- 🔄 Valorizzazione delle biblioteche private

## Slide 3: Architettura Tecnica

### Backend (Spring Boot)
```
- Spring Boot 3.1.5
- Database: H2 in-memory
- 6 Entità JPA
- 6 Repository
- 3 Service Layer
- 3 REST Controller
- 12+ API Endpoints
```

### Frontend (React + TypeScript)
```
- React 18 con TypeScript
- Build tool: Vite
- 9 Componenti
- 2 Form POST
- Single Page Application
- Comunicazione asincrona con fetch API
```

## Slide 4: Modello Dati

**Entità Principali:**
1. **User** - Utenti (USER/ADMIN)
2. **Book** - Catalogo libri
3. **Genre** - Categorie
4. **LoanRequest** - Richieste prestito
5. **Review** - Recensioni
6. **ReadingGroup** - Gruppi di lettura

**Relazioni:**
- One-to-Many: User → Books, Book → Reviews
- Many-to-One: Book → Genre, LoanRequest → Book/User
- Many-to-Many: ReadingGroup ↔ Users

## Slide 5: Funzionalità Implementate

### Casi d'Uso Principali
✅ **CU2: Aggiunta Libro alla Biblioteca**
- Form POST per aggiungere libri
- Validazione input
- Salvataggio nel database

✅ **CU3: Ricerca e Richiesta Prestito**
- Ricerca per titolo/autore
- Visualizzazione libri disponibili
- Richiesta prestito con note

### Funzionalità Aggiuntive
- Autenticazione con sessioni HTTP
- Gestione ruoli (USER/ADMIN)
- Approvazione/rifiuto richieste
- Completamento prestiti

## Slide 6: Componenti React

**9 Componenti implementati:**
1. **App** - Gestione stato globale e routing
2. **Header** - Navigazione
3. **LoginForm** - Autenticazione (POST)
4. **BookCard** - Visualizzazione libro
5. **BookList** - Lista libri
6. **BookForm** - Aggiunta libro (POST)
7. **BookSearch** - Ricerca
8. **LoanRequestCard** - Card prestito
9. **LoanRequestList** - Lista prestiti

**Comunicazione tra componenti:**
- State lifting per comunicazione sibling
- Props drilling controllato
- Gestione eventi asincroni

## Slide 7: API REST Endpoints

### Autenticazione
- `POST /api/auth/login`
- `POST /api/auth/logout`
- `GET /api/auth/current`

### Gestione Libri
- `GET /api/books/available`
- `GET /api/books/search/title/{title}`
- `POST /api/books`
- `DELETE /api/books/{id}`

### Gestione Prestiti
- `POST /api/loans`
- `GET /api/loans/my-requests`
- `PUT /api/loans/{id}/approve`
- `PUT /api/loans/{id}/complete`

## Slide 8: Demo Flow

**Scenario Tipico:**
1. Login come mario (USER)
2. Ricerca libro "Sapiens"
3. Richiesta prestito con note
4. Logout e login come anna (proprietaria)
5. Visualizzazione richieste ricevute
6. Approvazione prestito
7. (Dopo lettura) Completamento prestito

## Slide 9: Aspetti Implementativi Notevoli

### Backend
- **Repository Pattern**: Separazione logica dati
- **DTO Pattern**: Evita esposizione entità interne
- **Service Layer**: Business logic separata da controller
- **Data Initialization**: Dati di test automatici

### Frontend
- **Type Safety**: TypeScript per ridurre errori
- **Component Reusability**: BookCard usato in più contesti
- **Error Handling**: Gestione errori user-friendly
- **Responsive Design**: CSS Grid e Flexbox

## Slide 10: Sfide e Soluzioni

### Sfide Tecniche
❌ **CORS con credenziali**: Risolto con `allowCredentials: true`
❌ **TypeScript imports**: Risolto con `type` imports
❌ **Session management**: Configurazione corretta cookie

### Scelte Architetturali
✅ **Sessioni HTTP** invece di JWT (semplicità)
✅ **H2 in-memory** per sviluppo rapido
✅ **CSS puro** per controllo totale styling

## Slide 11: Miglioramenti Futuri

### Sicurezza
- Password hashing con BCrypt
- CSRF protection
- Rate limiting
- Input validation rigorosa

### Funzionalità
- Notifiche real-time (WebSocket)
- Geolocalizzazione per distanza
- Sistema recensioni completo
- Gruppi di lettura interattivi

### Scalabilità
- Database persistente (PostgreSQL)
- Caching (Redis)
- CDN per assets statici
- Microservizi architecture

## Slide 12: Accessibilità e Inclusività

### Accessibilità
- ✅ HTML semantico
- ✅ Label associate a input
- ✅ Contrasti colori adeguati
- ⚠️ Da migliorare: keyboard navigation, ARIA labels

### Inclusività Sociale
- ✅ Democratizza accesso alla cultura
- ✅ Promuove comunità locale
- ✅ Interfaccia intuitiva
- 💡 Futuro: i18n per altre lingue

## Slide 13: Requisiti Soddisfatti

### Backend (100%)
✅ 6+ Entità JPA  
✅ 6+ Repository  
✅ 3+ Service (escluso auth)  
✅ 3+ Controller (escluso auth)  
✅ 12+ Route API  
✅ 2+ Ruoli utente  

### Frontend (100%)
✅ TypeScript  
✅ 8+ Componenti React  
✅ 2+ Form POST  
✅ Single Page Application  
✅ Comunicazione componenti  
✅ Gestione autenticazione  
✅ Differenziazione per ruolo  

## Slide 14: Conclusioni

### Punti di Forza
- ✨ **Applicazione completa e funzionante**
- 🎯 **Tutti i requisiti soddisfatti**
- 📚 **Impatto sociale positivo**
- 🔧 **Architettura solida ed estendibile**
- 💻 **Codice pulito e ben organizzato**

### Lezioni Apprese
- Importanza della separazione dei compiti
- Type safety riduce debugging
- Component-based architecture scalabile
- API RESTful ben progettate facilitano frontend

## Slide 15: Q&A

**Demo Live disponibile su:**
- Backend: `http://localhost:8080`
- Frontend: `http://localhost:5173`

**Utenti Demo:**
- mario / password123
- anna / password123
- admin / admin123

**Repository:** github.com/MrDionesalvi/TWEB-MadeByAI

---

## Note per la Presentazione

### Setup Prima della Demo
```bash
# Terminale 1 - Backend
cd backend
mvn spring-boot:run

# Terminale 2 - Frontend
cd frontend
npm run dev
```

### Punti da Enfatizzare
1. Beneficio sociale dell'applicazione
2. Completezza implementativa (tutti i requisiti)
3. Qualità del codice e architettura
4. Potenziale di estensione futura

### Demo Flow Suggerito
1. Login e navigazione
2. Ricerca libri
3. Richiesta prestito
4. Cambio utente
5. Gestione richiesta
6. Aggiunta nuovo libro

### Tempo Stimato
- Introduzione: 2 min
- Architettura: 3 min
- Demo: 5 min
- Conclusioni: 2 min
- Q&A: 3 min
**Totale: ~15 minuti**
