package com.biblioshare.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private User borrower;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;
    
    @Column(nullable = false)
    private LocalDateTime requestDate;
    
    @Column
    private LocalDateTime approvalDate;
    
    @Column
    private LocalDateTime returnDate;
    
    @Column
    private String notes;
}
