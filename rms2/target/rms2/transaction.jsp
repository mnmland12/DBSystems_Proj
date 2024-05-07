<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Transaction</title>
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
    <h2>New Transaction</h2>

    <form id="transactionForm" action="processTransaction" method="post">
        <div class="table-container" style="height: 80vh; overflow-y: scroll;">
            <table>
                <thead>
                    <tr>
                        <th>Select</th>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Quantity</th>
                    </tr>
                </thead>
                <tbody>
                    <% HashMap<Integer, String> productMap = (HashMap<Integer, String>) session.getAttribute("productMap");
                    if (productMap != null) {
                        for (Map.Entry<Integer, String> entry : productMap.entrySet()) {
                            int productId = entry.getKey();
                            String productName = entry.getValue();
                    %>
                    <tr>
                        <td><input type="checkbox" name="selectedProducts" value="<%= productId %>" ></td>
                        <td><%= productId %></td>
                        <td><%= productName %></td>
                        <td><input type="number" name="quantity_<%= productId %>" min="0" value="0"></td>
                    </tr>
                    <% 
                        }
                    }
                    %>
                </tbody>
            </table>
        </div>
        <button type="submit" class="button">Submit</button>
    </form>

    <h2></h2>
    <form action="homepage" method="post">
        <button type="submit" class="button">Back to Homepage</button>
    </form>

    <script>
    </script>
</body>
</html>
