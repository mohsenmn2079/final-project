package com.example.finalproject.report.repository;

import com.example.finalproject.report.entity.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentRepository extends JpaRepository<Accident,Long> {
}
