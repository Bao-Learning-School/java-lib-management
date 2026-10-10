# 📚 Oakwood Public Library Management System
### *An Interactive Java Reference Project for Young Coders & High School Students*

Welcome to the **Oakwood Public Library Management System**! This project was designed from the ground up as a reference implementation for students learning Object-Oriented Programming (OOP) in Java.

Instead of dry, abstract theory, this project models real-world objects—**Books**, **Patrons**, and the **Library Coordinator**—interacting just like people and books do in your neighborhood library.

---

## 🌟 Key Concepts You Will Learn

```
               +---------------------------+
               |          Library          | (Coordinator)
               +---------------------------+
               | - catalog: List<Book>     |
               | - feePolicy: FeeCalculator|
               +-------------+-------------+
                             |
             +---------------+---------------+
             |                               |
             v                               v
+-------------------------+     +-------------------------+
|          Book           |     |         Patron          | (Consumer)
+-------------------------+     +-------------------------+
| - title: String         |     | - name: String          |
| - author: String        |     | - borrowed: List<Book>  |
| - isAvailable: boolean  |     | - MAX_LIMIT = 3         |
| - dueDays: int          |     +-------------------------+
+-------------------------+
```

| OOP Concept | Where It Happens | What It Means in Plain English |
|---|---|---|
| **Instantiation** | `new Book("Dune", "Frank Herbert")` | Allocating memory on the **Java Heap** to bring a new object into existence. |
| **Encapsulation** | `private` variables with getters/setters | Protecting internal data so outside code can't tamper with it directly. |
| **Passing References** | `patron.receiveBook(duneBook)` | Java doesn't clone the book; it hands the patron a pointer (address) to the exact same book on the heap. |
| **State Mutation** | `book.setAvailable(false)` | Modifying an object's internal properties based on an event. |
| **Aggregation** | `Patron` holds `List<Book>` | A patron possesses books, but if the patron leaves the library, the books still exist! |
| **Association** | `library.checkoutBook("Dune", alex)` | Two independent objects collaborating by passing messages to each other. |
| **Garbage Collection (GC)** | `closeLibrary()` local variable | When an object has zero references pointing to it, Java automatically cleans up its memory. |

---

## 🎨 Design Patterns Explained for Beginners

Design patterns are time-tested recipes used by professional software engineers to write clean, flexible code.

### 1. 🎯 The Strategy Pattern (Behavioral)
> *“Allow changing the algorithm at runtime without touching the main system.”*

* **The Problem:** Different libraries (or different days of the year) have different rules for late fines. We don't want giant, messy `if/else` ladders inside `Library`.
* **The Solution:** We defined a `FeeCalculator` interface with two interchangeable strategies:
  * [`StandardFeeCalculator`](src/main/java/com/oakwood/library/fee/StandardFeeCalculator.java): Charges a simple daily rate (e.g., $0.50/day overdue).
  * [`GracePeriodFeeCalculator`](src/main/java/com/oakwood/library/fee/GracePeriodFeeCalculator.java): Gives readers 2 free grace days before fines start.
* **How to switch it:**
  ```java
  oakwood.setFeeCalculator(new GracePeriodFeeCalculator(2, 0.75));
  ```

### 2. 👁️ The Observer Pattern (Behavioral)
> *“Keep business logic pure by letting listeners handle the output.”*

* **The Problem:** If `Library` uses `System.out.println` everywhere, you can never reuse it in a mobile app, website, or test without polluting the console.
* **The Solution:** We defined a [`LibraryEventListener`](src/main/java/com/oakwood/library/event/LibraryEventListener.java) interface. The `Library` broadcasts events (`onBookCheckedOut`, `onBookReturned`, `onCheckoutFailed`), and our [`ConsoleStoryNarrator`](src/main/java/com/oakwood/library/event/ConsoleStoryNarrator.java) listens and prints the story.

### 3. 🏭 The Factory Pattern (Creational)
> *“Make creating pre-configured objects as simple as reading natural English.”*

* **The Problem:** Remembering loan periods for different book types (`new Book("Dune", "Herbert", 7)`) is prone to typos.
* **The Solution:** [`BookFactory`](src/main/java/com/oakwood/library/factory/BookFactory.java) provides readable helper methods:
  * `BookFactory.createStandardBook(title, author)` (14-day loan)
  * `BookFactory.createBestseller(title, author)` (7-day loan)
  * `BookFactory.createReferenceBook(title, author)` (3-day loan)
  * `BookFactory.createChildrenBook(title, author)` (21-day loan)

---

## 📖 Features Implemented (From the Project Spec)

1. **Core Book & Patron Management:**
   - Books maintain title, author, availability, and loan duration (`dueDays`).
   - Safe state mutation (`setAvailable(false)` on checkout, `setAvailable(true)` on return).
2. **Borrow Limit Enforcement (Feature 2):**
   - Patrons can hold a maximum of **3 books** at any time.
   - Any attempt to borrow a 4th book is politely rejected.
3. **Return Logic (Feature 1):**
   - Returns verify that the book exists in the catalog and is currently held by the returning patron.
   - Successfully resets book availability to `true` and removes it from the patron's collection.
4. **Late Fees Calculation (Feature 3):**
   - Automatically checks `daysKept` against `book.getDueDays()`.
   - Computes fines via the active `FeeCalculator` strategy.
5. **Interactive Story Script:**
   - Turn-by-turn simulation of "A Day in the Life of Oakwood Public Library" demonstrating all interactions.

---

## 🗂️ Project Structure

```
xlib/
├── pom.xml                                   # Maven build file with JUnit 5 & plugins
├── README.md                                 # This guide!
└── src/
    ├── main/java/com/oakwood/library/
    │   ├── Main.java                         # Interactive story simulation script
    │   ├── event/
    │   │   ├── LibraryEventListener.java     # Observer interface
    │   │   └── ConsoleStoryNarrator.java     # Observer console reporter
    │   ├── factory/
    │   │   └── BookFactory.java              # Factory for books
    │   ├── fee/
    │   │   ├── FeeCalculator.java            # Strategy interface
    │   │   ├── StandardFeeCalculator.java    # Standard late fee strategy
    │   │   └── GracePeriodFeeCalculator.java # Grace period late fee strategy
    │   ├── model/
    │   │   ├── Book.java                     # Book model (Data carrier)
    │   │   └── Patron.java                   # Patron model (Consumer with borrow limit)
    │   └── service/
    │       └── Library.java                  # Library coordinator service
    └── test/java/com/oakwood/library/
        ├── OakwoodLibraryStoryIntegrationTest.java # Full story lifecycle test
        ├── event/
        │   └── ConsoleStoryNarratorTest.java
        ├── factory/
        │   └── BookFactoryTest.java
        ├── fee/
        │   ├── GracePeriodFeeCalculatorTest.java
        │   └── StandardFeeCalculatorTest.java
        ├── model/
        │   ├── BookTest.java
        │   └── PatronTest.java
        └── service/
            └── LibraryTest.java
```

---

## 🚀 How to Run the Project

### 1. Run the Interactive Story Simulation
Run the turn-by-turn story script in your terminal:
```bash
mvn compile exec:java
```

### 2. Run All Unit & Integration Tests
Execute the 36 automated unit and scenario tests:
```bash
mvn test
```

### 3. Generate HTML Javadoc Documentation
Generate clean, browsable documentation with zero warnings:
```bash
mvn javadoc:javadoc
```
The generated HTML website will be located in `target/reports/apidocs/index.html`.

---

## 🧪 Test Suite Summary

The project includes **36 tests** covering 100% of the core requirements and edge cases:
- ✅ **BookTest:** Verifies encapsulation, state mutation, due days, and input validation.
- ✅ **PatronTest:** Verifies collection aggregation, borrow limit (max 3 books), duplicate rejection, and return behavior.
- ✅ **StandardFeeCalculatorTest & GracePeriodFeeCalculatorTest:** Verifies overdue arithmetic and strategy compliance.
- ✅ **BookFactoryTest:** Verifies correct loan day presets.
- ✅ **LibraryTest:** Verifies catalog management, collision handling, borrow limit checks, return checks, and fee application.
- ✅ **OakwoodLibraryStoryIntegrationTest:** Validates the complete multi-patron morning-to-evening lifecycle script.
