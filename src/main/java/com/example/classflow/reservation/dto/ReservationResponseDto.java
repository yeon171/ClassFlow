package com.example.classflow.reservation.dto;

import com.example.classflow.reservation.Reservation;
import com.example.classflow.reservation.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponseDto {
    private Long id;
    private Long memberId;
    private String memberName;
    private LocalDateTime dateTime;
    private ReservationStatus status;

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this.memberId = reservation.getMember().getId();
        this.memberName = reservation.getMember().getName();
        this.dateTime = reservation.getDateTime();
        this.status = reservation.getStatus();
    }
}
