package com.example.finalproject.report.entity;

import com.example.finalproject.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "reports")
@Inheritance(strategy = InheritanceType.JOINED)
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    User user;

    @Column(columnDefinition = "geometry(Point,3857)")
    Point point;

    @Column(nullable = false)
    Date activationTime = new Date();

    @Column(nullable = false)
    boolean deleteStatus = false;

    int likes = 0;

    int dislikes = 0;

    Report(String title, String description, User user, Point point){
        this.title=title;
        this.description=description;
        this.user = user;
        this.point = point;
    }
}
