import React from 'react';
import type { Book } from '../types';
import BookCard from './BookCard';
import './BookList.css';

interface BookListProps {
  books: Book[];
  onRequestLoan?: (bookId: number) => void;
  onDelete?: (bookId: number) => void;
  showOwner?: boolean;
  currentUserId?: number;
  isAdmin?: boolean;
}

const BookList: React.FC<BookListProps> = ({ 
  books, 
  onRequestLoan, 
  onDelete,
  showOwner = true,
  currentUserId,
  isAdmin = false
}) => {
  if (books.length === 0) {
    return (
      <div className="empty-state">
        <p>Nessun libro trovato.</p>
      </div>
    );
  }

  return (
    <div className="book-list">
      {books.map(book => (
        <BookCard
          key={book.id}
          book={book}
          onRequestLoan={onRequestLoan}
          onDelete={onDelete}
          showOwner={showOwner}
          isOwner={currentUserId === book.owner.id}
          isAdmin={isAdmin}
        />
      ))}
    </div>
  );
};

export default BookList;
