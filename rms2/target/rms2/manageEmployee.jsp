<%@ page import="java.util.ArrayList" %>
<%@ page import="DataModel.LocationModel" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .container {
            display: flex;
        }

        .split {
            width: 50%;
        }

        .bottom-button {
            position: fixed;
            bottom: 20px;
            left: 50%;
            transform: translateX(-50%);
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="split">
    <h2>Delete Employee</h2>
    <form action="employeeServlet" method="get">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required><br>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required><br>
        <button type="submit" class="button">Delete Employee</button>
    </form>
    </div>
    <div class="split">
    <h2>Create New Employee</h2>
    <form action="employeeServlet" method="post">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required></input><br>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required><br>
        <label for="position">Position:</label>
        <input type="text" id="position" name="position" required><br>
                <label for="location">Location:</label>
        <select id="location" name="location" required>
            <option value="" disabled selected>Select Location</option>
            <% 
                ArrayList<LocationModel> locations = (ArrayList<LocationModel>) (session.getAttribute("locations"));
                for (LocationModel location : locations) {
            %>
            <option value="<%= location.getLocationID() %>"><%= location.getLocationName() %></option>
            <% 
                } 
            %>
        </select><br>
        <button type="submit" class="button">Add Employee</button>
    </form>
    </div>
</div>
    <form action="homepage" method="post" class="bottom-button">
        <button type="submit" class="button">Back to Homepage</button>
    </form>
</body>
</html>