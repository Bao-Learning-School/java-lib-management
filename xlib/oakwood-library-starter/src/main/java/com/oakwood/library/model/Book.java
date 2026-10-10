package com.oakwood.library.model;

import java.util.Objects;

/**
 * Models a Book as a Data Carrier in the Oakwood library system.
 *
 * <p>Key Concepts for Students:
 * <ul>
 *   <li><b>Encapsulation:</b> Fields are private and accessed through methods.</li>
 *   <li><b>State Mutation:</b> {@link #setAvailable(boolean)} mutates internal state.</li>
 *   <li><b>Heap Allocation:</b> Instances live in the Java Heap memory.</li>
 * </ul>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public class Book {

  /** Default loan duration in days (2 weeks). */
  public static final int DEFAULT_DUE_DAYS = 14;

  private final String title;
  private final String author;
  private boolean isAvailable;
  private int dueDays;~
`
  /**
   * Creates a new Book with the default loan period of 14 days.
   *
   * @param title the title of the book
   * @param author the author who wrote the book
   */
  public Book(String title, String author) {
    this(title, author, DEFAULT_DUE_DAYS);
  }

  /**
   * Creates a new Book with a custom loan duration.
   *
   * @param title the title of the book
   * @param author the author of the book
   * @param dueDays how many days a patron can keep this book before it is overdue
   */
  public Book(String title, String author, int dueDays) {
    if (title == null || title.trim().isEmpty()) {
      throw new IllegalArgumentException("Book title cannot be null or empty.");
    }
    if (author == null || author.trim().isEmpty()) {
      throw new IllegalArgumentException("Book author cannot be null or empty.");
    }
    if (dueDays <= 0) {
      throw new IllegalArgumentException("Due days must be greater than 0.");
    }
    this.title = title.trim();
    this.author = author.trim();
    this.isAvailable = true;
    this.dueDays = dueDays;
  }

  /**
   * Gets the book's title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the book's author.
   *
   * @return the author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Checks if the book is currently on the shelf and ready to borrow.
   *
   * @return {@code true} if available; {@code false} if already borrowed
   */
  public boolean isAvailable() {
    // TODO: Task 1.1 - Return the current availability status of this book.
    // Hint: Look at the private field 'isAvailable'.
    return false; // Replace this line with your code
  }

  /**
   * Updates the availability status of the book.
   *
   * @param available {@code true} when returned to shelf, {@code false} when checked out
   */
  public void setAvailable(boolean available) {
    // TODO: Task 1.2 - Mutate internal 'isAvailable' field to match the parameter.
    // Hint: Use 'this.isAvailable = available;'
  }

  /**
   * Gets the allowed loan period in days.
   *
   * @return allowed loan days
   */
  public int getDueDays() {
    return dueDays;
  }

  /**
   * Sets a new allowed loan period in days.
   *
   * @param dueDays positive number of allowed loan days
   */
  public void setDueDays(int dueDays) {
    // TODO: Task 1.3 - Validate that dueDays is greater than 0.
    // If dueDays <= 0, throw new IllegalArgumentException("Due days must be greater than 0.");
    // Otherwise, update 'this.dueDays'.
  }

  /**
   * Returns a human-friendly string formatted as {@code 'Title' by Author}.
   *
   * @return formatted string representation
   */
  @Override
  public String toString() {
    // TODO: Task 1.4 - Return the book formatted as: "'" + title + "' by " + author
    // Example: "'The Hobbit' by J.R.R. Tolkien"
    return ""; // Replace this line with your code
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Book other)) {
      return false;
    }
    return title.equalsIgnoreCase(other.title) && author.equalsIgnoreCase(other.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title.toLowerCase(), author.toLowerCase());
  }
}
