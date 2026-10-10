package com.oakwood.library.service;

import com.oakwood.library.fee.GracePeriodFeeCalculator;
import com.oakwood.library.model.Book;
import com.oakwood.library.model.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Library Service Tests (Coordinator & Transactions)")
class LibraryTest {

    private Library library;
    private Book duneBook;
    private Book javaBook;
    private Patron alex;
    private Patron maya;

    @BeforeEach
    void setUp() {
        library = new Library();
        duneBook = new Book("Dune", "Frank Herbert", 14);
        javaBook = new Book("Effective Java", "Joshua Bloch", 14);
        alex = new Patron("Alex");
        maya = new Patron("Maya");

        library.addBook(duneBook);
        library.addBook(javaBook);
    }

    @Test
    @DisplayName("Should successfully add books to the library catalog")
    void testAddBook() {
        assertEquals(2, library.getCatalog().size());
        assertTrue(library.findBook("Dune").isPresent());
        assertTrue(library.findBook("Effective Java").isPresent());
    }

    @Test
    @DisplayName("Should checkout book to patron and mutate availability")
    void testSuccessfulCheckout() {
        boolean success = library.checkoutBook("Dune", alex);

        assertTrue(success);
        assertFalse(duneBook.isAvailable(), "Book state should become unavailable");
        assertTrue(alex.hasBook(duneBook), "Patron should now possess reference to the book");
        assertEquals(1, alex.getBorrowCount());
    }

    @Test
    @DisplayName("Should reject checkout if book is already borrowed by someone else (Collision Detection)")
    void testCheckoutCollisionRejection() {
        library.checkoutBook("Dune", alex);

        // Maya tries to checkout the same book
        boolean success = library.checkoutBook("Dune", maya);
        assertFalse(success, "Second checkout for already borrowed book should fail");
        assertFalse(maya.hasBook(duneBook), "Maya should not possess Dune");
    }

    @Test
    @DisplayName("Should reject checkout if book is not in the catalog")
    void testCheckoutNonExistentBook() {
        boolean success = library.checkoutBook("Unknown Book", alex);
        assertFalse(success);
    }

    @Test
    @DisplayName("Feature 2: Should reject checkout if patron has reached borrow limit of 3 books")
    void testBorrowLimitRejectionDuringCheckout() {
        Book book3 = new Book("Book 3", "Author 3");
        Book book4 = new Book("Book 4", "Author 4");
        library.addBook(book3);
        library.addBook(book4);

        // Alex borrows 3 books
        assertTrue(library.checkoutBook("Dune", alex));
        assertTrue(library.checkoutBook("Effective Java", alex));
        assertTrue(library.checkoutBook("Book 3", alex));
        assertEquals(3, alex.getBorrowCount());

        // Attempt to borrow 4th book
        boolean checkout4 = library.checkoutBook("Book 4", alex);
        assertFalse(checkout4, "Checkout must be rejected when borrow limit is reached");
        assertTrue(book4.isAvailable(), "Book 4 remains available on shelf");
    }

    @Test
    @DisplayName("Feature 1: Should successfully return a book and make it available again")
    void testReturnBookSuccess() {
        library.checkoutBook("Dune", alex);
        assertFalse(duneBook.isAvailable());

        boolean returned = library.returnBook("Dune", alex);
        assertTrue(returned);
        assertTrue(duneBook.isAvailable(), "Returned book should become available again");
        assertFalse(alex.hasBook(duneBook), "Alex should no longer possess the book");
    }

    @Test
    @DisplayName("Should reject return if patron does not have the book")
    void testReturnBookNotHeldByPatron() {
        library.checkoutBook("Dune", alex);

        // Maya attempts to return Dune which Alex borrowed
        boolean returned = library.returnBook("Dune", maya);
        assertFalse(returned);
        assertFalse(duneBook.isAvailable(), "Book remains borrowed by Alex");
    }

    @Test
    @DisplayName("Should reject return for book not found in library records")
    void testReturnBookNotFound() {
        boolean returned = library.returnBook("Nonexistent Title", alex);
        assertFalse(returned);
    }

    @Test
    @DisplayName("Feature 3: Should calculate late fees accurately on overdue return")
    void testReturnBookWithLateFees() {
        library.checkoutBook("Dune", alex);

        // Dune has 14 due days. Kept 18 days -> 4 days overdue * $0.50 = $2.00
        double fee = library.returnBook("Dune", alex, 18);
        assertEquals(2.00, fee, 0.001);
        assertTrue(duneBook.isAvailable());
    }

    @Test
    @DisplayName("Should support switching fee strategy at runtime (Strategy Pattern)")
    void testSwapFeeStrategy() {
        library.checkoutBook("Dune", alex);

        // Switch to Grace Period strategy (2 grace days, $0.75/day)
        library.setFeeCalculator(new GracePeriodFeeCalculator(2, 0.75));

        // Kept 15 days (due 14 + 2 grace = up to 16 free) -> fee is $0.00
        double fee = library.returnBook("Dune", alex, 15);
        assertEquals(0.00, fee, 0.001);
    }
}
