package com.biblioshare.repository;

import com.biblioshare.entity.LoanRequest;
import com.biblioshare.entity.LoanStatus;
import com.biblioshare.entity.User;
import com.biblioshare.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
    List<LoanRequest> findByBorrower(User borrower);
    List<LoanRequest> findByBookOwner(User owner);
    List<LoanRequest> findByBook(Book book);
    List<LoanRequest> findByStatus(LoanStatus status);
    List<LoanRequest> findByBorrowerAndStatus(User borrower, LoanStatus status);
}
