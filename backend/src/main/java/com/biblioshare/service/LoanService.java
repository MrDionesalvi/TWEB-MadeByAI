package com.biblioshare.service;

import com.biblioshare.dto.BookDTO;
import com.biblioshare.dto.LoanRequestDTO;
import com.biblioshare.dto.LoanResponseDTO;
import com.biblioshare.dto.UserDTO;
import com.biblioshare.entity.*;
import com.biblioshare.repository.BookRepository;
import com.biblioshare.repository.LoanRequestRepository;
import com.biblioshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {
    
    @Autowired
    private LoanRequestRepository loanRequestRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public LoanResponseDTO createLoanRequest(LoanRequestDTO dto, Long borrowerId) {
        Optional<User> borrowerOpt = userRepository.findById(borrowerId);
        Optional<Book> bookOpt = bookRepository.findById(dto.getBookId());
        
        if (borrowerOpt.isEmpty() || bookOpt.isEmpty()) {
            throw new RuntimeException("User or Book not found");
        }
        
        Book book = bookOpt.get();
        if (!book.getAvailableForLoan()) {
            throw new RuntimeException("Book is not available for loan");
        }
        
        if (book.getOwner().getId().equals(borrowerId)) {
            throw new RuntimeException("Cannot borrow your own book");
        }
        
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBook(book);
        loanRequest.setBorrower(borrowerOpt.get());
        loanRequest.setStatus(LoanStatus.PENDING);
        loanRequest.setRequestDate(LocalDateTime.now());
        loanRequest.setNotes(dto.getNotes());
        
        LoanRequest saved = loanRequestRepository.save(loanRequest);
        return convertToResponseDTO(saved);
    }
    
    public List<LoanResponseDTO> getUserLoanRequests(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return loanRequestRepository.findByBorrower(userOpt.get()).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public List<LoanResponseDTO> getReceivedLoanRequests(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return loanRequestRepository.findByBookOwner(userOpt.get()).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public LoanResponseDTO approveLoanRequest(Long requestId, Long ownerId) {
        Optional<LoanRequest> requestOpt = loanRequestRepository.findById(requestId);
        if (requestOpt.isEmpty()) {
            throw new RuntimeException("Loan request not found");
        }
        
        LoanRequest request = requestOpt.get();
        if (!request.getBook().getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("Not authorized to approve this request");
        }
        
        if (request.getStatus() != LoanStatus.PENDING) {
            throw new RuntimeException("Request is not in pending status");
        }
        
        request.setStatus(LoanStatus.APPROVED);
        request.setApprovalDate(LocalDateTime.now());
        
        // Mark book as unavailable
        Book book = request.getBook();
        book.setAvailableForLoan(false);
        bookRepository.save(book);
        
        LoanRequest updated = loanRequestRepository.save(request);
        return convertToResponseDTO(updated);
    }
    
    public LoanResponseDTO rejectLoanRequest(Long requestId, Long ownerId) {
        Optional<LoanRequest> requestOpt = loanRequestRepository.findById(requestId);
        if (requestOpt.isEmpty()) {
            throw new RuntimeException("Loan request not found");
        }
        
        LoanRequest request = requestOpt.get();
        if (!request.getBook().getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("Not authorized to reject this request");
        }
        
        if (request.getStatus() != LoanStatus.PENDING) {
            throw new RuntimeException("Request is not in pending status");
        }
        
        request.setStatus(LoanStatus.REJECTED);
        
        LoanRequest updated = loanRequestRepository.save(request);
        return convertToResponseDTO(updated);
    }
    
    public LoanResponseDTO completeLoanRequest(Long requestId, Long userId) {
        Optional<LoanRequest> requestOpt = loanRequestRepository.findById(requestId);
        if (requestOpt.isEmpty()) {
            throw new RuntimeException("Loan request not found");
        }
        
        LoanRequest request = requestOpt.get();
        if (!request.getBook().getOwner().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to complete this request");
        }
        
        if (request.getStatus() != LoanStatus.APPROVED) {
            throw new RuntimeException("Request is not in approved status");
        }
        
        request.setStatus(LoanStatus.COMPLETED);
        request.setReturnDate(LocalDateTime.now());
        
        // Mark book as available again
        Book book = request.getBook();
        book.setAvailableForLoan(true);
        bookRepository.save(book);
        
        LoanRequest updated = loanRequestRepository.save(request);
        return convertToResponseDTO(updated);
    }
    
    private LoanResponseDTO convertToResponseDTO(LoanRequest request) {
        LoanResponseDTO dto = new LoanResponseDTO();
        dto.setId(request.getId());
        dto.setStatus(request.getStatus());
        dto.setRequestDate(request.getRequestDate());
        dto.setApprovalDate(request.getApprovalDate());
        dto.setReturnDate(request.getReturnDate());
        dto.setNotes(request.getNotes());
        
        // Book info
        Book book = request.getBook();
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setCondition(book.getCondition());
        
        UserDTO ownerDTO = new UserDTO();
        ownerDTO.setId(book.getOwner().getId());
        ownerDTO.setUsername(book.getOwner().getUsername());
        ownerDTO.setFullName(book.getOwner().getFullName());
        ownerDTO.setCity(book.getOwner().getCity());
        bookDTO.setOwner(ownerDTO);
        
        dto.setBook(bookDTO);
        
        // Borrower info
        User borrower = request.getBorrower();
        UserDTO borrowerDTO = new UserDTO();
        borrowerDTO.setId(borrower.getId());
        borrowerDTO.setUsername(borrower.getUsername());
        borrowerDTO.setFullName(borrower.getFullName());
        borrowerDTO.setCity(borrower.getCity());
        dto.setBorrower(borrowerDTO);
        
        return dto;
    }
}
