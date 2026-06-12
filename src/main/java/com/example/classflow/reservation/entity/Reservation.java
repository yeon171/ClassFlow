package com.example.classflow.reservation.entity;

import com.example.classflow.student.entity.Student; // Student 엔티티 임포트
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

    @ManyToOne(fetch = FetchType.LAZY) // ManyToOne 관계 설정
    @JoinColumn(name = "student_id") // 외래 키 컬럼 지정
    private Student student; // Student 엔티티 참조
}
