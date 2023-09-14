package com.example.finalproject.report.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("traffic")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Traffic extends Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


}
