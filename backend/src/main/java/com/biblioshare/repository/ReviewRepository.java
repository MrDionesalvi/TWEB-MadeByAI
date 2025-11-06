package com.biblioshare.repository;

import com.biblioshare.entity.Review;
import com.biblioshare.entity.Book;
import com.biblioshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBook(Book book);
    List<Review> findByUser(User user);
}
