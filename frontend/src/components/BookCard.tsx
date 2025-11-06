import React from 'react';
import type { Book } from '../types';
import './BookCard.css';

interface BookCardProps {
  book: Book;
  onRequestLoan?: (bookId: number) => void;
  onDelete?: (bookId: number) => void;
  showOwner?: boolean;
  isOwner?: boolean;
  isAdmin?: boolean;
}

const BookCard: React.FC<BookCardProps> = ({ 
  book, 
  onRequestLoan, 
  onDelete,
  showOwner = true,
  isOwner = false,
  isAdmin = false
}) => {
  const conditionLabels = {
    NEW: 'Nuovo',
    LIKE_NEW: 'Come Nuovo',
    GOOD: 'Buono',
    ACCEPTABLE: 'Accettabile',
    POOR: 'Rovinato'
  };

  return (
    <div className="book-card">
      <h3 className="book-title">{book.title}</h3>
      <p className="book-author">di {book.author}</p>
      
      {book.genreName && <p className="book-genre">📖 {book.genreName}</p>}
      
      {book.description && (
        <p className="book-description">{book.description}</p>
      )}
      
      <div className="book-details">
        <span className="book-condition">
          Condizione: {conditionLabels[book.condition]}
        </span>
        
        {book.reviewCount > 0 && (
          <span className="book-rating">
            ⭐ {book.averageRating.toFixed(1)} ({book.reviewCount} recensioni)
          </span>
        )}
      </div>
      
      {showOwner && (
        <p className="book-owner">
          Proprietario: {book.owner.fullName} - {book.owner.city}
        </p>
      )}
      
      <div className="book-actions">
        {book.availableForLoan ? (
          <>
            <span className="availability available">✓ Disponibile</span>
            {onRequestLoan && !isOwner && (
              <button 
                className="action-button primary"
                onClick={() => onRequestLoan(book.id)}
              >
                Richiedi Prestito
              </button>
            )}
          </>
        ) : (
          <span className="availability unavailable">✗ Non Disponibile</span>
        )}
        
        {(isOwner || isAdmin) && onDelete && (
          <button 
            className="action-button danger"
            onClick={() => onDelete(book.id)}
          >
            Elimina
          </button>
        )}
      </div>
    </div>
  );
};

export default BookCard;
