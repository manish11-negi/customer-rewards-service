package com.customer.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter @Setter
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
