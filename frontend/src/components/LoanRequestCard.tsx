import React from 'react';
import type { LoanResponse } from '../types';
import './LoanRequestCard.css';

interface LoanRequestCardProps {
  loan: LoanResponse;
  onApprove?: (id: number) => void;
  onReject?: (id: number) => void;
  onComplete?: (id: number) => void;
  isOwner: boolean;
}

const LoanRequestCard: React.FC<LoanRequestCardProps> = ({ 
  loan, 
  onApprove, 
  onReject,
  onComplete,
  isOwner 
}) => {
  const statusLabels = {
    PENDING: 'In Attesa',
    APPROVED: 'Approvato',
    REJECTED: 'Rifiutato',
    ACTIVE: 'Attivo',
    COMPLETED: 'Completato',
    CANCELLED: 'Annullato'
  };

  const formatDate = (dateString: string | null) => {
    if (!dateString) return '-';
    return new Date(dateString).toLocaleDateString('it-IT');
  };

  return (
    <div className={`loan-card status-${loan.status.toLowerCase()}`}>
      <div className="loan-header">
        <h3>{loan.book.title}</h3>
        <span className={`status-badge ${loan.status.toLowerCase()}`}>
          {statusLabels[loan.status]}
        </span>
      </div>

      <div className="loan-details">
        <p><strong>Autore:</strong> {loan.book.author}</p>
        
        {isOwner ? (
          <p><strong>Richiesto da:</strong> {loan.borrower.fullName} ({loan.borrower.city})</p>
        ) : (
          <p><strong>Proprietario:</strong> {loan.book.owner.fullName} ({loan.book.owner.city})</p>
        )}
        
        <p><strong>Data richiesta:</strong> {formatDate(loan.requestDate)}</p>
        
        {loan.approvalDate && (
          <p><strong>Data approvazione:</strong> {formatDate(loan.approvalDate)}</p>
        )}
        
        {loan.returnDate && (
          <p><strong>Data restituzione:</strong> {formatDate(loan.returnDate)}</p>
        )}
        
        {loan.notes && (
          <p className="loan-notes"><strong>Note:</strong> {loan.notes}</p>
        )}
      </div>

      {isOwner && loan.status === 'PENDING' && (
        <div className="loan-actions">
          {onApprove && (
            <button className="action-button approve" onClick={() => onApprove(loan.id)}>
              ✓ Approva
            </button>
          )}
          {onReject && (
            <button className="action-button reject" onClick={() => onReject(loan.id)}>
              ✗ Rifiuta
            </button>
          )}
        </div>
      )}

      {isOwner && loan.status === 'APPROVED' && onComplete && (
        <div className="loan-actions">
          <button className="action-button complete" onClick={() => onComplete(loan.id)}>
            ✓ Segna come Completato
          </button>
        </div>
      )}
    </div>
  );
};

export default LoanRequestCard;
