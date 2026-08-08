package com.movieverse.backend.service.impl;

import com.movieverse.backend.dto.PaymentRequest;
import com.movieverse.backend.dto.PaymentResponse;
import com.movieverse.backend.entity.Booking;
import com.movieverse.backend.entity.Payment;
import com.movieverse.backend.enums.BookingStatus;
import com.movieverse.backend.enums.PaymentStatus;
import com.movieverse.backend.exception.BookingException;
import com.movieverse.backend.exception.ResourceNotFoundException;
import com.movieverse.backend.repository.BookingRepository;
import com.movieverse.backend.repository.PaymentRepository;
import com.movieverse.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public PaymentResponse makePayment(PaymentRequest request) {

        // Find booking
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        // Check booking status
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingException(
                    "Cannot make payment for a cancelled booking"
            );
        }

        // Check if payment already exists
        if (paymentRepository.findByBookingId(booking.getId()).isPresent()) {
            throw new BookingException(
                    "Payment already exists for this booking"
            );
        }

        // Generate transaction ID
        String transactionId = "TXN-" + UUID.randomUUID();

        // Create payment
        Payment payment = Payment.builder()
                .booking(booking)
                .amount(booking.getTotalAmount())
                .paymentMethod(request.getPaymentMethod())
                .transactionId(transactionId)
                .status(PaymentStatus.SUCCESS)
                .createdAt(LocalDateTime.now())
                .paidAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // Confirm booking after successful payment
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentByBookingId(Long bookingId) {

        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found for booking"
                        ));

        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(Payment payment) {

        return PaymentResponse.builder()
                .id(payment.getId())
                .bookingId(payment.getBooking().getId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .createdAt(payment.getCreatedAt())
                .paidAt(payment.getPaidAt())
                .build();
    }
}