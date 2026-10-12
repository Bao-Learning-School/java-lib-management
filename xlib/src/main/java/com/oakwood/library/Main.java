package com.oakwood.library;

import com.oakwood.library.factory.BookFactory;
import com.oakwood.library.fee.GracePeriodFeeCalculator;
import com.oakwood.library.model.Book;
import com.oakwood.library.model.Patron;
import com.oakwood.library.service.Library;

/**
 * Runs an educational interactive simulation of Oakwood Public Library.
 *
 * <p>Demonstrated OOP Concepts:
 * <ol>
 *   <li><b>Instantiation:</b> Objects created on the Java Heap.</li>
 *   <li><b>Association &amp; Reference Passing:</b> Handing book references to patrons.</li>
 *   <li><b>State Mutation:</b> Changing availability from true to false.</li>
 *   <li><b>Collision Detection:</b> Two patrons wanting the same book.</li>
 *   <li><b>Borrow Limit Enforcement:</b> Guarding against borrowing more than 3 books.</li>
 *   <li><b>Return &amp; Late Fees:</b> Strategy Pattern fine calculation.</li>
 *   <li><b>Garbage Collection:</b> Local objects falling out of scope.</li>
 * </ol>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public class Main {

  /**
   * Default constructor for Main driver.
   */
  public Main() {
    // Driver entry point
  }

  /**
   * Main execution entry point.
   *
   * @param args command-line arguments (unused)
   */
  public static void main(String[] args) {
    System.out.println("================================================================");
    System.out.println("   WELCOME TO OAKWOOD PUBLIC LIBRARY - OBJECT-ORIENTED JOURNEY  ");
    System.out.println("================================================================\n");

    // -------------------------------------------------------------
    // Step 1: Morning Setup (Heap Memory Allocation)
    // -------------------------------------------------------------
    System.out.println("--- 1. MORNING SETUP: Opening Oakwood Library ---");
    System.out.println("[Concept: Instantiation - Memory allocated on Heap for catalog]");
    Library oakwood = new Library();
    Patron alex = new Patron("Alex");
    oakwood.addListener(alex);

    Book javaBook = new Book("Effective Java", "Joshua Bloch");
    Book duneBook = new Book("Dune", "Frank Herbert");
    Book hobbitBook = BookFactory.createStandardBook("The Hobbit", "J.R.R. Tolkien");
    Book book1984 = BookFactory.createBestseller("1984", "George Orwell");

    oakwood.addBook(javaBook);
    oakwood.addBook(duneBook);
    oakwood.addBook(hobbitBook);
    oakwood.addBook(book1984);

    // -------------------------------------------------------------
    // Step 2: Patron Registration
    // -------------------------------------------------------------
    System.out.println("\n--- 2. 10:00 AM: Alex registers for a card ---");
    System.out.println("[Concept: Patron Instantiation - Alex sits in memory with empty list]");

    // -------------------------------------------------------------
    // Step 3: First Checkout (Passing Reference & State Mutation)
    // -------------------------------------------------------------
    System.out.println("\n--- 3. 10:15 AM: Alex checks out Dune ---");
    System.out.println("[Concept: Reference Passing & State Mutation - availability changes]");
    oakwood.checkoutBook("Dune", alex);
    alex.showBorrowedBooks();

    // -------------------------------------------------------------
    // Step 4: Collision Detection (State Rejection)
    // -------------------------------------------------------------
    System.out.println("\n--- 4. 11:00 AM: Maya tries to borrow Dune ---");
    System.out.println("[Concept: Collision Detection - unavailable book request rejected]");
    Patron maya = new Patron("Maya");
    oakwood.addListener(maya);
    oakwood.checkoutBook("Dune", maya);
    maya.showBorrowedBooks();

    // -------------------------------------------------------------
    // Step 5: Borrow Limit Enforcement (Business Rule)
    // -------------------------------------------------------------
    System.out.println("\n--- 5. 11:30 AM: Alex tests the borrow limit (Max 3 books) ---");
    System.out.println("[Concept: Encapsulated Business Rules - Patron guards limit]");
    oakwood.checkoutBook("Effective Java", alex);
    oakwood.checkoutBook("The Hobbit", alex);
    alex.showBorrowedBooks();

    System.out.println("Alex attempts to borrow a 4th book ('1984'):");
    oakwood.checkoutBook("1984", alex);

    // -------------------------------------------------------------
    // Step 6: Return Logic & Late Fees (Strategy Pattern)
    // -------------------------------------------------------------
    System.out.println("\n--- 6. 2:00 PM: Alex returns Dune (overdue by 4 days) ---");
    System.out.println("[Concept: Strategy Pattern - Standard fee calculation for overdue]");
    // Dune dueDays is 14; Alex kept it for 18 days (4 days overdue * $0.50 = $2.00)
    oakwood.returnBook("Dune", alex, 18);
    alex.showBorrowedBooks();

    // -------------------------------------------------------------
    // Step 7: Maya Claims Dune (State Reset Hand-Off)
    // -------------------------------------------------------------
    System.out.println("\n--- 7. 2:05 PM: Maya claims Dune now that it is back ---");
    System.out.println("[Concept: State Reset - Book availability restored and transferred]");
    oakwood.checkoutBook("Dune", maya);
    maya.showBorrowedBooks();

    // -------------------------------------------------------------
    // Step 8: Swapping Strategy at Runtime (Polymorphism)
    // -------------------------------------------------------------
    System.out.println("\n--- 8. 3:00 PM: Oakwood switches to Grace Period Fee Policy ---");
    System.out.println("[Concept: Strategy Polymorphism - Swapping calculator at runtime]");
    oakwood.setFeeCalculator(new GracePeriodFeeCalculator(2, 0.75));
    System.out.println("Active Policy: " + oakwood.getFeeCalculator().getDescription());

    // Maya returns Dune after 15 days (1 day past 14, but within 2 grace days -> $0.00)
    System.out.println("Maya returns Dune after 15 days (within 2-day grace):");
    oakwood.returnBook("Dune", maya, 15);

    // -------------------------------------------------------------
    // Step 9: End of Day & Garbage Collection Demo
    // -------------------------------------------------------------
    System.out.println("\n--- 9. END OF DAY: Closing the library & Garbage Collection ---");
    System.out.println("[Concept: Lifecycle - Local reference drops to 0, eligible for GC]");
    simulateGuestVisit();

    System.out.println("\n================================================================");
    System.out.println("   ALL DEMONSTRATIONS COMPLETED SUCCESSFULLY! HAPPY CODING!     ");
    System.out.println("================================================================");
  }

  /**
   * Demonstrates object lifecycle and Garbage Collection.
   */
  public static void simulateGuestVisit() {
    Patron guest = new Patron("Guest Reader");
    System.out.println("Guest reader entered the reading lounge: " + guest.getName());
    // guest falls out of scope here!
  }
}
