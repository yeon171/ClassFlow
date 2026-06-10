package com.example.classflow.reservation.controller;

import com.example.classflow.reservation.Reservation;
import com.example.classflow.reservation.dto.ReservationRequestDto;
import com.example.classflow.reservation.dto.ReservationResponseDto;
import com.example.classflow.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 예약 생성 API
    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto requestDto) {
        try {
            Reservation reservation = reservationService.createReservation(requestDto);
            return new ResponseEntity<>(new ReservationResponseDto(reservation), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Member not found
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 회원 ID로 예약 목록 조회 API
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ReservationResponseDto>> getReservationsByMemberId(@PathVariable Long memberId) {
        List<Reservation> reservations = reservationService.getReservationsByMemberId(memberId);
        List<ReservationResponseDto> responseDtos = reservations.stream()
                .map(ReservationResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    // 예약 ID로 단일 예약 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id) {
        try {
            Reservation reservation = reservationService.getReservationById(id);
            return new ResponseEntity<>(new ReservationResponseDto(reservation), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Reservation not found
        }
    }

    // 예약 취소 API
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponseDto> cancelReservation(@PathVariable Long id) {
        try {
            Reservation cancelledReservation = reservationService.cancelReservation(id);
            return new ResponseEntity<>(new ReservationResponseDto(cancelledReservation), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Reservation not found
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Already cancelled
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
