package com.example.classflow.student.entity;

import com.example.classflow.reservation.entity.Reservation; // Reservation 엔티티 임포트
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String memo;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true) // OneToMany 관계 설정
    private List<Reservation> reservations = new ArrayList<>(); // Reservation 리스트
}
