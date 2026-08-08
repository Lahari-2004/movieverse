package com.movieverse.backend.service;

import com.movieverse.backend.dto.PaymentRequest;
import com.movieverse.backend.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse makePayment(PaymentRequest request);

    PaymentResponse getPaymentByBookingId(Long bookingId);
}
