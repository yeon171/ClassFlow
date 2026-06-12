package com.example.classflow.reservation.dto;

import com.example.classflow.reservation.entity.Reservation; // Reservation 엔티티 임포트
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponseDto {
    private Long id;
    private Long studentId; // memberId -> studentId
    private String studentName; // memberName -> studentName
    private LocalDateTime lessonTime; // dateTime -> lessonTime
    private String status; // ReservationStatus -> String

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        // reservation.getStudent()가 null이 아닐 때만 접근하도록 처리
        if (reservation.getStudent() != null) {
            this.studentId = reservation.getStudent().getId();
            this.studentName = reservation.getStudent().getName();
        } else {
            this.studentId = null; // 또는 적절한 기본값 설정
            this.studentName = "N/A"; // 또는 적절한 기본값 설정
        }
        this.lessonTime = reservation.getLessonTime(); // getDateTime -> getLessonTime
        this.status = reservation.getStatus();
    }
}
