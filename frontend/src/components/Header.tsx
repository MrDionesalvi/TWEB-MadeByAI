import React from 'react';
import type { User } from '../types';
import './Header.css';

type Page = 'available' | 'mylibrary' | 'loans';

interface HeaderProps {
  user: User | null;
  onLogout: () => void;
  currentPage: Page;
  onNavigate: (page: Page) => void;
}

const Header: React.FC<HeaderProps> = ({ user, onLogout, currentPage, onNavigate }) => {
  return (
    <header className="header">
      <div className="header-content">
        <h1 className="logo">📚 BiblioShare</h1>
        
        {user && (
          <nav className="navigation">
            <button
              className={currentPage === 'available' ? 'nav-button active' : 'nav-button'}
              onClick={() => onNavigate('available')}
            >
              Libri Disponibili
            </button>
            <button
              className={currentPage === 'mylibrary' ? 'nav-button active' : 'nav-button'}
              onClick={() => onNavigate('mylibrary')}
            >
              La Mia Biblioteca
            </button>
            <button
              className={currentPage === 'loans' ? 'nav-button active' : 'nav-button'}
              onClick={() => onNavigate('loans')}
            >
              Prestiti
            </button>
          </nav>
        )}
        
        {user && (
          <div className="user-info">
            <span className="user-name">{user.fullName} ({user.role})</span>
            <button className="logout-button" onClick={onLogout}>Logout</button>
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;
