package com.example.classflow.reservation.controller;

import com.example.classflow.reservation.entity.Reservation;
import com.example.classflow.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public Reservation createReservation(@RequestParam Long studentId, @RequestBody Reservation reservation) {
        return reservationService.createReservation(studentId, reservation);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestParam Long studentId, @RequestBody Reservation reservationDetails) {
        // 서비스에서 ResourceNotFoundException을 던지면 @ResponseStatus가 처리하므로 try-catch 제거
        Reservation updatedReservation = reservationService.updateReservation(id, studentId, reservationDetails);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        // 서비스에서 ResourceNotFoundException을 던지면 @ResponseStatus가 처리하므로 try-catch 제거
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
