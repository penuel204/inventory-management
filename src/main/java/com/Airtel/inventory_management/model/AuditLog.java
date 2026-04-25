package com.Airtel.inventory_management.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String action;
    private String entityType;
    private Long entityId;
    private String performedBy;
    private String oldValue;
    private String newValue;
    private LocalDateTime timestamp;

    public AuditLog() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getAction() { return action; }
    public String getEntityType() { return entityType; }
    public Long getEntityId() { return entityId; }
    public String getPerformedBy() { return performedBy; }
    public String getOldValue() { return oldValue; }
    public String getNewValue() { return newValue; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setAction(String action) { this.action = action; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }
    public void setOldValue(String oldValue) { this.oldValue = oldValue; }
    public void setNewValue(String newValue) { this.newValue = newValue; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}