<%@ page import="java.util.ArrayList" %>
<%@ page import="DataModel.InventoryModel" %>
<%@ page import="DataModel.ProductModel" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventory Information</title>
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

        .button {
            margin: 10px;
            padding: 10px 20px;
            font-size: 16px;
            border: 2px solid #007bff; /* Button border color */
            border-radius: 5px;
            background-color: #0056b3; /* Button background color */
            color: #fff; 
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .button:hover {
            background-color: #0056b3; /* Button background color on hover */
            border-color: #0056b3; /* Button border color on hover */
        }
        button {
            padding: 10px 20px;
            margin: 10px;
        }
        input[type="number"],
        select {
            width: 200px;
            /* Adjust the width as needed */
            padding: 10px;
            /* Adjust padding as needed */
            margin: 5px;
            /* Adjust margin as needed */
            border: 1px solid #ccc;
            /* Add border */
            border-radius: 5px;
            /* Add border radius */
        }
    </style>
</head>
<body>
    <h2>Inventory Information</h2>

    <form action="chooseAction" method="post" class="button-container">
        <button type="submit" class="button" name="action" value="homepage">Back to Homepage</button>
        <button type="submit" class="button" name="action" value="getStockInfo" id="getLocation">Choose a New Location</button><br>
    </form>

    <form action="getTransferForm" method="get" class="button-container">
        <label for="aboveRestock">Above Restock Level:</label>
        <input type="number" id="aboveRestock" name="aboveRestock" min="0" value="0" required><br>
        <button type="submit" class="button" name="action">Get Warehouse Restock/Transfer Form</button><br>
    </form>

    <form action="getAutomatedTransfer" method="get" class="button-container">
        <button type="submit" class="button" name="action">Get Automated Restock Form</button><br>
    </form>

    <table>
        <thead>
            <tr>
                <th>ProductID</th>
                <th>Product Name</th>
                <th>Size</th>
                <th>Quantity In Stock</th>
            </tr>
        </thead>
        <tbody>
            <% ArrayList<InventoryModel> invList = (ArrayList<InventoryModel>) session.getAttribute("inventoryList");
                if (invList != null) {
                    for (InventoryModel i : invList) {
                        int productID = i.getProduct().getProductID();
                        String productName = i.getProduct().getProductName();
                        String size = i.getProduct().getSize();
                        int quantity = i.getStockQuantity();
                %>
                <tr>
                    <td><%= productID %></td>
                    <td><%= productName %></td>
                    <td><%= size %></td>
                    <td><%= quantity %></td>
                </tr>
                <% 
                        }}
                %>
        </tbody>
    </table>

    <script>
        //get all location info for the stock page
        document.getElementById("getLocation").addEventListener("click", function(){
            var xhr = new XMLHttpRequest();
            var url = 'getLocation';
            xhr.open('GET', url, true);

            xhr.onload = function() {
                if (xhr.status >= 200 && xhr.status < 300) {
                    console.log('Data loaded successfully:', xhr.responseText);
                } else {
                    console.error('Failed to load data:', xhr.status, xhr.statusText);
                }
            };

            xhr.send();
        });
    </script>
</body>
</html>