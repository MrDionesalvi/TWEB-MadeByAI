package com.biblioshare.service;

import com.biblioshare.dto.BookDTO;
import com.biblioshare.dto.BookRequest;
import com.biblioshare.dto.UserDTO;
import com.biblioshare.entity.Book;
import com.biblioshare.entity.Genre;
import com.biblioshare.entity.Review;
import com.biblioshare.entity.User;
import com.biblioshare.repository.BookRepository;
import com.biblioshare.repository.GenreRepository;
import com.biblioshare.repository.ReviewRepository;
import com.biblioshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GenreRepository genreRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<BookDTO> getAvailableBooks() {
        return bookRepository.findByAvailableForLoan(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<BookDTO> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<BookDTO> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<BookDTO> getBooksByGenre(Long genreId) {
        return bookRepository.findByGenreId(genreId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<BookDTO> getUserBooks(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return bookRepository.findByOwner(userOpt.get()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public BookDTO getBookById(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        return bookOpt.map(this::convertToDTO).orElse(null);
    }
    
    public BookDTO createBook(BookRequest request, Long ownerId) {
        Optional<User> ownerOpt = userRepository.findById(ownerId);
        if (ownerOpt.isEmpty()) {
            throw new RuntimeException("Owner not found");
        }
        
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setCondition(request.getCondition());
        book.setAvailableForLoan(request.getAvailableForLoan() != null ? request.getAvailableForLoan() : true);
        book.setOwner(ownerOpt.get());
        
        if (request.getGenreId() != null) {
            Optional<Genre> genreOpt = genreRepository.findById(request.getGenreId());
            genreOpt.ifPresent(book::setGenre);
        }
        
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }
    
    public BookDTO updateBook(Long id, BookRequest request, Long userId) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isEmpty()) {
            throw new RuntimeException("Book not found");
        }
        
        Book book = bookOpt.get();
        if (!book.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to update this book");
        }
        
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setCondition(request.getCondition());
        book.setAvailableForLoan(request.getAvailableForLoan());
        
        if (request.getGenreId() != null) {
            Optional<Genre> genreOpt = genreRepository.findById(request.getGenreId());
            genreOpt.ifPresent(book::setGenre);
        }
        
        Book updatedBook = bookRepository.save(book);
        return convertToDTO(updatedBook);
    }
    
    public void deleteBook(Long id, Long userId, boolean isAdmin) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isEmpty()) {
            throw new RuntimeException("Book not found");
        }
        
        Book book = bookOpt.get();
        if (!isAdmin && !book.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to delete this book");
        }
        
        bookRepository.deleteById(id);
    }
    
    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setGenreName(book.getGenre() != null ? book.getGenre().getName() : null);
        dto.setDescription(book.getDescription());
        dto.setCondition(book.getCondition());
        dto.setAvailableForLoan(book.getAvailableForLoan());
        
        UserDTO ownerDTO = new UserDTO();
        ownerDTO.setId(book.getOwner().getId());
        ownerDTO.setUsername(book.getOwner().getUsername());
        ownerDTO.setFullName(book.getOwner().getFullName());
        ownerDTO.setCity(book.getOwner().getCity());
        dto.setOwner(ownerDTO);
        
        // Calculate average rating
        List<Review> reviews = reviewRepository.findByBook(book);
        if (!reviews.isEmpty()) {
            double avgRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
            dto.setAverageRating(avgRating);
            dto.setReviewCount(reviews.size());
        } else {
            dto.setAverageRating(0.0);
            dto.setReviewCount(0);
        }
        
        return dto;
    }
}
