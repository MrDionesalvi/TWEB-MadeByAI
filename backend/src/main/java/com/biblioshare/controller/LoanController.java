package com.biblioshare.controller;

import com.biblioshare.dto.LoanRequestDTO;
import com.biblioshare.dto.LoanResponseDTO;
import com.biblioshare.service.AuthService;
import com.biblioshare.service.LoanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class LoanController {
    
    @Autowired
    private LoanService loanService;
    
    @Autowired
    private AuthService authService;
    
    @PostMapping
    public ResponseEntity<?> createLoanRequest(@RequestBody LoanRequestDTO dto, HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        Long userId = (Long) session.getAttribute("userId");
        try {
            LoanResponseDTO loan = loanService.createLoanRequest(dto, userId);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/my-requests")
    public ResponseEntity<?> getMyLoanRequests(HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        Long userId = (Long) session.getAttribute("userId");
        try {
            List<LoanResponseDTO> loans = loanService.getUserLoanRequests(userId);
            return ResponseEntity.ok(loans);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/received")
    public ResponseEntity<?> getReceivedLoanRequests(HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        Long userId = (Long) session.getAttribute("userId");
        try {
            List<LoanResponseDTO> loans = loanService.getReceivedLoanRequests(userId);
            return ResponseEntity.ok(loans);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveLoanRequest(@PathVariable Long id, HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        Long userId = (Long) session.getAttribute("userId");
        try {
            LoanResponseDTO loan = loanService.approveLoanRequest(id, userId);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectLoanRequest(@PathVariable Long id, HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        Long userId = (Long) session.getAttribute("userId");
        try {
            LoanResponseDTO loan = loanService.rejectLoanRequest(id, userId);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeLoanRequest(@PathVariable Long id, HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        Long userId = (Long) session.getAttribute("userId");
        try {
            LoanResponseDTO loan = loanService.completeLoanRequest(id, userId);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
