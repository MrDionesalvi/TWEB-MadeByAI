package com.biblioshare.controller;

import com.biblioshare.dto.BookDTO;
import com.biblioshare.dto.BookRequest;
import com.biblioshare.service.AuthService;
import com.biblioshare.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private AuthService authService;
    
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<BookDTO>> getAvailableBooks() {
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }
    
    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<BookDTO>> searchByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }
    
    @GetMapping("/search/author/{author}")
    public ResponseEntity<List<BookDTO>> searchByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(bookService.searchByAuthor(author));
    }
    
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable Long genreId) {
        return ResponseEntity.ok(bookService.getBooksByGenre(genreId));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookDTO>> getUserBooks(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(bookService.getUserBooks(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookRequest request, HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        Long userId = (Long) session.getAttribute("userId");
        try {
            BookDTO book = bookService.createBook(request, userId);
            return ResponseEntity.ok(book);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookRequest request, HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        Long userId = (Long) session.getAttribute("userId");
        try {
            BookDTO book = bookService.updateBook(id, request, userId);
            return ResponseEntity.ok(book);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id, HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        Long userId = (Long) session.getAttribute("userId");
        boolean isAdmin = authService.isAdmin(session);
        
        try {
            bookService.deleteBook(id, userId, isAdmin);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
