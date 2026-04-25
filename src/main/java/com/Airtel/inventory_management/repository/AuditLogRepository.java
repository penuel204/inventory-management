package com.Airtel.inventory_management.repository;

import com.Airtel.inventory_management.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    // Find logs by entity type and entity ID
    List<AuditLog> findByEntityTypeAndEntityId(String entityType, Long entityId);
    
    // Find logs by action (CREATE, UPDATE, DELETE, ASSIGN, RETURN)
    List<AuditLog> findByAction(String action);
    
    // Find logs by date range
    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    
    // Find logs by performer (username who performed the action)
    List<AuditLog> findByPerformedBy(String performedBy);
    
    // Find most recent 100 logs
    List<AuditLog> findTop100ByOrderByTimestampDesc();
    
    // Find all logs ordered by timestamp descending
    List<AuditLog> findAllByOrderByTimestampDesc();
    
    // Find logs before a specific timestamp (for cleanup)
    List<AuditLog> findByTimestampBefore(LocalDateTime timestamp);
    
    // Search logs by keyword in action, entity type, or performer
    @Query("SELECT a FROM AuditLog a WHERE " +
           "LOWER(a.action) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(a.entityType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(a.performedBy) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(a.oldValue) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(a.newValue) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AuditLog> searchAuditLogs(@Param("keyword") String keyword);
    
    // Count logs by action
    long countByAction(String action);
    
    // Count logs by entity type
    long countByEntityType(String entityType);
    
    // Delete logs older than timestamp
    void deleteByTimestampBefore(LocalDateTime timestamp);
}