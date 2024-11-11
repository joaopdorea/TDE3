package com.example.demo.Repository;

import com.example.demo.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverDAO extends JpaRepository<Driver,Long> {
}
