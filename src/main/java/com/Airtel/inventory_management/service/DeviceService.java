package com.Airtel.inventory_management.service;

import com.Airtel.inventory_management.model.Device;
import com.Airtel.inventory_management.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Device saveDevice(Device device) {
        if (device.getId() == null) {
            device.setCreatedAt(LocalDateTime.now());
        }
        device.setUpdatedAt(LocalDateTime.now());
        return deviceRepository.save(device);
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    public Device getDeviceBySerialNumber(String serialNumber) {
        return deviceRepository.findBySerialNumber(serialNumber);
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    public boolean isSerialNumberExists(String serialNumber) {
        return deviceRepository.existsBySerialNumber(serialNumber);
    }

    public List<Device> getDevicesByStatus(String status) {
        return deviceRepository.findByStatus(status);
    }

    public List<Device> getDevicesByType(String deviceType) {
        return deviceRepository.findByDeviceType(deviceType);
    }

    public List<Device> getDevicesByCondition(String deviceCondition) {
        return deviceRepository.findByDeviceCondition(deviceCondition);
    }

    public List<Device> searchDevices(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllDevices();
        }
        return deviceRepository.searchDevices(keyword);
    }

    public Device updateDeviceStatus(Long id, String status) {
        Optional<Device> deviceOpt = deviceRepository.findById(id);
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            device.setStatus(status);
            device.setUpdatedAt(LocalDateTime.now());
            return deviceRepository.save(device);
        }
        return null;
    }

    public Device updateDeviceCondition(Long id, String deviceCondition) {
        Optional<Device> deviceOpt = deviceRepository.findById(id);
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            device.setDeviceCondition(deviceCondition);
            device.setUpdatedAt(LocalDateTime.now());
            return deviceRepository.save(device);
        }
        return null;
    }

    public List<Device> getAvailableDevices() {
        return deviceRepository.findByStatus("Available");
    }

    public long getTotalDeviceCount() {
        return deviceRepository.count();
    }

    public long getAvailableDeviceCount() {
        return deviceRepository.countByStatus("Available");
    }

    public long getAssignedDeviceCount() {
        return deviceRepository.countByStatus("Assigned");
    }

    public long getRepairDeviceCount() {
        return deviceRepository.countByStatus("Under Repair");
    }

    public long getLaptopCount() {
        return deviceRepository.countByDeviceType("Laptop");
    }

    public long getDesktopCount() {
        return deviceRepository.countByDeviceType("Desktop");
    }

    public long getMobileCount() {
        return deviceRepository.countByDeviceType("Mobile");
    }
}