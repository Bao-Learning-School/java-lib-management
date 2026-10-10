package com.oakwood.library.fee;

/**
 * <h2>Design Pattern: Strategy Pattern (Behavioral)</h2>
 * <p>
 * The <b>FeeCalculator</b> interface defines a strategy for calculating late fees
 * when books are returned after their due date.
 * </p>
 *
 * <h3>Why use the Strategy Pattern here?</h3>
 * <ul>
 *   <li><b>Open/Closed Principle:</b> We can introduce new fee calculation rules
 *       (e.g., student discount, weekend grace period, holiday waiver) without
 *       modifying the existing {@code Library} or {@code Book} classes.</li>
 *   <li><b>Runtime Flexibility:</b> The library can swap fee policies on the fly
 *       using {@code library.setFeeCalculator(new GracePeriodFeeCalculator())}.</li>
 * </ul>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public interface FeeCalculator {

    /**
     * Calculates the late fee owed based on how many days the book was kept
     * versus its allowed loan period.
     *
     * @param daysKept the total number of days the patron possessed the book
     * @param dueDays  the allowed loan duration in days before it becomes overdue
     * @return the total fee in dollars (0.0 if returned on time or within grace)
     */
    double calculateFee(int daysKept, int dueDays);

    /**
     * Provides a kid-friendly description of this calculation policy.
     *
     * @return policy description
     */
    String getDescription();
}
