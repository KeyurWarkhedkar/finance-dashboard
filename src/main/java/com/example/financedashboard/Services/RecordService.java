package com.example.financedashboard.Services;

import com.example.financedashboard.DTOs.CreateRecordRequest;
import com.example.financedashboard.Entities.FinancialRecord;
import com.example.financedashboard.Entities.User;
import com.example.financedashboard.Enums.RecordType;
import com.example.financedashboard.Repositories.FinancialRecordRepository;
import com.example.financedashboard.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    // Create a new record
    public FinancialRecord createRecord(CreateRecordRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        FinancialRecord record = FinancialRecord.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .category(request.getCategory())
                .date(request.getDate())
                .description(request.getDescription())
                .user(user)
                .build();

        return recordRepository.save(record);
    }

    // Get all records (optionally filtered)
    public List<FinancialRecord> getRecords(
            RecordType type,
            String category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        Long userId = getCurrentUserId();
        if (type != null) {
            return recordRepository.findByTypeAndUser_Id(type, userId);
        } else if (category != null) {
            return recordRepository.findByCategoryAndUser_Id(category, userId);
        } else if (startDate != null && endDate != null) {
            return recordRepository.findByDateBetweenAndUser_Id(startDate, endDate, userId);
        } else {
            return recordRepository.findAll();
        }
    }

    // Update a record
    public FinancialRecord updateRecord(Long id, CreateRecordRequest request) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found"));

        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setDescription(request.getDescription());

        return recordRepository.save(record);
    }

    // Delete a record
    public void deleteRecord(Long id) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found"));
        recordRepository.delete(record);
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
