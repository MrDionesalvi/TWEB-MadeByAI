package com.biblioshare.dto;

import lombok.Data;

@Data
public class LoanRequestDTO {
    private Long bookId;
    private String notes;
}
