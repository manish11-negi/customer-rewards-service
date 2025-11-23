package com.customer.rewards.controller;

import com.customer.rewards.dto.RewardsResponseDto;
import com.customer.rewards.service.RewardsService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/rewards")
@Validated
@Slf4j
public class RewardsController {
   @Autowired
   RewardsService rewardsService;

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
    public CompletableFuture<ResponseEntity<RewardsResponseDto>> getRewardsAsync(
            @PathVariable @Min(1) Integer customerId,
            @RequestParam(required = false) Integer months
    ) {
        log.info("Async request getRewardsAsync for customer {} months={}", customerId, months);
        return rewardsService.getRewardsForCustomerAsync(customerId, months)
                .thenApply(ResponseEntity::ok);
    }
}
