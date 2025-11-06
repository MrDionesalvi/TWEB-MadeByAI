import React from 'react';
import type { LoanResponse } from '../types';
import LoanRequestCard from './LoanRequestCard';
import './LoanRequestList.css';

interface LoanRequestListProps {
  loans: LoanResponse[];
  onApprove?: (id: number) => void;
  onReject?: (id: number) => void;
  onComplete?: (id: number) => void;
  currentUserId: number;
  title: string;
}

const LoanRequestList: React.FC<LoanRequestListProps> = ({ 
  loans, 
  onApprove, 
  onReject,
  onComplete,
  currentUserId,
  title
}) => {
  if (loans.length === 0) {
    return (
      <div className="loan-section">
        <h2>{title}</h2>
        <div className="empty-state">
          <p>Nessuna richiesta di prestito.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="loan-section">
      <h2>{title}</h2>
      <div className="loan-list">
        {loans.map(loan => (
          <LoanRequestCard
            key={loan.id}
            loan={loan}
            onApprove={onApprove}
            onReject={onReject}
            onComplete={onComplete}
            isOwner={loan.book.owner.id === currentUserId}
          />
        ))}
      </div>
    </div>
  );
};

export default LoanRequestList;
