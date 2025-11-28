package com.customer.rewards.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RewardsResponseDto {
    private Integer customerId;
    private String customerName;
    private Integer totalRewardPoints;
    private Integer totalTransactions;
    private List<TransactionDto> monthlyRewardTransactions;
}
