package com.oakwood.library.fee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StandardFeeCalculator Strategy Tests")
class StandardFeeCalculatorTest {

    @Test
    @DisplayName("Should charge $0.00 when book is returned on time or early")
    void testNoFeeWhenReturnedOnTime() {
        StandardFeeCalculator calculator = new StandardFeeCalculator();

        assertEquals(0.0, calculator.calculateFee(10, 14));
        assertEquals(0.0, calculator.calculateFee(14, 14));
    }

    @Test
    @DisplayName("Should calculate fine based on default $0.50 per overdue day")
    void testStandardLateFeeCalculation() {
        StandardFeeCalculator calculator = new StandardFeeCalculator();

        // 18 days kept with 14 due days -> 4 days overdue * $0.50 = $2.00
        double fee = calculator.calculateFee(18, 14);
        assertEquals(2.00, fee, 0.001);
    }

    @Test
    @DisplayName("Should support custom daily rate")
    void testCustomDailyRate() {
        StandardFeeCalculator calculator = new StandardFeeCalculator(1.00);

        // 5 days overdue * $1.00 = $5.00
        double fee = calculator.calculateFee(19, 14);
        assertEquals(5.00, fee, 0.001);
    }

    @Test
    @DisplayName("Should reject negative daily rate")
    void testNegativeRateRejection() {
        assertThrows(IllegalArgumentException.class, () -> new StandardFeeCalculator(-0.25));
    }
}
