import React, { useState } from 'react';
import type { BookRequest } from '../types';
import './BookForm.css';

interface BookFormProps {
  onSubmit: (book: BookRequest) => Promise<void>;
  onCancel: () => void;
}

const BookForm: React.FC<BookFormProps> = ({ onSubmit, onCancel }) => {
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [isbn, setIsbn] = useState('');
  const [description, setDescription] = useState('');
  const [condition, setCondition] = useState<'NEW' | 'LIKE_NEW' | 'GOOD' | 'ACCEPTABLE' | 'POOR'>('GOOD');
  const [availableForLoan, setAvailableForLoan] = useState(true);
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    const bookRequest: BookRequest = {
      title,
      author,
      isbn,
      genreId: null, // Simplified for now
      description,
      condition,
      availableForLoan,
    };

    try {
      await onSubmit(bookRequest);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Errore durante il salvataggio del libro');
    }
  };

  return (
    <div className="book-form-container">
      <form className="book-form" onSubmit={handleSubmit}>
        <h2>Aggiungi un Nuovo Libro</h2>

        <div className="form-group">
          <label htmlFor="title">Titolo *</label>
          <input
            id="title"
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="author">Autore *</label>
          <input
            id="author"
            type="text"
            value={author}
            onChange={(e) => setAuthor(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="isbn">ISBN</label>
          <input
            id="isbn"
            type="text"
            value={isbn}
            onChange={(e) => setIsbn(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label htmlFor="description">Descrizione</label>
          <textarea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows={4}
          />
        </div>

        <div className="form-group">
          <label htmlFor="condition">Condizione *</label>
          <select
            id="condition"
            value={condition}
            onChange={(e) => setCondition(e.target.value as any)}
            required
          >
            <option value="NEW">Nuovo</option>
            <option value="LIKE_NEW">Come Nuovo</option>
            <option value="GOOD">Buono</option>
            <option value="ACCEPTABLE">Accettabile</option>
            <option value="POOR">Rovinato</option>
          </select>
        </div>

        <div className="form-group checkbox">
          <label>
            <input
              type="checkbox"
              checked={availableForLoan}
              onChange={(e) => setAvailableForLoan(e.target.checked)}
            />
            Disponibile per prestito
          </label>
        </div>

        {error && <div className="error-message">{error}</div>}

        <div className="form-actions">
          <button type="button" className="cancel-button" onClick={onCancel}>
            Annulla
          </button>
          <button type="submit" className="submit-button">
            Aggiungi Libro
          </button>
        </div>
      </form>
    </div>
  );
};

export default BookForm;
