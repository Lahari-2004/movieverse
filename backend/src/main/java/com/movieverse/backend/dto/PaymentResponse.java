package com.movieverse.backend.dto;

import com.movieverse.backend.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private Long id;

    private Long bookingId;

    private Double amount;

    private PaymentStatus status;

    private String paymentMethod;

    private String transactionId;

    private LocalDateTime createdAt;

    private LocalDateTime paidAt;
}