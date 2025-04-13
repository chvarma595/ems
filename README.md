# Employee Management System

This is a simple Employee Management System built using **Spring Boot**, **Java**, **MySQL**, and **Thymeleaf**. It follows the **MVC (Model-View-Controller)** architecture and uses plain JDBC for database interaction. The project is designed for students learning full-stack development with a focus on backend logic, minimal CSS, and no JavaScript.

### Functionalities Supported

1. **Add Employee**:
   - Add a new employee with ID, Name, Email, Salary, and Department.
   - Displays a success message ("Employee added successfully!") on the same page after submission, and resets the form for adding another employee.
   - Shows an error message if the email is already in use or any other error.

2. **View All Employees**:
   - Displays a table of all employees on the home page with their ID, Name, Email, Salary, and Department.

3. **Edit Employee**:
   - Update an employee’s Email, Salary, and Department.
   - The ID and name fields are readonly and styled gray to prevent changes.
   - Redirects to the home page after a successful update.

4. **Delete Employee**:
   - Shows a confirmation page with employee details before deletion.
   - Deletes the employee upon confirmation and redirects to the home page with a success message ("Employee successfully deleted!").

5. **Search Employees**:
   - Search employees by partial name (case-insensitive).
   - Displays matching employees in the home page table.

## Installation Guide

Follow these steps to set up and run the project on your local machine using VS Code as the IDE.

### Prerequisites

1. **Java:** Install JDK 17 (or compatible version). Verify with `java -version` in your terminal
2. **Maven:** Included with Spring Boot, but ensure you have it installed (`mvn -version`) or let VS Code handle it.
3. **MySQL:** Install MySQL (e.g., MySQL Community Server) and ensure it’s running. Verify with `mysql -V`.
4. **VS Code:** Install Visual Studio Code with the Java Extension Pack and Spring Boot Extension Pack for easier setup.

### Steps

#### Clone or Download the Project:

If using Git: git clone <repository-url> (replace with your repo URL if hosted).
Otherwise, download the project ZIP and extract it.

#### Set Up the Database:
Open MySQL (e.g., via terminal with `mysql -u root -p` or a GUI like MySQL Workbench).
Run the following SQL commands to create the database and tables:
```sql

    CREATE DATABASE employee_db;
    USE employee_db;

    CREATE TABLE employee (
        id BIGINT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        salary DOUBLE NOT NULL,
        department VARCHAR(255) NOT NULL
    );
```

### Configure Database Connection:
Open `src/main/resources/application.properties`. Update the MySQL credentials to match your setup:

```properties

    spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
    spring.datasource.username=root
    spring.datasource.password=your_password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
Replace your_password with your MySQL root password.

### Open the Project in VS Code:

1. Launch VS Code.
2. Click File > Open Folder and select the project folder (e.g., employee).
3. Wait for VS Code to load dependencies (Maven will download them automatically if internet is available).

### Run the Application:

1. Open src/main/java/com/example/employee/EmployeeManagementApplication.java.
2. Right-click the file and select "Run Java" (or click the green "Play" button if the Spring Boot extension is installed). 
3. The app starts on http://localhost:8080.

### Access the App:

Open a browser and go to http://localhost:8080. You’ll see the home page with an empty employee list (add employees to populate it).