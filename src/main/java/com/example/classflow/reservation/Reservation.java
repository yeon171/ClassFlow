package com.example.classflow.reservation;

import com.example.classflow.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // Member 엔티티의 ID를 참조
    private Member member; // userId 대신 Member 객체 참조

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
    private ReservationStatus status;

    // 편의 메서드
    public void confirm() {
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }
}
