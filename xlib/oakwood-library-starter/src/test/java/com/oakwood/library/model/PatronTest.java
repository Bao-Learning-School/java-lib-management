package com.oakwood.library.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Patron Class Tests (Consumer & Borrow Limit Rule)")
class PatronTest {

    @Test
    @DisplayName("Should initialize patron with empty borrowed list and canBorrow as true")
    void testPatronInitialization() {
        Patron patron = new Patron("Alex");

        assertEquals("Alex", patron.getName());
        assertEquals(0, patron.getBorrowCount());
        assertTrue(patron.canBorrow());
        assertTrue(patron.getBorrowedBooks().isEmpty());
    }

    @Test
    @DisplayName("Should accept books up to the maximum limit of 3")
    void testBorrowUpToLimit() {
        Patron patron = new Patron("Alex");
        Book book1 = new Book("Book 1", "Author 1");
        Book book2 = new Book("Book 2", "Author 2");
        Book book3 = new Book("Book 3", "Author 3");

        patron.receiveBook(book1);
        patron.receiveBook(book2);
        patron.receiveBook(book3);

        assertEquals(3, patron.getBorrowCount());
        assertFalse(patron.canBorrow(), "Patron should not be eligible to borrow once limit of 3 is reached");
        assertTrue(patron.hasBook(book1));
        assertTrue(patron.hasBook(book2));
        assertTrue(patron.hasBook(book3));
    }

    @Test
    @DisplayName("Feature 2: Should reject borrowing more than 3 books with IllegalStateException")
    void testBorrowLimitEnforcement() {
        Patron patron = new Patron("Alex");
        patron.receiveBook(new Book("Book 1", "Author 1"));
        patron.receiveBook(new Book("Book 2", "Author 2"));
        patron.receiveBook(new Book("Book 3", "Author 3"));

        Book book4 = new Book("Book 4", "Author 4");
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> patron.receiveBook(book4));
        assertTrue(ex.getMessage().contains("maximum borrowing limit of 3"));
    }

    @Test
    @DisplayName("Should remove book when returned and restore borrow capacity")
    void testReturnBook() {
        Patron patron = new Patron("Alex");
        Book book1 = new Book("Book 1", "Author 1");
        Book book2 = new Book("Book 2", "Author 2");
        Book book3 = new Book("Book 3", "Author 3");

        patron.receiveBook(book1);
        patron.receiveBook(book2);
        patron.receiveBook(book3);
        assertFalse(patron.canBorrow());

        boolean returned = patron.returnBook(book2);
        assertTrue(returned);
        assertEquals(2, patron.getBorrowCount());
        assertFalse(patron.hasBook(book2));
        assertTrue(patron.canBorrow(), "Patron should be able to borrow again after returning a book");
    }

    @Test
    @DisplayName("Should return false when attempting to return a book not held")
    void testReturnUnheldBook() {
        Patron patron = new Patron("Alex");
        Book bookNotHeld = new Book("Unheld", "Author");

        assertFalse(patron.returnBook(bookNotHeld));
    }

    @Test
    @DisplayName("Should prevent duplicate book additions to same patron")
    void testDuplicateBookAddition() {
        Patron patron = new Patron("Alex");
        Book book = new Book("Book", "Author");

        patron.receiveBook(book);
        assertThrows(IllegalArgumentException.class, () -> patron.receiveBook(book));
    }

    @Test
    @DisplayName("Should protect internal borrowedBooks list from direct external modification")
    void testEncapsulationOfBorrowedBooksList() {
        Patron patron = new Patron("Alex");
        Book book = new Book("Book", "Author");

        assertThrows(UnsupportedOperationException.class, () -> patron.getBorrowedBooks().add(book));
    }
}
