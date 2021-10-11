package com.wildcats.project.respository;

import com.wildcats.project.entity.Dispenser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DispenserRepository extends JpaRepository<Dispenser, Integer> {

    Dispenser findByMacAddress(String macAddress);

    List<Dispenser> findByLocalContainingIgnoreCase(String local);
}
