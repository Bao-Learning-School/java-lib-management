package com.oakwood.library.fee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GracePeriodFeeCalculator Strategy Tests")
class GracePeriodFeeCalculatorTest {

    @Test
    @DisplayName("Should charge $0.00 when returned within due days")
    void testWithinDueDays() {
        GracePeriodFeeCalculator calculator = new GracePeriodFeeCalculator(2, 0.75);

        assertEquals(0.0, calculator.calculateFee(12, 14));
        assertEquals(0.0, calculator.calculateFee(14, 14));
    }

    @Test
    @DisplayName("Should charge $0.00 when returned within the grace period")
    void testWithinGracePeriod() {
        // Due 14 days + 2 grace days = up to 16 days free
        GracePeriodFeeCalculator calculator = new GracePeriodFeeCalculator(2, 0.75);

        assertEquals(0.0, calculator.calculateFee(15, 14));
        assertEquals(0.0, calculator.calculateFee(16, 14));
    }

    @Test
    @DisplayName("Should charge daily rate for days beyond the grace period")
    void testBeyondGracePeriod() {
        // Due 14 days + 2 grace days = 16 days free. Kept 18 days -> 2 taxable days * 0.75 = $1.50
        GracePeriodFeeCalculator calculator = new GracePeriodFeeCalculator(2, 0.75);

        double fee = calculator.calculateFee(18, 14);
        assertEquals(1.50, fee, 0.001);
    }

    @Test
    @DisplayName("Should reject negative parameters in constructor")
    void testNegativeParameters() {
        assertThrows(IllegalArgumentException.class, () -> new GracePeriodFeeCalculator(-1, 0.50));
        assertThrows(IllegalArgumentException.class, () -> new GracePeriodFeeCalculator(2, -0.50));
    }
}
