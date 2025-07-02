# Library Management System

A Java-based Library Management System demonstrating the implementation of Observer and Strategy design patterns. This system provides core library operations including book management, patron management, and lending services with event-driven notifications.

## System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                            LIBRARY MANAGEMENT SYSTEM                            │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────┐
│  LibraryApplication │ ◄─── Main Entry Point
└─────────────────────┘
           │
           ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                               CORE SERVICES                                     │
├─────────────────────┬─────────────────────┬─────────────────────────────────────┤
│    BookService      │   PatronService     │         LendingService              │
│                     │                     │                                     │
│ + addBook()         │ + registerPatron()  │ + checkoutBook()                    │
│ + search()          │ + findPatron()      │ + returnBook()                      │
│ + removeBook()      │ + getAllPatrons()   │                                     │
│ + getAllBooks()     │                     │                                     │
└─────────────────────┴─────────────────────┴─────────────────────────────────────┘
           │                                               │
           │                                               │
           ▼                                               ▼
┌─────────────────────────────────────────────┐ ┌─────────────────────────────────┐
│              MODEL CLASSES                  │ │         DOMAIN MODELS           │
├─────────────────────┬───────────────────────┤ ├─────────────────┬───────────────┤
│       Book          │     BookStatus        │ │     Patron      │               │
│                     │     (Enum)            │ │                 │               │
│ - title: String     │                       │ │ - patronId      │               │
│ - author: String    │ • AVAILABLE           │ │ - name          │               │
│ - isbn: String      │ • CHECKED_OUT         │ │ - email         │               │
│ - year: int         │ • RESERVED            │ │ - phone         │               │
│ - status            │ • MAINTENANCE         │ │                 │               │
└─────────────────────┴───────────────────────┘ └─────────────────┴───────────────┘
```

## Design Patterns Implementation

### 🔍 Observer Pattern
```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              OBSERVER PATTERN                                   │
└─────────────────────────────────────────────────────────────────────────────────┘

                    ┌─────────────────┐
                    │   BookService   │ ◄─── Subject
                    │   (Subject)     │
                    │                 │
                    │ + addObserver() │
                    │ + notify()      │
                    └─────────────────┘
                             │
                             │ notifies
                             ▼
        ┌────────────────────────────────────────────────────────┐
        │                  OBSERVERS                             │
        ├──────────────────────────┬─────────────────────────────┤
        │   LibraryEventLogger     │    InventoryObserver        │
        │                          │                             │
        │ + update()               │ + update()                  │
        │   └─ Logs events         │   └─ Tracks inventory       │
        └──────────────────────────┴─────────────────────────────┘
```

### ⚡ Strategy Pattern
```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                             STRATEGY PATTERN                                    │
└─────────────────────────────────────────────────────────────────────────────────┘

                    ┌─────────────────┐
                    │   BookService   │ ◄─── Context
                    │                 │
                    │ + search()      │
                    └─────────────────┘
                             │
                             │ uses
                             ▼
                ┌─────────────────────────┐
                │    SearchStrategy       │ ◄─── Strategy (Enum)
                │      (Enum)             │
                │                         │
                │ • BY_TITLE              │
                │ • BY_AUTHOR             │
                │ • BY_ISBN               │
                │ • BY_YEAR               │
                └─────────────────────────┘
```

## Features

### Core Functionality
- **Book Management**: Add, search, and manage books in the library inventory
- **Patron Management**: Register and manage library patrons
- **Lending System**: Handle book checkouts and returns
- **Event Logging**: Comprehensive logging of all library activities

### Design Patterns Implemented
- **Observer Pattern**: Real-time notifications for library events (book additions, lending activities)
- **Strategy Pattern**: Flexible search functionality with multiple search strategies

### Search Capabilities
- Search by Title
- Search by Author
- Search by ISBN
- Search by Publication Year

## Usage Examples

The application demonstrates the following functionality:

### Observer Pattern Demo
- Adding books triggers inventory tracking and event logging
- All library activities are automatically logged

### Strategy Pattern Demo
- Search books by different criteria:
  - Title: "Java"
  - Author: "Martin"
  - ISBN: "978-0134685991"
  - Publication Year: "2020"

### Lending Operations
- Patron registration
- Book checkout and return
- Real-time notifications for all lending activities

## Design Pattern Details

### Observer Pattern
- **Observers**: `LibraryEventLogger`, `InventoryObserver`
- **Subject**: `BookService`
- **Events**: Book additions, lending activities
- **Benefits**: Loose coupling, extensible event handling

### Strategy Pattern
- **Context**: `BookService.search()`
- **Strategies**: `BY_TITLE`, `BY_AUTHOR`, `BY_ISBN`, `BY_YEAR`
- **Benefits**: Flexible search algorithms, easy to extend with new search criteria

## Logging

The application uses SLF4J with Logback for comprehensive logging:
- Console output for real-time monitoring
- File logging to `logs/library-events.log`
- Configurable log levels and patterns

## Sample Output

When you run the application, you'll see:
1. Observer pattern demonstrations with real-time notifications
2. Strategy pattern examples showing different search methods
3. Lending operations with complete event tracking


