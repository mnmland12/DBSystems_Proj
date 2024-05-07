<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventory Information</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        h2 {
            margin-top: 0;
        }

        form:last-of-type {
            margin-top: auto;
        }
        body {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }

        .table-container {
            width: 90%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .table-container thead {
            position: sticky;
            top: 0; 
            background-color: white;
            z-index: 1; 
        }

        th, td {
            text-align: center;
            padding: 8px;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ddd;
        }

        form {
            text-align: center;
        }

        button {
            padding: 10px 20px;
            margin: 10px;
        }
    </style>
</head>

<body>
    <h2>Choose a Store Location</h2>
    
    <form id="locForm" action="inventorySearch" method="get">
        <select name="location" id="location">
                <% HashMap<Integer, String> locMap = (HashMap<Integer, String>) session.getAttribute("locationMap");
                if (locMap != null) {
                    for (Map.Entry<Integer, String> entry : locMap.entrySet()) {
                        int locId = entry.getKey();
                        String locName = entry.getValue();
                %>
                <option value="<%= locId %>"><%= locName %></option>
                <% 
                        }
                    }
                %>
        </select>
        <button type="submit" class="button">Get Inventory For Selected Location</button>
    </form>
    <form action="updateWarehouse" method="get">
        <button type="submit" class="button" name="action" value="updateWarehouse" id="getLocation">Update Warehouse Stock</button><br>
    </form>
    <form action="homepage" method="post">
        <button type="submit" class="button">Back to Homepage</button>
    </form>

</body>