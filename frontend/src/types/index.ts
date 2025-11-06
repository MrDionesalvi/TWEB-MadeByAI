export interface User {
  id: number;
  username: string;
  email: string;
  fullName: string;
  city: string;
  address: string;
  role: 'USER' | 'ADMIN';
}

export interface Book {
  id: number;
  title: string;
  author: string;
  isbn: string;
  genreName: string | null;
  description: string;
  condition: 'NEW' | 'LIKE_NEW' | 'GOOD' | 'ACCEPTABLE' | 'POOR';
  availableForLoan: boolean;
  owner: User;
  averageRating: number;
  reviewCount: number;
}

export interface BookRequest {
  title: string;
  author: string;
  isbn: string;
  genreId: number | null;
  description: string;
  condition: 'NEW' | 'LIKE_NEW' | 'GOOD' | 'ACCEPTABLE' | 'POOR';
  availableForLoan: boolean;
}

export interface LoanRequest {
  bookId: number;
  notes: string;
}

export interface LoanResponse {
  id: number;
  book: Book;
  borrower: User;
  status: 'PENDING' | 'APPROVED' | 'REJECTED' | 'ACTIVE' | 'COMPLETED' | 'CANCELLED';
  requestDate: string;
  approvalDate: string | null;
  returnDate: string | null;
  notes: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}
