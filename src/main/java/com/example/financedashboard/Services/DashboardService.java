package com.example.financedashboard.Services;

import com.example.financedashboard.Entities.FinancialRecord;
import com.example.financedashboard.Entities.User;
import com.example.financedashboard.Enums.RecordType;
import com.example.financedashboard.Repositories.FinancialRecordRepository;
import com.example.financedashboard.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    // Total income
    public Double getTotalIncome() {
        Long userId = getCurrentUserId();
        return recordRepository.findByTypeAndUser_Id(RecordType.INCOME, userId)
                .stream()
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    // Total expenses
    public Double getTotalExpenses() {
        Long userId = getCurrentUserId();
        return recordRepository.findByTypeAndUser_Id(RecordType.EXPENSE, userId)
                .stream()
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    // Net balance
    public Double getNetBalance() {
        return getTotalIncome() - getTotalExpenses();
    }

    // Category-wise totals
    public Map<String, Double> getCategoryTotals() {
        List<FinancialRecord> allRecords = recordRepository.findAll();

        return allRecords.stream()
                .collect(Collectors.groupingBy(
                        FinancialRecord::getCategory,
                        Collectors.summingDouble(FinancialRecord::getAmount)
                ));
    }

    // Monthly trend: returns Map<YearMonth, Net Amount>
    public Map<YearMonth, Double> getMonthlyTrend() {
        List<FinancialRecord> allRecords = recordRepository.findAll();

        Map<YearMonth, Double> trend = new TreeMap<>();

        for (FinancialRecord r : allRecords) {
            YearMonth ym = YearMonth.from(r.getDate());
            trend.putIfAbsent(ym, 0.0);
            if (r.getType() == RecordType.INCOME) {
                trend.put(ym, trend.get(ym) + r.getAmount());
            } else {
                trend.put(ym, trend.get(ym) - r.getAmount());
            }
        }
        return trend;
    }

    // Recent activity (last N records)
    public List<FinancialRecord> getRecentActivity(int limit) {
        List<FinancialRecord> allRecords = recordRepository.findAll();
        return allRecords.stream()
                .sorted(Comparator.comparing(FinancialRecord::getDate).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        Object principal = auth.getPrincipal();

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername(); // usually email in our setup
        } else {
            email = principal.toString();
        }

        // fetch User entity from DB using email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        return user.getId();
    }
}
