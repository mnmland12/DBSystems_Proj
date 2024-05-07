<%@ page import="DataModel.EmployeeModel" %>
<%@ page import="DataModel.LocationModel" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Employee Details</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .header {
            text-align: center;
            margin-bottom: 20px;
        }

        .container {
            text-align: center;
            font-size: 24px; /* Adjust font size as needed */
            margin-bottom: 20px;
        }

        .employee-id {
            font-size: 36px; /* Larger font size for employee ID */
            font-weight: bold; /* Make the ID bold */
        }

        .button-container {
            text-align: center;
        }
    </style>
</head>
<body>
    <% EmployeeModel emp = (EmployeeModel) (request.getAttribute("employee")); 
        LocationModel eLoc = (LocationModel) (request.getAttribute("location"));
    %>
<div class="container">
    <h2>Employee Details</h2>
    <p>Employee ID: <%= emp.getEmpID() %></p>
    <p>First Name: <%= emp.getFirstName() %></p>
    <p>Last Name: <%= emp.getLastName() %></p>
    <p>Position: <%= emp.getPosition() %></p>
    <p>Location: <%= eLoc.getLocationName() %></p>

    <form action="deleteEmployee" method="post">
        <input type="hidden" name="empID" value="<%= emp.getEmpID() %>">
        <button class="button" type="submit" onclick="return confirm('Are you sure you want to delete this employee?');">Delete Employee</button>
    </form>
</div>
</body>
</html>
