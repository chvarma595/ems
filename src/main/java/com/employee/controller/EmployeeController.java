package com.employee.controller;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.employee.model.Employee;

@Controller
public class EmployeeController {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    /* 
     * TASK 1: Implement the getConnection() method to establish a connection to the database.
     * The method should return an object of Connection class.
     * Handle exceptions carefully.
     */
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("succes");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        /* 
         * Write code below to establish connection to the database and return the connection object.
         * Handle exceptions carefully.
         */

        return conn;
    }

    /*
     * TASK 2: Implement the home() method to return 
     * the home page "index.html" with a list of all employees.
     * As part of this task, you need to implement getAllEmployees() method 
     * and call it in home() to get list of all employees.
     *
     * Note:
     * The index.html page already exists under resources/templates/ directory.
     * You need to pass the list of employees to the view using the model object.
     */
    @GetMapping("/")
    public String home(Model model) {
        List<Employee> employees=getAllEmployees();
        model.addAttribute("employees", employees);

        /* 
         * Write code below to return the home page "index.html" with a list of all employees.
         * You need to call getAllEmployees() method to get list of all employees.
         * Pass the list of employees to the view using the model object.
         */
        return "index";
    }


    /*
     * This method returns a list of all employees from the database.
     */
    private List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Connection conn=null;
        try {
            String sql = "SELECT * FROM employee";
            conn = getConnection();
           PreparedStatement psd=conn.prepareStatement(sql);
           ResultSet rs= psd.executeQuery();
           while (rs.next()){
            Employee employee=new Employee();
            System.out.println("sucess");
            employee.setId(rs.getLong("id"));
            employee.setName(rs.getString("name"));
            employee.setEmail(rs.getString("email"));
            employee.setSalary(rs.getDouble("salary"));
            employee.setDepartment(rs.getString("department"));
            employees.add(employee);
           }
        }catch (Exception e) {
            e.printStackTrace();
        }
        /* 
         * Write code below to return a list of all employees from the database
         * You need to fetch the data from the employees table in the database
         * and create a list of Employee objects.
         * Handle exceptions carefully.
         */

        return employees;
    }      

    /*
     * TASK 3: Implement the feature to add a new employee.
     * As part of this task, you need to implement 2 methods:
     * 1. Implement showAddEmployeeForm() to display the add-employee form
     * 2. Implement addEmployee() to store the details of the employee in the database
     * 
     * Note: Write proper annotations for each of these methods
     */
    public String showAddEmployeeForm(Model model) {
        List<Employee> employees=getAllEmployees();
        model.addAttribute("employees", employees);

        /*
         * Write code here for adding an empty object of Employee class to model
         * and return add-employee form
         */
        return "index";
    }

    public String addEmployee(@ModelAttribute Employee employee, Model model) {
        /*
         * Write code here to add the details of the employee to the database
         * Handle exceptions carefully.
         */
        return "";
    }

    /*
     * TASK 4: Implement the feature to search employees by name. 
     * As part of this task, you need to implement searchEmployees() method.
     * The method should fetch the list of employees whose name contains 
     * the search keyword from the database and populate in index.html
     * 
     * Note: Write proper annotation for the method. 
     */
    public String searchEmployees(@RequestParam String name, Model model) {
        return "index";
    }


    /*
     * TASK 5: Implement the feature to edit an employee.
     * As part of this task, you need to implement 2 methods:
     * 1. Implement showEditForm() to display the edit-employee form
     * 2. Implement editEmployee() to update the details of the employee in the database
     * 
     */
    public String showEditForm(@PathVariable Long id, Model model) {
        /*
         * Write code here to fetch the employee with the given ID from the database
         * and add it to the model object. Return edit-employee form.
         * 
         * Hint: You might need to implement a new method getEmployeeById() 
         * to fetch the employee by ID.
         * 
         * If employee with the given ID is not found in the DB, 
         * then return to homepage with an error message "Employee with {id} not found.".
         * 
         * Note: edit-employee.html page already exists under resources/templates/ directory.
         * You need to pass the employee object to the view using the model object.
         */
        return "";
    }

    /*
     * This method returns an employee with the given ID from the database.
     */
    private Employee getEmployeeById(Long id) throws Exception {
        Employee employee = new Employee();
        /*
         * Write code below to return an employee with the given ID from the database
         * You need to fetch the data from the employees table in the database
         * and create an object of Employee class.
         * Handle exceptions carefully.
         */
        return employee;
    }

    public String editEmployee(@PathVariable long id, @ModelAttribute Employee employee, Model model) {
        /*
         * Write code here to update the details of the employee in the database
         * Handle exceptions carefully.
         */
        return "";
    }

    /*
     * TASK 6: Implement the feature to delete an employee.
     * As part of this task, you need to implement 2 methods:
     * 1. Implement showDeleteForm() to display the delete-employee form
     * 2. Implement deleteEmployee() to delete the employee from the database
     * 
     * Note: Write proper annotations for each of these methods
     */
    public String showDeleteForm(@PathVariable Long id, Model model) {
        /*
         * Write code here to display the delete employee form
         * The form should already have details of the employee to be deleted.
         * 
         * Hint: You need to get the employee details by ID and add it to the model object.
         * Use getEmployeeByID() method here.
         * 
         * Note: delete-employee.html page already exists under resources/templates/ directory.
         * You need to pass the employee object to the view using the model object.
         */
        return "delete-employee";
    }

    public String deleteEmployee(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        /*
         * Write code here to delete the employee with the given ID from the database
         * Handle exceptions carefully.
         * After successful deletion, redirect to homepage.
         */
        return "";
    }
}
