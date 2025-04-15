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
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee, Model model) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO employee (id, name, email, salary, department) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, employee.getId());
            statement.setString(2, employee.getName());
            statement.setString(3, employee.getEmail());
            statement.setDouble(4, employee.getSalary());
            statement.setString(5, employee.getDepartment());
            statement.executeUpdate();

            model.addAttribute("success", "Successfully added employee " + employee.getName());
            // Explicitly create new object to clear the form
            Employee newEmployee = new Employee();
            model.addAttribute("employee", newEmployee);
            return "add-employee";
        } catch (SQLException e) {
            model.addAttribute("error", "Error adding employee " + e.getMessage());
            e.printStackTrace();
            return "add-employee";
        }
    }

    
    
        
      
    

    /*
     * TASK 4: Implement the feature to search employees by name. 
     * As part of this task, you need to implement searchEmployees() method.
     * The method should fetch the list of employees whose name contains 
     * the search keyword from the database and populate in index.html
     * 
     * Note: Write proper annotation for the method. 
     */
    @GetMapping("/search")
    public String searchEmployees(@RequestParam String name, Model model) {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM employee WHERE name LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            // Iterate over the result set and create a list of employees
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setDepartment(resultSet.getString("department"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        model.addAttribute("employees", employees);
        return "index";
    }


    /*
     * TASK 5: Implement the feature to edit an employee.
     * As part of this task, you need to implement 2 methods:
     * 1. Implement showEditForm() to display the edit-employee form
     * 2. Implement editEmployee() to update the details of the employee in the database
     * 
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Employee employee = getEmployeeById(id);
            model.addAttribute("employee", employee);
            return "edit-employee";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }


    /*
     * This method returns an employee with the given ID from the database.
     */
    private Employee getEmployeeById(Long id) throws Exception {
        Employee employee = new Employee();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM employee WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setDepartment(resultSet.getString("department"));
            } else {
                throw new Exception("Employee not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    /*
     * This method updates an employee with the given ID in the database.
     */
    @PostMapping("/edit/{id}")
    public String editEmployee(@PathVariable long id, @ModelAttribute Employee employee, Model model) {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE employee SET name = ?, email = ?, salary = ?, department = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getEmail());
            statement.setDouble(3, employee.getSalary());
            statement.setString(4, employee.getDepartment());
            statement.setLong(5, id);
            statement.executeUpdate();

            model.addAttribute("success", "Successfully updated employee " + employee.getName());
            return "edit-employee";
        } catch (SQLException e) {
            model.addAttribute("error", "Error updating employee " + e.getMessage());
            e.printStackTrace();
            return "edit-employee";
        }
    }

    /*
     * TASK 6: Implement the feature to delete an employee.
     * As part of this task, you need to implement 2 methods:
     * 1. Implement showDeleteForm() to display the delete-employee form
     * 2. Implement deleteEmployee() to delete the employee from the database
     * 
     * Note: Write proper annotations for each of these methods
     */
    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model) {
        try {
            Employee employee = getEmployeeById(id);
            model.addAttribute("employee", employee);
            return "delete-employee";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE from employee WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
            redirectAttributes.addFlashAttribute("success", "Employee with ID " + id + " is successfully deleted!");

            return "redirect:/";
        } catch (SQLException e) {
            model.addAttribute("error", "Error updating employee " + e.getMessage());
            e.printStackTrace();
            return "delete-employee";
        }
    }
}
