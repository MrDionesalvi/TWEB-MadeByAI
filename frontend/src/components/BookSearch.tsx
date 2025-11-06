import React, { useState } from 'react';
import './BookSearch.css';

interface BookSearchProps {
  onSearch: (query: string, type: 'title' | 'author') => void;
  onReset: () => void;
}

const BookSearch: React.FC<BookSearchProps> = ({ onSearch, onReset }) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [searchType, setSearchType] = useState<'title' | 'author'>('title');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      onSearch(searchQuery.trim(), searchType);
    }
  };

  const handleReset = () => {
    setSearchQuery('');
    onReset();
  };

  return (
    <div className="book-search">
      <form onSubmit={handleSubmit} className="search-form">
        <div className="search-type">
          <label>
            <input
              type="radio"
              value="title"
              checked={searchType === 'title'}
              onChange={(e) => setSearchType(e.target.value as 'title')}
            />
            Titolo
          </label>
          <label>
            <input
              type="radio"
              value="author"
              checked={searchType === 'author'}
              onChange={(e) => setSearchType(e.target.value as 'author')}
            />
            Autore
          </label>
        </div>

        <div className="search-input-group">
          <input
            type="text"
            placeholder={`Cerca per ${searchType === 'title' ? 'titolo' : 'autore'}...`}
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="search-input"
          />
          <button type="submit" className="search-button">
            🔍 Cerca
          </button>
          <button type="button" onClick={handleReset} className="reset-button">
            ✕ Reset
          </button>
        </div>
      </form>
    </div>
  );
};

export default BookSearch;
