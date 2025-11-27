package com.customer.rewards.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RewardsResponseDto {
    private Integer customerId;
    private String customerName;
    private Integer totalRewardPoints;
    private Integer totalTransaction;
    private List<TransactionDto> monthlyRewardTransactions;
}
