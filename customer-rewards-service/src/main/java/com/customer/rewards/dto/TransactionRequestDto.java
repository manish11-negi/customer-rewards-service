package com.customer.rewards.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto {
    @NotNull @Min(1)
    private Integer customerId;

    @NotNull @Min(0)
    private Double amount;

    @NotNull
    private LocalDate date;
}
