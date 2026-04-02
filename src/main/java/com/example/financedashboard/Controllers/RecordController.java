package com.example.financedashboard.Controllers;

import com.example.financedashboard.DTOs.CreateRecordRequest;
import com.example.financedashboard.Entities.FinancialRecord;
import com.example.financedashboard.Enums.RecordType;
import com.example.financedashboard.Services.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    // Create a new record
    @PostMapping
    public ResponseEntity<FinancialRecord> createRecord(
            @Valid @RequestBody CreateRecordRequest request,
            @RequestParam String userEmail // For now, pass email as param
    ) {
        FinancialRecord record = recordService.createRecord(request, userEmail);
        return ResponseEntity.ok(record);
    }

    // Get records with optional filters
    @GetMapping
    public ResponseEntity<List<FinancialRecord>> getRecords(
            @RequestParam(required = false) RecordType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<FinancialRecord> records = recordService.getRecords(type, category, startDate, endDate);
        return ResponseEntity.ok(records);
    }

    // Update record
    @PutMapping("/{id}")
    public ResponseEntity<FinancialRecord> updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody CreateRecordRequest request
    ) {
        FinancialRecord record = recordService.updateRecord(id, request);
        return ResponseEntity.ok(record);
    }

    // Delete record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
