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
//@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("approvalReport")
public class ApprovalReport extends Report{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ApprovalStatus approvalStatus = ApprovalStatus.PENDING;
    ApprovalReport(String title, String description, User user, Point point){
        super(title,description,user,point);
    }
}


