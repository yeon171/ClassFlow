package com.example.classflow.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequestDto {
    private Long memberId;
    private LocalDateTime dateTime;
}
