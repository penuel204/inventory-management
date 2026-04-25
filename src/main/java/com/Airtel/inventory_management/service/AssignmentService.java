package com.Airtel.inventory_management.service;

import com.Airtel.inventory_management.model.Assignment;
import com.Airtel.inventory_management.model.Device;
import com.Airtel.inventory_management.model.Employee;
import com.Airtel.inventory_management.repository.AssignmentRepository;
import com.Airtel.inventory_management.repository.DeviceRepository;
import com.Airtel.inventory_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Assign device to employee
    @Transactional
    public Assignment assignDevice(Long deviceId, Long employeeId, String condition, String notes) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        if (deviceOpt.isEmpty() || employeeOpt.isEmpty()) {
            return null;
        }

        Device device = deviceOpt.get();
        Employee employee = employeeOpt.get();

        // Check if device is already assigned
        Assignment existingAssignment = assignmentRepository.findByDeviceAndReturnedDateIsNull(device);
        if (existingAssignment != null) {
            return null;
        }

        // Create new assignment
        Assignment assignment = new Assignment();
        assignment.setDevice(device);
        assignment.setEmployee(employee);
        assignment.setAssignedDate(LocalDateTime.now());
        assignment.setConditionAtAssignment(condition);
        assignment.setNotes(notes);

        // Update device status
        device.setStatus("Assigned");
        deviceRepository.save(device);

        return assignmentRepository.save(assignment);
    }

    // Return device
    @Transactional
    public Assignment returnDevice(Long deviceId, String returnNotes) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isEmpty()) {
            return null;
        }

        Device device = deviceOpt.get();
        Assignment assignment = assignmentRepository.findByDeviceAndReturnedDateIsNull(device);

        if (assignment == null) {
            return null;
        }

        assignment.setReturnedDate(LocalDateTime.now());
        assignment.setNotes(returnNotes);

        // Update device status
        device.setStatus("Available");
        deviceRepository.save(device);

        return assignmentRepository.save(assignment);
    }

    // Get current assignments
    public List<Assignment> getCurrentAssignments() {
        return assignmentRepository.findByReturnedDateIsNull();
    }

    // Get assignment history for a device
    public List<Assignment> getAssignmentHistoryForDevice(Long deviceId) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isEmpty()) {
            return List.of();
        }
        return assignmentRepository.findByDevice(deviceOpt.get());
    }

    // Get assignment history for an employee
    public List<Assignment> getAssignmentHistoryForEmployee(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return List.of();
        }
        return assignmentRepository.findByEmployee(employeeOpt.get());
    }

    // Get current assignment for a device
    public Assignment getCurrentAssignmentForDevice(Long deviceId) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isEmpty()) {
            return null;
        }
        return assignmentRepository.findByDeviceAndReturnedDateIsNull(deviceOpt.get());
    }

    // Get current assignment for an employee
    public Assignment getCurrentAssignmentForEmployee(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return null;
        }
        return assignmentRepository.findByEmployeeAndReturnedDateIsNull(employeeOpt.get());
    }

    // Get active assignment count
    public long getActiveAssignmentCount() {
        return assignmentRepository.countByReturnedDateIsNull();
    }

    // Get all assignments
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // Get assignment by ID
    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }
}