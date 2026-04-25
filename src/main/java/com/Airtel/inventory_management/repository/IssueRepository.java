package com.Airtel.inventory_management.repository;

import com.Airtel.inventory_management.model.Issue;
import com.Airtel.inventory_management.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    
    // Find issues by device
    List<Issue> findByDevice(Device device);
    
    // Find open issues
    List<Issue> findByStatus(String status);
    
    // Find open issues for a device
    List<Issue> findByDeviceAndStatus(Device device, String status);
    
    // Find issues by reported date range
    List<Issue> findByReportedDateBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
    
    // Count open issues
    long countByStatus(String status);
    
    // Find unresolved issues
    List<Issue> findByResolutionDateIsNull();
}