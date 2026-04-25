package com.Airtel.inventory_management.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
    
    private String reportedBy;
    private LocalDateTime reportedDate;
    private String issueDescription;
    private LocalDateTime resolutionDate;
    private String resolutionNotes;
    private String status;

    public Issue() {
        this.reportedDate = LocalDateTime.now();
        this.status = "Open";
    }

    // Getters
    public Long getId() { return id; }
    public Device getDevice() { return device; }
    public String getReportedBy() { return reportedBy; }
    public LocalDateTime getReportedDate() { return reportedDate; }
    public String getIssueDescription() { return issueDescription; }
    public LocalDateTime getResolutionDate() { return resolutionDate; }
    public String getResolutionNotes() { return resolutionNotes; }
    public String getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setDevice(Device device) { this.device = device; }
    public void setReportedBy(String reportedBy) { this.reportedBy = reportedBy; }
    public void setReportedDate(LocalDateTime reportedDate) { this.reportedDate = reportedDate; }
    public void setIssueDescription(String issueDescription) { this.issueDescription = issueDescription; }
    public void setResolutionDate(LocalDateTime resolutionDate) { this.resolutionDate = resolutionDate; }
    public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }
    public void setStatus(String status) { this.status = status; }
}