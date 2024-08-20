Exericise 1
Problem 3:
![Alt text](Diagram\Login.jpg)

# Use Case Scenarios

## 1. Successful Login and Borrowing a Book:
   - The user attempts to log in
   - Login is successful
   - The user searches for a book
   - The book is found
   - The system displays the list of books
   - The user selects a book to add to the borrowing list
   - Payment is successful
   - The system creates the borrowing details
   - The process ends

## 2. Failed Login Scenario:
   - The user attempts to log in
   - Login fails
   - The system returns to the login step
   - The user can try to log in again

## 3. Successful Login but No Book Found:
   - The user successfully logs in
   - The user searches for a book
   - The book is not found
   - The system returns to the book search step
   - The user can search for another book

## 4. Successful Login, Book Found, but Payment Fails:
   - The user successfully logs in
   - The user searches for a book
   - The book is found
   - The system displays the list of books
   - The user selects a book to add to the borrowing list
   - Payment fails (for example, due to reaching borrowing limits or the book is unavailable)
   - The system returns to the book list display step
   - The user can choose another book or end the process

## 5. Multiple Book Searches Scenario:
   - The user successfully logs in
   - The user searches for a book
   - The book is not found
   - The user searches for another book
   - The book is found
   - The process continues as in the successful scenario
