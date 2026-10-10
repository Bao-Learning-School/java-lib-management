package com.oakwood.library.event;

import com.oakwood.library.model.Book;
import com.oakwood.library.model.Patron;

/**
 * Translates library events into readable console output for students.
 *
 * <p><b>Design Pattern: Concrete Observer</b></p>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public class ConsoleStoryNarrator implements LibraryEventListener {

  /**
   * Constructs a default console story narrator.
   */
  public ConsoleStoryNarrator() {
    // Default constructor
  }

  @Override
  public void onBookAdded(Book book) {
    System.out.println("Added to library: " + book);
  }

  @Override
  public void onBookCheckedOut(Patron patron, Book book) {
    System.out.println("SUCCESS: " + patron.getName() + " checked out " + book);
  }

  @Override
  public void onCheckoutFailed(Patron patron, String title, String reason) {
    System.out.println("FAIL: " + reason);
  }

  @Override
  public void onBookReturned(Patron patron, Book book, double lateFee) {
    if (lateFee > 0.0) {
      System.out.printf(
          "SUCCESS: %s returned %s (Late fee assessed: $%.2f)%n",
          patron.getName(), book, lateFee);
    } else {
      System.out.println("SUCCESS: " + patron.getName() + " returned " + book);
    }
  }

  @Override
  public void onReturnFailed(Patron patron, String title, String reason) {
    System.out.println("ERROR: " + reason);
  }
}
