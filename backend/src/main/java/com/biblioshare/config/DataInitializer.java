package com.biblioshare.config;

import com.biblioshare.entity.*;
import com.biblioshare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GenreRepository genreRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private LoanRequestRepository loanRequestRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Create genres
        Genre fiction = new Genre(null, "Fiction", "Fictional literature", null);
        Genre nonFiction = new Genre(null, "Non-Fiction", "Non-fictional literature", null);
        Genre sciFi = new Genre(null, "Science Fiction", "Science fiction and fantasy", null);
        Genre mystery = new Genre(null, "Mystery", "Mystery and thriller books", null);
        Genre biography = new Genre(null, "Biography", "Biographical works", null);
        
        genreRepository.saveAll(Arrays.asList(fiction, nonFiction, sciFi, mystery, biography));
        
        // Create users
        User user1 = new User();
        user1.setUsername("mario");
        user1.setPassword("password123");
        user1.setEmail("mario.rossi@email.com");
        user1.setFullName("Mario Rossi");
        user1.setCity("Milano");
        user1.setAddress("Via Roma 1");
        user1.setRole(UserRole.USER);
        
        User user2 = new User();
        user2.setUsername("anna");
        user2.setPassword("password123");
        user2.setEmail("anna.bianchi@email.com");
        user2.setFullName("Anna Bianchi");
        user2.setCity("Roma");
        user2.setAddress("Via Veneto 10");
        user2.setRole(UserRole.USER);
        
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setEmail("admin@biblioshare.com");
        admin.setFullName("Administrator");
        admin.setCity("Milano");
        admin.setAddress("Via Admin 1");
        admin.setRole(UserRole.ADMIN);
        
        userRepository.saveAll(Arrays.asList(user1, user2, admin));
        
        // Create books for user1
        Book book1 = new Book();
        book1.setTitle("Il Nome della Rosa");
        book1.setAuthor("Umberto Eco");
        book1.setIsbn("978-8845292453");
        book1.setGenre(mystery);
        book1.setDescription("Un thriller medievale");
        book1.setCondition(BookCondition.GOOD);
        book1.setAvailableForLoan(true);
        book1.setOwner(user1);
        
        Book book2 = new Book();
        book2.setTitle("1984");
        book2.setAuthor("George Orwell");
        book2.setIsbn("978-0451524935");
        book2.setGenre(fiction);
        book2.setDescription("Un classico della distopia");
        book2.setCondition(BookCondition.LIKE_NEW);
        book2.setAvailableForLoan(true);
        book2.setOwner(user1);
        
        Book book3 = new Book();
        book3.setTitle("Dune");
        book3.setAuthor("Frank Herbert");
        book3.setIsbn("978-0441172719");
        book3.setGenre(sciFi);
        book3.setDescription("Epico romanzo di fantascienza");
        book3.setCondition(BookCondition.GOOD);
        book3.setAvailableForLoan(true);
        book3.setOwner(user1);
        
        // Create books for user2
        Book book4 = new Book();
        book4.setTitle("Sapiens");
        book4.setAuthor("Yuval Noah Harari");
        book4.setIsbn("978-0062316097");
        book4.setGenre(nonFiction);
        book4.setDescription("Una breve storia dell'umanità");
        book4.setCondition(BookCondition.NEW);
        book4.setAvailableForLoan(true);
        book4.setOwner(user2);
        
        Book book5 = new Book();
        book5.setTitle("Il Signore degli Anelli");
        book5.setAuthor("J.R.R. Tolkien");
        book5.setIsbn("978-8845292613");
        book5.setGenre(fiction);
        book5.setDescription("La trilogia completa");
        book5.setCondition(BookCondition.ACCEPTABLE);
        book5.setAvailableForLoan(true);
        book5.setOwner(user2);
        
        Book book6 = new Book();
        book6.setTitle("Steve Jobs");
        book6.setAuthor("Walter Isaacson");
        book6.setIsbn("978-1451648539");
        book6.setGenre(biography);
        book6.setDescription("La biografia di Steve Jobs");
        book6.setCondition(BookCondition.GOOD);
        book6.setAvailableForLoan(false);
        book6.setOwner(user2);
        
        bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6));
        
        // Create some reviews
        Review review1 = new Review();
        review1.setBook(book1);
        review1.setUser(user2);
        review1.setRating(5);
        review1.setComment("Un capolavoro assoluto! Intrigante dall'inizio alla fine.");
        review1.setReviewDate(LocalDateTime.now().minusDays(5));
        
        Review review2 = new Review();
        review2.setBook(book4);
        review2.setUser(user1);
        review2.setRating(5);
        review2.setComment("Libro affascinante che ti fa riflettere sulla storia dell'umanità.");
        review2.setReviewDate(LocalDateTime.now().minusDays(3));
        
        Review review3 = new Review();
        review3.setBook(book5);
        review3.setUser(user1);
        review3.setRating(4);
        review3.setComment("Epico! Un po' lungo ma ne vale la pena.");
        review3.setReviewDate(LocalDateTime.now().minusDays(1));
        
        reviewRepository.saveAll(Arrays.asList(review1, review2, review3));
        
        // Create a sample loan request
        LoanRequest loan1 = new LoanRequest();
        loan1.setBook(book4);
        loan1.setBorrower(user1);
        loan1.setStatus(LoanStatus.PENDING);
        loan1.setRequestDate(LocalDateTime.now().minusDays(2));
        loan1.setNotes("Mi piacerebbe molto leggere questo libro!");
        
        loanRequestRepository.save(loan1);
        
        System.out.println("Database initialized with sample data!");
    }
}
