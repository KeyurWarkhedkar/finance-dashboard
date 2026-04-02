package com.example.financedashboard.Repositories;

import com.example.financedashboard.Entities.FinancialRecord;
import com.example.financedashboard.Enums.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByTypeAndUser_Id(RecordType type, Long userId);

    List<FinancialRecord> findByCategoryAndUser_Id(String category, Long userId);

    List<FinancialRecord> findByDateBetweenAndUser_Id(LocalDate start, LocalDate end, Long userId);
}
