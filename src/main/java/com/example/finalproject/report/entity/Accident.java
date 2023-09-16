package com.example.finalproject.report.entity;

import com.example.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Accident extends ApprovalReport {
    @Id
    Long id;
    public Accident(String title, String description, User user, Point point){
        super(title,description,user,point);
    }
}
