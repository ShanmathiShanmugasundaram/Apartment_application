# Apartment Visitor Entry Management System

A **Java console-based application** designed to manage visitor entry, approval, parking allocation, and security monitoring in an apartment community.  
The system follows **role-based access control** for Admin, Resident, and Security users.

---

## Features

### User Authentication
- User Registration and Login
- Role-based access:
  - Admin
  - Resident
  - Security

---

### Resident Module
- Pre-register visitors
- Approve or deny visitor requests
- View visitor logs related to their apartment

---

### Security Module
- Visitor check-in (only approved visitors)
- Visitor check-out
- Automatic parking slot allocation
- Parking slot release on checkout
- View all visitor activity logs

---

### Admin Module
- View all visitors
- Monitor parking slot availability
- Generate security audit reports

---

### Parking Management
- Limited parking slots (`P1` to `P5`)
- Automatic slot assignment
- Real-time parking status
- Slot release after visitor checkout

---

## Technologies Used
- Java
- Java Collections Framework
  - ArrayList
  - HashSet
  - HashMap
- Scanner (for console input)

---


## How to Run the Project

### Prerequisites
- Java JDK 8 or higher
- Command Prompt / Terminal or any Java IDE

### Steps to Execute
```bash
javac ApartmentVisitorApp.java
java ApartmentVisitorApp

