import type { User, Book, BookRequest, LoanRequest, LoanResponse, LoginRequest } from '../types';

const API_BASE_URL = 'http://localhost:8080/api';

export const authService = {
  async login(request: LoginRequest): Promise<User> {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(request),
    });

    if (!response.ok) {
      throw new Error('Login failed');
    }

    return response.json();
  },

  async logout(): Promise<void> {
    await fetch(`${API_BASE_URL}/auth/logout`, {
      method: 'POST',
      credentials: 'include',
    });
  },

  async getCurrentUser(): Promise<User> {
    const response = await fetch(`${API_BASE_URL}/auth/current`, {
      credentials: 'include',
    });

    if (!response.ok) {
      throw new Error('Not authenticated');
    }

    return response.json();
  },
};

export const bookService = {
  async getAllBooks(): Promise<Book[]> {
    const response = await fetch(`${API_BASE_URL}/books`, {
      credentials: 'include',
    });
    return response.json();
  },

  async getAvailableBooks(): Promise<Book[]> {
    const response = await fetch(`${API_BASE_URL}/books/available`, {
      credentials: 'include',
    });
    return response.json();
  },

  async searchByTitle(title: string): Promise<Book[]> {
    const response = await fetch(`${API_BASE_URL}/books/search/title/${encodeURIComponent(title)}`, {
      credentials: 'include',
    });
    return response.json();
  },

  async searchByAuthor(author: string): Promise<Book[]> {
    const response = await fetch(`${API_BASE_URL}/books/search/author/${encodeURIComponent(author)}`, {
      credentials: 'include',
    });
    return response.json();
  },

  async getUserBooks(userId: number): Promise<Book[]> {
    const response = await fetch(`${API_BASE_URL}/books/user/${userId}`, {
      credentials: 'include',
    });
    return response.json();
  },

  async getBookById(id: number): Promise<Book> {
    const response = await fetch(`${API_BASE_URL}/books/${id}`, {
      credentials: 'include',
    });
    
    if (!response.ok) {
      throw new Error('Book not found');
    }
    
    return response.json();
  },

  async createBook(request: BookRequest): Promise<Book> {
    const response = await fetch(`${API_BASE_URL}/books`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(request),
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error);
    }

    return response.json();
  },

  async updateBook(id: number, request: BookRequest): Promise<Book> {
    const response = await fetch(`${API_BASE_URL}/books/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(request),
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error);
    }

    return response.json();
  },

  async deleteBook(id: number): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/books/${id}`, {
      method: 'DELETE',
      credentials: 'include',
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error);
    }
  },
};

export const loanService = {
  async createLoanRequest(request: LoanRequest): Promise<LoanResponse> {
    const response = await fetch(`${API_BASE_URL}/loans`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(request),
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error);
    }

    return response.json();
  },

  async getMyLoanRequests(): Promise<LoanResponse[]> {
    const response = await fetch(`${API_BASE_URL}/loans/my-requests`, {
      credentials: 'include',
    });

    if (!response.ok) {
      throw new Error('Failed to fetch loan requests');
    }

    return response.json();
  },

  async getReceivedLoanRequests(): Promise<LoanResponse[]> {
    const response = await fetch(`${API_BASE_URL}/loans/received`, {
      credentials: 'include',
    });

    if (!response.ok) {
      throw new Error('Failed to fetch received loan requests');
    }

    return response.json();
  },

  async approveLoanRequest(id: number): Promise<LoanResponse> {
    const response = await fetch(`${API_BASE_URL}/loans/${id}/approve`, {
      method: 'PUT',
      credentials: 'include',
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error);
    }

    return response.json();
  },

  async rejectLoanRequest(id: number): Promise<LoanResponse> {
    const response = await fetch(`${API_BASE_URL}/loans/${id}/reject`, {
      method: 'PUT',
      credentials: 'include',
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error);
    }

    return response.json();
  },

  async completeLoanRequest(id: number): Promise<LoanResponse> {
    const response = await fetch(`${API_BASE_URL}/loans/${id}/complete`, {
      method: 'PUT',
      credentials: 'include',
    });

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error);
    }

    return response.json();
  },
};
