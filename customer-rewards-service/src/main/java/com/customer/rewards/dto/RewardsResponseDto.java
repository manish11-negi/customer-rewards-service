package com.customer.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RewardsResponseDto {
    private Integer customerId;
    private String customerName;
    private Integer totalPoints;
    private List<TransactionDto> transactions;
}
