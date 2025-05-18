package org.example.booksy.service;

import org.example.booksy.repository.IAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    @Autowired
    private IAppointmentRepository appointmentRepository;

    public Map<String, Long> getTopCities() {
        List<Object[]> results = appointmentRepository.countAppointmentsByCity();
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : results) {
            map.put((String) row[0], (Long) row[1]);
        }
        return map;
    }

    public Map<String, Long> getTopServices() {
        List<Object[]> results = appointmentRepository.countAppointmentsByService();
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : results) {
            map.put((String) row[0], (Long) row[1]);
        }
        return map;
    }

    public Map<String, Double> getMonthlyRevenue(Long providerUserId) {
        List<Object[]> results = appointmentRepository.findMonthlyRevenueByProvider(providerUserId);
        Map<String, Double> map = new LinkedHashMap<>();
        for (Object[] row : results) {
            map.put((String) row[0], (Double) row[1]);
        }
        return map;
    }

    public double getTotalIncome(Long providerUserId) {
        Double total = appointmentRepository.findTotalIncomeByProvider(providerUserId);
        return total != null ? total : 0.0;
    }

}