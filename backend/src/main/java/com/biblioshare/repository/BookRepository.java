package com.biblioshare.repository;

import com.biblioshare.entity.Book;
import com.biblioshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByOwner(User owner);
    List<Book> findByAvailableForLoan(Boolean available);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByGenreId(Long genreId);
}
