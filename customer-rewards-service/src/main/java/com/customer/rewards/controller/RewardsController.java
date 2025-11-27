package com.customer.rewards.controller;

import java.time.LocalDate;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.customer.rewards.dto.RewardsResponseDto;
import com.customer.rewards.service.RewardsService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rewards")
@Validated
@Slf4j
public class RewardsController {
	
   RewardsService rewardsService;
   
   public RewardsController(RewardsService rewardsService) {
       this.rewardsService = rewardsService;
   }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RewardsResponseDto> getRewards(
            @PathVariable @Min(1) Integer customerId,
            @RequestParam(required = false) Integer months,
            @RequestParam(required = false) LocalDate start,
            @RequestParam(required = false) LocalDate end
    ) {
        log.info("Request: method: getRewards customerId={}, months={}, start={}, end={}", customerId, months, start, end);
        RewardsResponseDto resp;
        if (start != null && end != null) {
            resp = rewardsService.getRewardsForCustomer(customerId, start, end);
        } else {
            resp = rewardsService.getRewardsForCustomerWithMonths(customerId, months);
        }
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{customerId}/async")
    public ResponseEntity<RewardsResponseDto> getRewardsAsync(
            @PathVariable @Min(1) Integer customerId,
            @RequestParam(required = false) Integer months
    ) {
        log.info("Async request getRewardsAsync for customer {} months={}", customerId, months);
        RewardsResponseDto responseDto=rewardsService.getRewardsForCustomerAsync(customerId, months).join();
        return ResponseEntity.ok(responseDto);
                
    }
}
