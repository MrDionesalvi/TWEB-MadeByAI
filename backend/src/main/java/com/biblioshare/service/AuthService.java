package com.biblioshare.service;

import com.biblioshare.dto.UserDTO;
import com.biblioshare.entity.User;
import com.biblioshare.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    public UserDTO login(String username, String password, HttpSession session) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole().toString());
            return convertToDTO(user);
        }
        
        return null;
    }
    
    public void logout(HttpSession session) {
        session.invalidate();
    }
    
    public UserDTO getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return null;
        }
        
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(this::convertToDTO).orElse(null);
    }
    
    public boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }
    
    public boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        return "ADMIN".equals(role);
    }
    
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setCity(user.getCity());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole());
        return dto;
    }
}
