package com.customer.rewards.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.customer.rewards.util.RewardsCalculator;

class RewardsCalculatorTest {

	@Test
	void testRewardsAmountBelow50() {
		int points = RewardsCalculator.calculatePoints(40);
		assertEquals(0, points, "No points should be awarded below $50");
	}

	@Test
	void testRewardsAmountExactly50() {
		int points = RewardsCalculator.calculatePoints(50);
		assertEquals(0, points, "Exactly $50 should give 0 points");
	}

	@Test
	void testRewardsAmountBetween50And100() {
		int points = RewardsCalculator.calculatePoints(70);
		assertEquals(20, points, "Amount between $50 and $100 should give (amount - 50) points");
	}

	@Test
	void testRewardsAmountExactly100() {
		int points = RewardsCalculator.calculatePoints(100);
		assertEquals(50, points, "Exactly $100 should give 50 points");
	}

	@Test
	void testRewardsAmountAbove100() {
		int points = RewardsCalculator.calculatePoints(120);
		assertEquals(90, points, "Amount above $100 should give correct points");
	}

	@Test
	void testRewardsLargeAmount() {
		int points = RewardsCalculator.calculatePoints(250);
		assertEquals(350, points, "Large amount should calculate correctly");
	}
}

