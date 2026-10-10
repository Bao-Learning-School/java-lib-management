package com.oakwood.library.fee;

/**
 * Calculates late fees using a constant daily overdue rate.
 *
 * <pre>
 * Overdue Days = daysKept - dueDays
 * Total Fine   = Overdue Days * dailyRate
 * </pre>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public class StandardFeeCalculator implements FeeCalculator {

  /** Default rate charged per overdue day ($0.50). */
  public static final double DEFAULT_DAILY_RATE = 0.50;

  private final double dailyRate;

  /**
   * Creates a standard fee calculator with the default rate of $0.50/day.
   */
  public StandardFeeCalculator() {
    this(DEFAULT_DAILY_RATE);
  }

  /**
   * Creates a standard fee calculator with a custom daily rate.
   *
   * @param dailyRate fee in dollars charged for each day past due date
   */
  public StandardFeeCalculator(double dailyRate) {
    if (dailyRate < 0) {
      throw new IllegalArgumentException("Daily rate cannot be negative.");
    }
    this.dailyRate = dailyRate;
  }

  @Override
  public double calculateFee(int daysKept, int dueDays) {
    if (daysKept <= dueDays) {
      return 0.0;
    }
    int daysOverdue = daysKept - dueDays;
    return daysOverdue * dailyRate;
  }

  @Override
  public String getDescription() {
    return String.format("Standard Policy: $%.2f charged for each day overdue.", dailyRate);
  }

  /**
   * Gets the daily fee rate charged per overdue day.
   *
   * @return daily fee rate in dollars
   */
  public double getDailyRate() {
    return dailyRate;
  }
}
