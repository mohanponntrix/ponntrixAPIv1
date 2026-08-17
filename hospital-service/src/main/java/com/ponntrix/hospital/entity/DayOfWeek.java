package com.ponntrix.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "day_of_week", schema = "ponntrix_hospital")
@Getter
@Setter
@NoArgsConstructor
public class DayOfWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_of_week_id")
    private Integer dayOfWeekId;

    @Column(name = "days_of_week_name", nullable = false, unique = true)
    private String dayName;
}