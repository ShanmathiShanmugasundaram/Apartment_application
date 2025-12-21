Apartment Visitor Entry Management System (Java Console App)
#Project Overview

The Apartment Visitor Entry Management System is a Java-based console application designed to manage visitor entry, approval, parking allocation, and security monitoring in an apartment community.

This system supports multiple user roles:

Admin

Resident

Security

Each role has specific responsibilities to ensure secure and organized visitor management.

#Features
#User Authentication

User Registration & Login

Role-based access control:

Admin

Resident

Security

 #Resident Features

Pre-register visitors

Approve or deny visitor requests

View visitor logs related to their apartment

#Security Features

Visitor check-in (only approved visitors)

Visitor check-out

Automatic parking slot assignment & release

View all visitor activity logs

 #Admin Features

View all visitors and logs

Monitor parking slot status

Generate security audit reports

#Parking Management

Limited parking slots (P1 to P5)

Automatic allocation during check-in

Slot release during check-out

Real-time parking availability tracking

#Technologies Used

Java

Java Collections Framework

ArrayList

HashSet

HashMap

Scanner for user input

Console-based UI

#Project Structure
ApartmentVisitorApp.java


Main components:

User class – handles user data and roles

Visitor class – manages visitor details

Role-specific menus:

Resident Menu

Security Menu

Admin Menu

#How to Run the Application
#Prerequisites

Java JDK (Version 8 or above)

Any Java-supported IDE (IntelliJ, Eclipse, VS Code)
OR Command Prompt / Terminal

#Steps to Run

Save the file as:

ApartmentVisitorApp.java


Compile the program:

javac ApartmentVisitorApp.java


Run the application:

java ApartmentVisitorApp
