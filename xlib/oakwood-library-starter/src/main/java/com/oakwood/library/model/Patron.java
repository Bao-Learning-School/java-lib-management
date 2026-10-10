package com.oakwood.library.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Models a library member who can borrow books up to a fixed limit.
 *
 * <p>Key Concepts for Students:
 * <ul>
 *   <li><b>Aggregation:</b> Patron holds a collection of {@link Book} references.</li>
 *   <li><b>Passing References:</b> Java passes pointers to objects on the Heap.</li>
 *   <li><b>Borrow Limit:</b> Invariant rule preventing more than 3 books per patron.</li>
 * </ul>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public class Patron {

  /** Maximum number of books any patron may hold at the same time. */
  public static final int MAX_BORROW_LIMIT = 3;

  private final String name;
  private final List<Book> borrowedBooks;

  /**
   * Creates a new library Patron with an empty list of borrowed books.
   *
   * @param name the patron's name
   */
  public Patron(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Patron name cannot be null or empty.");
    }
    this.name = name.trim();
    this.borrowedBooks = new ArrayList<>();
  }

  /**
   * Gets the patron's name.
   *
   * @return the name of the patron
   */
  public String getName() {
    return name;
  }

  /**
   * Checks if the patron is eligible to borrow another book.
   *
   * @return {@code true} if current borrowed count &lt; 3; {@code false} otherwise
   */
  public boolean canBorrow() {
    // TODO: Task 2.1 - Check if patron has room to borrow more books.
    // Rule: A patron can borrow if borrowedBooks.size() is strictly less than MAX_BORROW_LIMIT.
    return false; // Replace this line with your code
  }

  /**
   * Receives a book reference and appends it to the patron's borrowed list.
   *
   * @param book the book to borrow (must not be null)
   * @throws IllegalStateException if patron reached borrow limit
   * @throws IllegalArgumentException if book is null or duplicate
   */
  public void receiveBook(Book book) {
    // TODO: Task 2.2 - Implement book receiving logic:
    // 1. If 'book' is null, throw IllegalArgumentException.
    // 2. If !canBorrow(), throw IllegalStateException.
    // 3. If borrowedBooks.contains(book), throw IllegalArgumentException.
    // 4. Finally, add the book reference to the 'borrowedBooks' list!
  }

  /**
   * Relinquishes a borrowed book from the patron's possession.
   *
   * @param book the book to return
   * @return {@code true} if the book was held and successfully removed; {@code false} otherwise
   */
  public boolean returnBook(Book book) {
    // TODO: Task 2.3 - Remove book from 'borrowedBooks' and return whether it was removed.
    // Hint: ArrayList has a .remove(Object) method that returns boolean!
    return false; // Replace this line with your code
  }

  /**
   * Checks whether the patron is currently holding a specific book.
   *
   * @param book the book to check
   * @return {@code true} if held, {@code false} otherwise
   */
  public boolean hasBook(Book book) {
    // TODO: Task 2.4 - Return whether 'borrowedBooks' contains the given book.
    return false; // Replace this line with your code
  }

  /**
   * Returns an unmodifiable view of the currently borrowed books.
   *
   * @return unmodifiable list of borrowed books
   */
  public List<Book> getBorrowedBooks() {
    // TODO: Task 2.5 - Protect encapsulation by returning an unmodifiable list.
    // Hint: Use Collections.unmodifiableList(borrowedBooks);
    return null; // Replace this line with your code
  }

  /**
   * Returns how many books the patron currently holds.
   *
   * @return count of borrowed books
   */
  public int getBorrowCount() {
    // TODO: Task 2.6 - Return the size of the 'borrowedBooks' list.
    return 0; // Replace this line with your code
  }

  /**
   * Displays all books currently in the patron's possession to standard output.
   */
  public void showBorrowedBooks() {
    System.out.println(name + "'s Borrowed Books: " + borrowedBooks);
  }

  @Override
  public String toString() {
    return "Patron{" + "name='" + name + '\'' + ", borrowedCount=" + borrowedBooks.size() + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Patron patron)) {
      return false;
    }
    return name.equalsIgnoreCase(patron.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name.toLowerCase());
  }
}
