package com.example.classflow.reservation.service;

import com.example.classflow.member.Member;
import com.example.classflow.member.repository.MemberRepository;
import com.example.classflow.reservation.Reservation;
import com.example.classflow.reservation.ReservationStatus;
import com.example.classflow.reservation.dto.ReservationRequestDto;
import com.example.classflow.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Reservation createReservation(ReservationRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("Member not found with ID: " + requestDto.getMemberId()));

        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setDateTime(requestDto.getDateTime());
        reservation.setStatus(ReservationStatus.PENDING); // 초기 상태는 PENDING

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByMemberId(Long memberId) {
        return reservationRepository.findByMemberId(memberId);
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found with ID: " + reservationId));
    }

    @Transactional
    public Reservation cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found with ID: " + reservationId));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("이미 취소된 예약입니다.");
        }
        // TODO: 예약 취소 로직 (예: 시간 제약, 환불 정책 등) 추가 가능
        reservation.cancel(); // 상태를 CANCELLED로 변경
        return reservationRepository.save(reservation);
    }
}
