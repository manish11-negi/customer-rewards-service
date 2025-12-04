package com.customer.rewards.test.controller;


import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.customer.rewards.controller.RewardsController;
import com.customer.rewards.dto.RewardsResponseDto;
import com.customer.rewards.service.RewardsService;

@WebMvcTest(RewardsController.class)
public class RewardsControllerTest {
	 	
		@Autowired
	    private MockMvc mockMvc;
	    @MockitoBean
	    private RewardsService rewardsService;
	    
	    @Test
	    void testGetRewardsWithMonths() throws Exception {
	        RewardsResponseDto mockResponse = new RewardsResponseDto();
	        Mockito.when(rewardsService.getRewardsForCustomerWithMonths(eq(5), eq(3)))
	                .thenReturn(mockResponse);

	        mockMvc.perform(get("/api/rewards/5")
	                        .param("months", "3")
	                        .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
	    }
	    
	    @Test
	    void testGetRewardsWithDateRange() throws Exception {
	        RewardsResponseDto mockResponse = new RewardsResponseDto();
	        Mockito.when(rewardsService.getRewardsForCustomer(
	                eq(5),
	                eq(LocalDate.parse("2024-01-01")),
	                eq(LocalDate.parse("2024-02-01"))
	        )).thenReturn(mockResponse);

	        mockMvc.perform(get("/api/rewards/5")
	                        .param("start", "2024-01-01")
	                        .param("end", "2024-02-01")
	                        .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
	    }
	    
	    @Test
	    void testGetRewardsValidationFailure() throws Exception {

	        mockMvc.perform(get("/api/rewards/0")
	                        .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().is5xxServerError());
	    }
	    
}
