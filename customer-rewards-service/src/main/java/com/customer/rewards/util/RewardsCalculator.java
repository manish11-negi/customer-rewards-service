package com.customer.rewards.util;

public final class RewardsCalculator {
    private RewardsCalculator() {}

    public static int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (int)((amount - 100) * 2);
            amount = 100;
        }
        if (amount > 50) {
            points += (int)((amount - 50) * 1);
        }
        return points;
    }
}
