package com.Airtel.inventory_management.repository;

import com.Airtel.inventory_management.model.Assignment;
import com.Airtel.inventory_management.model.Device;
import com.Airtel.inventory_management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    // Find current assignments (not returned)
    List<Assignment> findByReturnedDateIsNull();
    
    // Find assignments by device
    List<Assignment> findByDevice(Device device);
    
    // Find current assignment for a device
    Assignment findByDeviceAndReturnedDateIsNull(Device device);
    
    // Find assignments by employee
    List<Assignment> findByEmployee(Employee employee);
    
    // Find current assignment for an employee
    Assignment findByEmployeeAndReturnedDateIsNull(Employee employee);
    
    // Find assignments by date range
    List<Assignment> findByAssignedDateBetween(LocalDateTime start, LocalDateTime end);
    
    // Find returned assignments
    List<Assignment> findByReturnedDateIsNotNull();
    
    // Count active assignments
    long countByReturnedDateIsNull();
    
    // Find assignments by device and date range
    @Query("SELECT a FROM Assignment a WHERE a.device = :device AND " +
           "((a.assignedDate BETWEEN :start AND :end) OR " +
           "(a.returnedDate BETWEEN :start AND :end))")
    List<Assignment> findAssignmentsByDeviceAndDateRange(@Param("device") Device device,
                                                          @Param("start") LocalDateTime start,
                                                          @Param("end") LocalDateTime end);
}