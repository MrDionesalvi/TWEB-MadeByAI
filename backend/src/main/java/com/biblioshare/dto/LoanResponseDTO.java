package com.biblioshare.dto;

import com.biblioshare.entity.LoanStatus;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDTO {
    private Long id;
    private BookDTO book;
    private UserDTO borrower;
    private LoanStatus status;
    private LocalDateTime requestDate;
    private LocalDateTime approvalDate;
    private LocalDateTime returnDate;
    private String notes;
}
