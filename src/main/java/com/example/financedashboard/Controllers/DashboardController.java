package com.example.financedashboard.Controllers;

import com.example.financedashboard.Entities.FinancialRecord;
import com.example.financedashboard.Services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/total-income")
    public ResponseEntity<Double> getTotalIncome() {
        return ResponseEntity.ok(dashboardService.getTotalIncome());
    }

    @GetMapping("/total-expenses")
    public ResponseEntity<Double> getTotalExpenses() {
        return ResponseEntity.ok(dashboardService.getTotalExpenses());
    }

    @GetMapping("/net-balance")
    public ResponseEntity<Double> getNetBalance() {
        return ResponseEntity.ok(dashboardService.getNetBalance());
    }

    @GetMapping("/category-totals")
    public ResponseEntity<Map<String, Double>> getCategoryTotals() {
        return ResponseEntity.ok(dashboardService.getCategoryTotals());
    }

    @GetMapping("/monthly-trend")
    public ResponseEntity<Map<YearMonth, Double>> getMonthlyTrend() {
        return ResponseEntity.ok(dashboardService.getMonthlyTrend());
    }

    @GetMapping("/recent-activity")
    public ResponseEntity<List<FinancialRecord>> getRecentActivity(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(dashboardService.getRecentActivity(limit));
    }
}
