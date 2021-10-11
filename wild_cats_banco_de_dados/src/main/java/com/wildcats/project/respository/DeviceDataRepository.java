package com.wildcats.project.respository;

import com.wildcats.project.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData, Integer> {
}
