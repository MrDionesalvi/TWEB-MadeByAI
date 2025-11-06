import React, { useState, useEffect } from 'react';
import Header from './components/Header';
import LoginForm from './components/LoginForm';
import BookList from './components/BookList';
import BookForm from './components/BookForm';
import BookSearch from './components/BookSearch';
import LoanRequestList from './components/LoanRequestList';
import type { User, Book, BookRequest, LoanResponse, LoanRequest } from './types';
import { authService, bookService, loanService } from './services/api';
import './App.css';

type Page = 'available' | 'mylibrary' | 'loans';

const App: React.FC = () => {
  const [user, setUser] = useState<User | null>(null);
  const [currentPage, setCurrentPage] = useState<Page>('available');
  const [books, setBooks] = useState<Book[]>([]);
  const [myBooks, setMyBooks] = useState<Book[]>([]);
  const [myLoans, setMyLoans] = useState<LoanResponse[]>([]);
  const [receivedLoans, setReceivedLoans] = useState<LoanResponse[]>([]);
  const [showBookForm, setShowBookForm] = useState(false);
  const [loading, setLoading] = useState(true);

  // Check if user is logged in on mount
  useEffect(() => {
    const checkAuth = async () => {
      try {
        const currentUser = await authService.getCurrentUser();
        setUser(currentUser);
      } catch (error) {
        setUser(null);
      } finally {
        setLoading(false);
      }
    };

    checkAuth();
  }, []);

  // Load data when user or page changes
  useEffect(() => {
    if (user) {
      loadPageData();
    }
  }, [user, currentPage]);

  const loadPageData = async () => {
    if (!user) return;

    try {
      if (currentPage === 'available') {
        const availableBooks = await bookService.getAvailableBooks();
        setBooks(availableBooks);
      } else if (currentPage === 'mylibrary') {
        const userBooks = await bookService.getUserBooks(user.id);
        setMyBooks(userBooks);
      } else if (currentPage === 'loans') {
        const [myRequests, received] = await Promise.all([
          loanService.getMyLoanRequests(),
          loanService.getReceivedLoanRequests()
        ]);
        setMyLoans(myRequests);
        setReceivedLoans(received);
      }
    } catch (error) {
      console.error('Error loading data:', error);
    }
  };

  const handleLogin = (loggedInUser: User) => {
    setUser(loggedInUser);
    setCurrentPage('available');
  };

  const handleLogout = async () => {
    await authService.logout();
    setUser(null);
    setBooks([]);
    setMyBooks([]);
    setMyLoans([]);
    setReceivedLoans([]);
  };

  const handleNavigate = (page: Page) => {
    setCurrentPage(page);
    setShowBookForm(false);
  };

  const handleSearch = async (query: string, type: 'title' | 'author') => {
    try {
      const results = type === 'title' 
        ? await bookService.searchByTitle(query)
        : await bookService.searchByAuthor(query);
      setBooks(results);
    } catch (error) {
      console.error('Error searching books:', error);
    }
  };

  const handleResetSearch = async () => {
    try {
      const availableBooks = await bookService.getAvailableBooks();
      setBooks(availableBooks);
    } catch (error) {
      console.error('Error loading books:', error);
    }
  };

  const handleRequestLoan = async (bookId: number) => {
    if (!user) return;

    const notes = prompt('Aggiungi una nota alla richiesta (opzionale):');
    if (notes === null) return; // User cancelled

    try {
      const request: LoanRequest = {
        bookId,
        notes: notes || ''
      };
      await loanService.createLoanRequest(request);
      alert('Richiesta di prestito inviata con successo!');
      loadPageData(); // Refresh data
    } catch (error) {
      alert(error instanceof Error ? error.message : 'Errore durante la richiesta di prestito');
    }
  };

  const handleAddBook = async (bookRequest: BookRequest) => {
    try {
      await bookService.createBook(bookRequest);
      setShowBookForm(false);
      alert('Libro aggiunto con successo!');
      loadPageData(); // Refresh my library
    } catch (error) {
      throw error; // Let the form handle the error
    }
  };

  const handleDeleteBook = async (bookId: number) => {
    if (!confirm('Sei sicuro di voler eliminare questo libro?')) return;

    try {
      await bookService.deleteBook(bookId);
      alert('Libro eliminato con successo!');
      loadPageData(); // Refresh data
    } catch (error) {
      alert(error instanceof Error ? error.message : 'Errore durante l\'eliminazione del libro');
    }
  };

  const handleApproveLoan = async (loanId: number) => {
    try {
      await loanService.approveLoanRequest(loanId);
      alert('Richiesta di prestito approvata!');
      loadPageData(); // Refresh loans
    } catch (error) {
      alert(error instanceof Error ? error.message : 'Errore durante l\'approvazione');
    }
  };

  const handleRejectLoan = async (loanId: number) => {
    try {
      await loanService.rejectLoanRequest(loanId);
      alert('Richiesta di prestito rifiutata.');
      loadPageData(); // Refresh loans
    } catch (error) {
      alert(error instanceof Error ? error.message : 'Errore durante il rifiuto');
    }
  };

  const handleCompleteLoan = async (loanId: number) => {
    try {
      await loanService.completeLoanRequest(loanId);
      alert('Prestito completato!');
      loadPageData(); // Refresh loans
    } catch (error) {
      alert(error instanceof Error ? error.message : 'Errore durante il completamento');
    }
  };

  if (loading) {
    return <div className="loading">Caricamento...</div>;
  }

  if (!user) {
    return (
      <div className="app">
        <Header user={null} onLogout={handleLogout} currentPage={currentPage} onNavigate={handleNavigate} />
        <LoginForm onLoginSuccess={handleLogin} />
      </div>
    );
  }

  return (
    <div className="app">
      <Header user={user} onLogout={handleLogout} currentPage={currentPage} onNavigate={handleNavigate} />
      
      <main className="main-content">
        {currentPage === 'available' && (
          <div>
            <h1>Libri Disponibili per Prestito</h1>
            <BookSearch onSearch={handleSearch} onReset={handleResetSearch} />
            <BookList 
              books={books} 
              onRequestLoan={handleRequestLoan}
              currentUserId={user.id}
              isAdmin={user.role === 'ADMIN'}
            />
          </div>
        )}

        {currentPage === 'mylibrary' && (
          <div>
            <div className="page-header">
              <h1>La Mia Biblioteca</h1>
              {!showBookForm && (
                <button className="primary-button" onClick={() => setShowBookForm(true)}>
                  + Aggiungi Libro
                </button>
              )}
            </div>

            {showBookForm ? (
              <BookForm onSubmit={handleAddBook} onCancel={() => setShowBookForm(false)} />
            ) : (
              <BookList 
                books={myBooks} 
                onDelete={handleDeleteBook}
                showOwner={false}
                currentUserId={user.id}
                isAdmin={user.role === 'ADMIN'}
              />
            )}
          </div>
        )}

        {currentPage === 'loans' && (
          <div>
            <h1>Gestione Prestiti</h1>
            
            <LoanRequestList
              loans={receivedLoans}
              onApprove={handleApproveLoan}
              onReject={handleRejectLoan}
              onComplete={handleCompleteLoan}
              currentUserId={user.id}
              title="Richieste Ricevute"
            />

            <LoanRequestList
              loans={myLoans}
              currentUserId={user.id}
              title="Le Mie Richieste"
            />
          </div>
        )}
      </main>
    </div>
  );
};

export default App;
