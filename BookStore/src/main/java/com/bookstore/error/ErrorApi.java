package com.bookstore.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorApi {

    private int status;
    private String errorMessage;
    private LocalDateTime timestamp;
}
