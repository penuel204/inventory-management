package com.Airtel.inventory_management.repository;

import com.Airtel.inventory_management.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    
    List<Device> findByStatus(String status);
    List<Device> findByDeviceType(String deviceType);
    List<Device> findByDeviceCondition(String deviceCondition);
    boolean existsBySerialNumber(String serialNumber);
    Device findBySerialNumber(String serialNumber);
    
    @Query("SELECT d FROM Device d WHERE " +
           "LOWER(d.serialNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.make) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.model) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Device> searchDevices(@Param("keyword") String keyword);
    
    long countByStatus(String status);
    long countByDeviceType(String deviceType);
}