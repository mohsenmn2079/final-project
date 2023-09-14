package com.example.finalproject.report.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("accident")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Accident extends Report {
    @Id
    Long id;
    @Column(nullable = false)
    boolean approvalStatus;
}
