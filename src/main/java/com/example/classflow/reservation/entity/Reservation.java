package com.example.classflow.reservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime lessonTime;

    private Integer duration; // 수업 시간 (예: 60분, 90분)

    private String memo; // 예약 특이사항

    private String status; // 예약 상태 (예: PENDING, CONFIRMED, CANCELLED)
}
