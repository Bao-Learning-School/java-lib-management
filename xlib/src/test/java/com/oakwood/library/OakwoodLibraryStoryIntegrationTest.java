package com.oakwood.library;

import com.oakwood.library.event.LibraryEventListener;
import com.oakwood.library.fee.GracePeriodFeeCalculator;
import com.oakwood.library.model.Book;
import com.oakwood.library.model.Patron;
import com.oakwood.library.service.Library;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A Day in the Life of Oakwood Public Library - Story Integration Test")
class OakwoodLibraryStoryIntegrationTest {

    @Test
    @DisplayName("Verify full lifecycle story sequence from PDF specifications")
    void testOakwoodLibraryStorySequence() {
        // Track events via a test observer
        List<String> eventLog = new ArrayList<>();
        Library oakwood = new Library();
        oakwood.addListener(new LibraryEventListener() {
            @Override
            public void onBookAdded(Book book) {
                eventLog.add("ADDED: " + book.getTitle());
            }

            @Override
            public void onBookCheckedOut(Patron patron, Book book) {
                eventLog.add("CHECKOUT_OK: " + patron.getName() + " -> " + book.getTitle());
            }

            @Override
            public void onCheckoutFailed(Patron patron, String title, String reason) {
                eventLog.add("CHECKOUT_FAIL: " + patron.getName() + " -> " + title + " (" + reason + ")");
            }

            @Override
            public void onBookReturned(Patron patron, Book book, double lateFee) {
                eventLog.add("RETURN_OK: " + patron.getName() + " -> " + book.getTitle() + " fee=" + lateFee);
            }

            @Override
            public void onReturnFailed(Patron patron, String title, String reason) {
                eventLog.add("RETURN_FAIL: " + patron.getName() + " -> " + title + " (" + reason + ")");
            }
        });

        // 1. Morning Setup
        Book javaBook = new Book("Effective Java", "Joshua Bloch", 14);
        Book duneBook = new Book("Dune", "Frank Herbert", 14);
        Book hobbitBook = new Book("The Hobbit", "J.R.R. Tolkien", 14);
        Book book1984 = new Book("1984", "George Orwell", 14);

        oakwood.addBook(javaBook);
        oakwood.addBook(duneBook);
        oakwood.addBook(hobbitBook);
        oakwood.addBook(book1984);

        assertEquals(4, oakwood.getCatalog().size());

        // 2. 10:00 AM: Patron Alex registers
        Patron alex = new Patron("Alex");
        assertEquals(0, alex.getBorrowCount());

        // 3. 10:15 AM: First Checkout (Alex checks out Dune)
        boolean checkout1 = oakwood.checkoutBook("Dune", alex);
        assertTrue(checkout1);
        assertFalse(duneBook.isAvailable());
        assertTrue(alex.hasBook(duneBook));

        // 4. 11:00 AM: Collision Detection (Maya tries to borrow Dune)
        Patron maya = new Patron("Maya");
        boolean mayaCheckoutFail = oakwood.checkoutBook("Dune", maya);
        assertFalse(mayaCheckoutFail);
        assertFalse(maya.hasBook(duneBook));

        // 5. 11:30 AM: Alex tests Borrow Limit (Attempts 4 books)
        assertTrue(oakwood.checkoutBook("Effective Java", alex));
        assertTrue(oakwood.checkoutBook("The Hobbit", alex));
        assertEquals(3, alex.getBorrowCount());
        assertFalse(alex.canBorrow());

        // 4th checkout should be rejected
        boolean fourthCheckout = oakwood.checkoutBook("1984", alex);
        assertFalse(fourthCheckout);
        assertTrue(book1984.isAvailable());

        // 6. 2:00 PM: Alex returns Dune (overdue by 4 days)
        // 18 days kept - 14 due days = 4 days overdue * 0.50 = $2.00
        double alexFee = oakwood.returnBook("Dune", alex, 18);
        assertEquals(2.00, alexFee, 0.001);
        assertTrue(duneBook.isAvailable());
        assertFalse(alex.hasBook(duneBook));
        assertEquals(2, alex.getBorrowCount());
        assertTrue(alex.canBorrow()); // Can borrow again!

        // 7. 2:05 PM: Maya claims Dune now that it is available
        boolean mayaCheckoutSuccess = oakwood.checkoutBook("Dune", maya);
        assertTrue(mayaCheckoutSuccess);
        assertTrue(maya.hasBook(duneBook));
        assertFalse(duneBook.isAvailable());

        // 8. 3:00 PM: Switch to Grace Period Strategy and Maya returns Dune
        oakwood.setFeeCalculator(new GracePeriodFeeCalculator(2, 0.75));
        // Kept 15 days (14 due + 2 grace = up to 16 days free) -> fee = $0.00
        double mayaFee = oakwood.returnBook("Dune", maya, 15);
        assertEquals(0.00, mayaFee, 0.001);
        assertTrue(duneBook.isAvailable());

        // Verify event log recorded all phases
        assertTrue(eventLog.contains("ADDED: Dune"));
        assertTrue(eventLog.contains("CHECKOUT_OK: Alex -> Dune"));
        assertTrue(eventLog.stream().anyMatch(e -> e.startsWith("CHECKOUT_FAIL: Maya -> Dune")));
        assertTrue(eventLog.stream().anyMatch(e -> e.startsWith("CHECKOUT_FAIL: Alex -> 1984")));
        assertTrue(eventLog.contains("RETURN_OK: Alex -> Dune fee=2.0"));
        assertTrue(eventLog.contains("CHECKOUT_OK: Maya -> Dune"));
        assertTrue(eventLog.contains("RETURN_OK: Maya -> Dune fee=0.0"));
    }
}
