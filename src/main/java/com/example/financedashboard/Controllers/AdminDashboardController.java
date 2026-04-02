package com.example.financedashboard.Controllers;

import com.example.financedashboard.Services.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin-dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/total-income")
    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    public Double getTotalIncome() { return adminDashboardService.getTotalIncome(); }

    @GetMapping("/total-expenses")
    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    public Double getTotalExpenses() { return adminDashboardService.getTotalExpenses(); }

    @GetMapping("/net-balance")
    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    public Double getNetBalance() { return adminDashboardService.getNetBalance(); }
}