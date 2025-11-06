package com.biblioshare.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    @Column
    private String isbn;
    
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    
    @Column
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookCondition condition;
    
    @Column(nullable = false)
    private Boolean availableForLoan = true;
    
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<LoanRequest> loanRequests;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
