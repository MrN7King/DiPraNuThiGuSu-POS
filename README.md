a) Project Title

POS & MANAGEMENT SYSTEM
A Comprehensive Sales, Inventory, Expense, and Uber Eats Integration System

b) Task Option Chosen

3: Software Logic (Desktop or Backend System) - Create a basic desktop or backend software that performs:
    •    Employee attendance or inventory management
    •    Store data locally or in a database
    •    Generate a report (PDF/Excel or chart form)

c) Technologies Used

🔹 Java – Core application logic

🔹 JavaFX (Scenebuilder) – GUI interface for POS, dashboard, and file uploads

🔹 Spring Boot – Backend business logic and service handling

🔹 MySQL – Relational database to store orders, users, inventory, expenses

🔹 Uber Eats API (CSV/Excel Import) – Third-party order data integration

🔹 Apache POI / OpenCSV – Parsing Excel and CSV files

🔹 ApachePDFBox – Generating downloadable PDF reports

🔹 WAMP Server / phpMyAdmin – Local MySQL environment for development and testing


d) Features Implemented

🔹 Point-of-Sale (POS) Module: Real-time order processing, digital receipts, refunds, and discounts

🔹 Inventory Management: Live stock tracking, low-stock alerts, and supplier linkage

🔹 Uber Eats Integration: Upload multiple CSV/Excel files, merged database view, unified revenue reporting

🔹 Expense Tracking: Categorized expense logging, profit/loss summary dashboard

🔹 Data Analytics: Visual charts and summary reports for decision-making

🔹 PDF Export: Export customized data views to PDF with selectable columns

🔹 User Roles and Security: Admin and cashier login, secure access with role-based permissions


e) Instructions to Run the Project
Step 1: Setup Environment
1. Install WAMP Server - Download from https://www.wampserver.com

2. Launch WAMP and ensure services are running (green icon)

3. Visit http://localhost/phpmyadmin and create a database named: sipandsnack

4. Go to Import tab, upload your .sql file that is named in the project file known as database_export.sql and click Go

Step 2: Run the Application
1. Open with an IDE (IntelliJ is recommended)

2. Click Run and let the dependencies be install and then feel free to explore the application.
