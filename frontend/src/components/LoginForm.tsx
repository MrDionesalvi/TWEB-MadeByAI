import React, { useState } from 'react';
import { authService } from '../services/api';
import type { LoginRequest, User } from '../types';
import './LoginForm.css';

interface LoginFormProps {
  onLoginSuccess: (user: User) => void;
}

const LoginForm: React.FC<LoginFormProps> = ({ onLoginSuccess }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    const request: LoginRequest = { username, password };

    try {
      const user = await authService.login(request);
      onLoginSuccess(user);
    } catch (err) {
      setError('Credenziali non valide. Riprova.');
    }
  };

  return (
    <div className="login-form-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Accedi a BiblioShare</h2>
        
        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            id="username"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        {error && <div className="error-message">{error}</div>}

        <button type="submit" className="submit-button">Accedi</button>

        <div className="demo-users">
          <p>Utenti demo:</p>
          <p>mario / password123 (Utente)</p>
          <p>anna / password123 (Utente)</p>
          <p>admin / admin123 (Admin)</p>
        </div>
      </form>
    </div>
  );
};

export default LoginForm;
