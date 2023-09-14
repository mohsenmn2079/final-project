package com.example.finalproject.report.entity;

import com.example.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.geo.Point;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "reports")
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "report_type", discriminatorType = DiscriminatorType.STRING)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    Point geometry;

    @Column(nullable = false)
    Date activationTime;

    @Column(nullable = false)
    Date expireTime;

    @Column(nullable = false)
    boolean deleteStatus = false;

    int likes;

    int dislikes;
}
