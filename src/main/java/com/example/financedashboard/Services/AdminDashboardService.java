package com.example.financedashboard.Services;

import com.example.financedashboard.Entities.FinancialRecord;
import com.example.financedashboard.Enums.RecordType;
import com.example.financedashboard.Repositories.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final FinancialRecordRepository recordRepository;

    public Double getTotalIncome() {
        return recordRepository.findAll().stream()
                .filter(r -> r.getType() == RecordType.INCOME)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public Double getTotalExpenses() {
        return recordRepository.findAll().stream()
                .filter(r -> r.getType() == RecordType.EXPENSE)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public Double getNetBalance() {
        return getTotalIncome() - getTotalExpenses();
    }

    public Map<String, Double> getCategoryTotals() {
        return recordRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        FinancialRecord::getCategory,
                        Collectors.summingDouble(FinancialRecord::getAmount)
                ));
    }

    public Map<YearMonth, Double> getMonthlyTrend() {
        Map<YearMonth, Double> trend = new TreeMap<>();
        for (FinancialRecord r : recordRepository.findAll()) {
            YearMonth ym = YearMonth.from(r.getDate());
            trend.putIfAbsent(ym, 0.0);
            trend.put(ym, trend.get(ym) + (r.getType() == RecordType.INCOME ? r.getAmount() : -r.getAmount()));
        }
        return trend;
    }

    public List<FinancialRecord> getRecentActivity(int limit) {
        return recordRepository.findAll().stream()
                .sorted(Comparator.comparing(FinancialRecord::getDate).reversed())
                .limit(limit)
                .toList();
    }
}