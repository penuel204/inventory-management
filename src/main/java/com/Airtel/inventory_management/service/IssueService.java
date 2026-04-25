package com.Airtel.inventory_management.service;

import com.Airtel.inventory_management.model.Device;
import com.Airtel.inventory_management.model.Issue;
import com.Airtel.inventory_management.repository.DeviceRepository;
import com.Airtel.inventory_management.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    // Report new issue
    @Transactional
    public Issue reportIssue(Long deviceId, String reportedBy, String description) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isEmpty()) {
            return null;
        }

        Device device = deviceOpt.get();
        
        Issue issue = new Issue();
        issue.setDevice(device);
        issue.setReportedBy(reportedBy);
        issue.setIssueDescription(description);
        issue.setReportedDate(LocalDateTime.now());
        issue.setStatus("Open");

        // Update device status if needed
        if (!device.getStatus().equals("Under Repair")) {
            device.setStatus("Under Repair");
            deviceRepository.save(device);
        }

        return issueRepository.save(issue);
    }

    // Resolve issue
    @Transactional
    public Issue resolveIssue(Long issueId, String resolutionNotes) {
        Optional<Issue> issueOpt = issueRepository.findById(issueId);
        if (issueOpt.isEmpty()) {
            return null;
        }

        Issue issue = issueOpt.get();
        issue.setResolutionDate(LocalDateTime.now());
        issue.setResolutionNotes(resolutionNotes);
        issue.setStatus("Resolved");

        // Check if device has other open issues
        Device device = issue.getDevice();
        List<Issue> openIssues = issueRepository.findByDeviceAndStatus(device, "Open");
        
        if (openIssues.isEmpty()) {
            // No more open issues, update device status
            device.setStatus("Available");
            deviceRepository.save(device);
        }

        return issueRepository.save(issue);
    }

    // Get all issues
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    // Get open issues
    public List<Issue> getOpenIssues() {
        return issueRepository.findByStatus("Open");
    }

    // Get issues by device
    public List<Issue> getIssuesByDevice(Long deviceId) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isEmpty()) {
            return List.of();
        }
        return issueRepository.findByDevice(deviceOpt.get());
    }

    // Get issue by ID
    public Optional<Issue> getIssueById(Long id) {
        return issueRepository.findById(id);
    }

    // Get open issues count
    public long getOpenIssuesCount() {
        return issueRepository.countByStatus("Open");
    }

    // Update issue status
    public Issue updateIssueStatus(Long issueId, String status) {
        Optional<Issue> issueOpt = issueRepository.findById(issueId);
        if (issueOpt.isPresent()) {
            Issue issue = issueOpt.get();
            issue.setStatus(status);
            return issueRepository.save(issue);
        }
        return null;
    }
}