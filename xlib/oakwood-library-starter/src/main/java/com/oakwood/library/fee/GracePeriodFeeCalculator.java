package com.oakwood.library.fee;

/**
 * Calculates late fees with a forgiving grace period before daily fines apply.
 *
 * <pre>
 * Days Past Grace = daysKept - (dueDays + gracePeriodDays)
 * If Days Past Grace &gt; 0, Total Fine = Days Past Grace * dailyRate
 * Otherwise, Total Fine = $0.00
 * </pre>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public class GracePeriodFeeCalculator implements FeeCalculator {

  /** Default grace period: 2 extra days without penalty. */
  public static final int DEFAULT_GRACE_DAYS = 2;

  /** Default daily rate after grace expires ($0.75). */
  public static final double DEFAULT_DAILY_RATE = 0.75;

  private final int gracePeriodDays;
  private final double dailyRate;

  /**
   * Creates a grace-period fee calculator with default 2 grace days and $0.75/day thereafter.
   */
  public GracePeriodFeeCalculator() {
    this(DEFAULT_GRACE_DAYS, DEFAULT_DAILY_RATE);
  }

  /**
   * Creates a grace-period fee calculator with custom settings.
   *
   * @param gracePeriodDays number of buffer days before any fine starts
   * @param dailyRate rate charged per day after grace period ends
   */
  public GracePeriodFeeCalculator(int gracePeriodDays, double dailyRate) {
    if (gracePeriodDays < 0) {
      throw new IllegalArgumentException("Grace period days cannot be negative.");
    }
    if (dailyRate < 0) {
      throw new IllegalArgumentException("Daily rate cannot be negative.");
    }
    this.gracePeriodDays = gracePeriodDays;
    this.dailyRate = dailyRate;
  }

  @Override
  public double calculateFee(int daysKept, int dueDays) {
    // TODO: Task 3.2 - Implement forgiving grace period late fee calculation.
    // 1. Calculate allowedDays = dueDays + gracePeriodDays.
    // 2. If daysKept <= allowedDays, return 0.0 (still inside free grace window!).
    // 3. Otherwise, calculate taxableDays = daysKept - allowedDays.
    // 4. Return taxableDays * dailyRate.
    return 0.0; // Replace this line with your code
  }

  @Override
  public String getDescription() {
    return String.format(
        "Grace Policy: %d free grace days, then $%.2f/day.", gracePeriodDays, dailyRate);
  }

  /**
   * Gets the number of grace days allowed.
   *
   * @return grace period days
   */
  public int getGracePeriodDays() {
    return gracePeriodDays;
  }

  /**
   * Gets the daily rate charged after grace period ends.
   *
   * @return daily rate in dollars
   */
  public double getDailyRate() {
    return dailyRate;
  }
}
