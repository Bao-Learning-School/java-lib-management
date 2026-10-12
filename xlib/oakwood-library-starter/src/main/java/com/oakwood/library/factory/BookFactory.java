package com.oakwood.library.factory;

import com.oakwood.library.model.Book;

/**
 * Creates common types of books with pre-configured loan durations.
 *
 * <p><b>Design Pattern: Factory Pattern (Creational)</b></p>
 *
 * <p>Why use the Factory Pattern here?
 * <ul>
 *   <li><b>Readability:</b> Replaces raw magic numbers with descriptive methods.</li>
 *   <li><b>Centralized Configuration:</b> Loan duration rules are managed in one place.</li>
 * </ul>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public final class BookFactory {

  /** Standard loan duration for general reading material (14 days). */
  public static final int STANDARD_LOAN_DAYS = 14;

  /** High-demand bestseller loan duration (7 days). */
  public static final int BESTSELLER_LOAN_DAYS = 7;

  /** Non-circulating or high-demand reference material loan duration (3 days). */
  public static final int REFERENCE_LOAN_DAYS = 3;

  /** Extended loan duration for young readers and children's books (21 days). */
  public static final int CHILDREN_LOAN_DAYS = 21;

  private BookFactory() {
    // Utility class - prevent instantiation
  }
  
  /**
   * Creates a regular fiction or non-fiction book with a 14-day loan period.
   *
   * @param title title of the book
   * @param author author of the book
   * @return newly instantiated Book
   */
  public static Book createStandardBook(String title, String author) {
    // TODO: Task 4.1 - Return a new Book with title, author, and STANDARD_LOAN_DAYS.
    return new Book(title, author, STANDARD_LOAN_DAYS); // Replace this line with your code
  }

  /**
   * Creates a popular bestseller with a shortened 7-day loan period.
   *
   * @param title title of the bestseller
   * @param author author of the bestseller
   * @return newly instantiated Book
   */
  public static Book createBestseller(String title, String author) {
    // TODO: Task 4.2 - Return a new Book with title, author, and BESTSELLER_LOAN_DAYS.
    return new Book(title, author, BESTSELLER_LOAN_DAYS); // Replace this line with your code
  }

  /**
   * Creates a reference or study book with a short 3-day loan period.
   *
   * @param title title of the reference book
   * @param author author of the reference book
   * @return newly instantiated Book
   */
  public static Book createReferenceBook(String title, String author) {
    // TODO: Task 4.3 - Return a new Book with title, author, and REFERENCE_LOAN_DAYS.
    return new Book(title, author, REFERENCE_LOAN_DAYS); // Replace this line with your code
  }

  /**
   * Creates a children's storybook with an extended 21-day loan period.
   *
   * @param title title of the children's book
   * @param author author of the children's book
   * @return newly instantiated Book
   */
  public static Book createChildrenBook(String title, String author) {
    // TODO: Task 4.4 - Return a new Book with title, author, and CHILDREN_LOAN_DAYS.
    return new Book(title, author, CHILDREN_LOAN_DAYS); // Replace this line with your code
  }
}
