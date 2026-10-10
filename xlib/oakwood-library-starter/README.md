# 🎓 Student Assignment: Oakwood Public Library Framework
### *Hands-on Object-Oriented Programming & Design Patterns in Java*

Welcome, Junior Software Engineer! The Oakwood Public Library needs your help to build their new automated catalog and lending system.

We have provided the architectural skeleton, design pattern interfaces, and tests. Your mission is to fill in the missing logic marked with `// TODO:` comments across the project.

---

## 🗺️ Your Step-by-Step Roadmap

Work through the tasks in order. After each step, run the matching unit test to verify your work!

```
[Step 1: Book.java]  ──>  [Step 2: Patron.java]  ──>  [Step 3: Fee Strategies]
        │
        v
[Step 4: BookFactory.java]  ──>  [Step 5: Library.java]  ──>  [Step 6: Run Story in Main!]
```

---

### 📝 Step 1: Complete `Book.java` (The Data Carrier)
* **File:** `src/main/java/com/oakwood/library/model/Book.java`
* **Concepts:** Encapsulation, State Mutation, Heap Allocation.
* **Tasks:**
  * **Task 1.1:** Return the current `isAvailable` boolean in `isAvailable()`.
  * **Task 1.2:** Mutate `this.isAvailable` in `setAvailable(boolean)`.
  * **Task 1.3:** Validate and set `dueDays` in `setDueDays(int)`.
  * **Task 1.4:** Format the book string as `'Title' by Author` in `toString()`.
* **Verify your work:**
  ```bash
  mvn test -Dtest=BookTest
  ```

---

### 📝 Step 2: Complete `Patron.java` (The Consumer)
* **File:** `src/main/java/com/oakwood/library/model/Patron.java`
* **Concepts:** Aggregation, Passing References, Business Rules (Borrow Limit).
* **Tasks:**
  * **Task 2.1:** Implement `canBorrow()` to check if currently borrowed books < `MAX_BORROW_LIMIT` (3).
  * **Task 2.2:** Implement `receiveBook(Book book)`: check `null`, check `canBorrow()`, reject duplicates, and append to the list.
  * **Task 2.3:** Implement `returnBook(Book book)` to remove the book reference.
  * **Task 2.4:** Implement `hasBook(Book book)` using `contains()`.
  * **Task 2.5:** Return `Collections.unmodifiableList(borrowedBooks)` in `getBorrowedBooks()`.
  * **Task 2.6:** Return the count of borrowed books in `getBorrowCount()`.
* **Verify your work:**
  ```bash
  mvn test -Dtest=PatronTest
  ```

---

### 📝 Step 3: Complete Fee Strategies (Strategy Pattern)
* **Files:**
  * `src/main/java/com/oakwood/library/fee/StandardFeeCalculator.java`
  * `src/main/java/com/oakwood/library/fee/GracePeriodFeeCalculator.java`
* **Concepts:** Strategy Pattern (swappable algorithms), Polymorphism.
* **Tasks:**
  * **Task 3.1:** In `StandardFeeCalculator`, charge `$0.00` if on-time, or `(daysKept - dueDays) * dailyRate` if overdue.
  * **Task 3.2:** In `GracePeriodFeeCalculator`, give `gracePeriodDays` free before charging for extra days.
* **Verify your work:**
  ```bash
  mvn test -Dtest=*FeeCalculatorTest
  ```

---

### 📝 Step 4: Complete `BookFactory.java` (Factory Pattern)
* **File:** `src/main/java/com/oakwood/library/factory/BookFactory.java`
* **Concepts:** Creational Factory Pattern, Static Helper Methods.
* **Tasks:**
  * **Tasks 4.1 - 4.4:** Return new `Book` objects pre-configured with standard loan periods (14 days, 7 days, 3 days, 21 days).
* **Verify your work:**
  ```bash
  mvn test -Dtest=BookFactoryTest
  ```

---

### 📝 Step 5: Complete `Library.java` (The Coordinator)
* **File:** `src/main/java/com/oakwood/library/service/Library.java`
* **Concepts:** Association, Collision Detection, Transaction Coordination.
* **Tasks:**
  * **Task 6.1:** Implement `addBook(Book book)` and notify observers.
  * **Task 6.2:** Implement `checkoutBook(String title, Patron patron)`:
    1. Reject if patron reached borrow limit.
    2. Search catalog: if available, set `book.setAvailable(false)`, give reference to patron, and notify success.
    3. If unavailable or not found, notify failure.
  * **Task 6.3:** Implement `returnBook(String title, Patron patron)`.
  * **Task 6.4:** Implement `processReturn(String title, Patron patron, int daysKept)`:
    1. Find book in catalog.
    2. Remove from patron, set `book.setAvailable(true)`, compute late fee using `feeCalculator.calculateFee(...)`, and notify observers.
* **Verify your work:**
  ```bash
  mvn test -Dtest=LibraryTest
  ```

---

### 🚀 Step 6: Run Full Test Suite & Watch the Story Run!
Once all tests pass:
```bash
# Run all 36 tests:
mvn test

# Run the interactive Oakwood Public Library story:
mvn compile exec:java
```

---

## 💡 Tips & Common Gotchas
* **Remember the Heap:** Remember that `patron.receiveBook(book)` does NOT copy the book—it passes an address to the exact same book in memory!
* **Case-Insensitive Titles:** Always compare book titles using `.equalsIgnoreCase()` so users can type "dune" or "Dune".
* **Check Your Tests:** If a test fails, read JUnit's error message—it tells you exactly what was expected vs. what your code produced.
