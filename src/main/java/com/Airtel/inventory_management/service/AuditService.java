package com.Airtel.inventory_management.service;

import com.Airtel.inventory_management.model.AuditLog;
import com.Airtel.inventory_management.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // Log an action
    public void log(String action, String entityType, Long entityId, String performedBy, String oldValue, String newValue) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setPerformedBy(performedBy);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    // Get all audit logs
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    // Get recent logs
    public List<AuditLog> getRecentLogs() {
        return auditLogRepository.findTop100ByOrderByTimestampDesc();
    }

    // Get logs by entity
    public List<AuditLog> getLogsByEntity(String entityType, Long entityId) {
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    // Get logs by action
    public List<AuditLog> getLogsByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    // Get logs by date range
    public List<AuditLog> getLogsByDateRange(LocalDateTime start, LocalDateTime end) {
        return auditLogRepository.findByTimestampBetween(start, end);
    }

    // Get logs by performer
    public List<AuditLog> getLogsByPerformer(String performedBy) {
        return auditLogRepository.findByPerformedBy(performedBy);
    }

    // Search logs
    public List<AuditLog> searchLogs(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllLogs();
        }
        return auditLogRepository.searchAuditLogs(keyword);
    }

    // Delete old logs (older than days)
    public void deleteOldLogs(int days) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        List<AuditLog> oldLogs = auditLogRepository.findByTimestampBefore(cutoff);
        auditLogRepository.deleteAll(oldLogs);
    }
}