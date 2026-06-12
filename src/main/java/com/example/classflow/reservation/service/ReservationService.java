package com.example.classflow.reservation.service;

import com.example.classflow.exception.ResourceNotFoundException; // ResourceNotFoundException 임포트
import com.example.classflow.reservation.entity.Reservation;
import com.example.classflow.reservation.repository.ReservationRepository;
import com.example.classflow.student.entity.Student;
import com.example.classflow.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Reservation createReservation(Long studentId, Reservation reservation) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + studentId)); // 예외 변경
        reservation.setStudent(student);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation updateReservation(Long id, Long studentId, Reservation reservationDetails) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found for this id :: " + id)); // 예외 변경

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + studentId)); // 예외 변경

        reservation.setLessonTime(reservationDetails.getLessonTime());
        reservation.setDuration(reservationDetails.getDuration());
        reservation.setMemo(reservationDetails.getMemo());
        reservation.setStatus(reservationDetails.getStatus());
        reservation.setStudent(student);

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found for this id :: " + id)); // 예외 변경
        reservationRepository.delete(reservation);
    }
}
