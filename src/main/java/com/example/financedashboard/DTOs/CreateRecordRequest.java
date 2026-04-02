package com.example.financedashboard.DTOs;

import com.example.financedashboard.Enums.RecordType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateRecordRequest {

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    private RecordType type;

    @NotBlank
    private String category;

    @NotNull
    private LocalDate date;

    private String description;
}
