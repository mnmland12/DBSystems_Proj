<%@ page import="java.util.ArrayList" %>
<%@ page import="DataModel.InventoryModel" %>
<%@ page import="DataModel.ProductModel" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventory Information</title>
    <link rel="stylesheet" href="css/styles.css">

    <style>
        .button-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            margin-bottom: 10px; /* Add some margin between button rows */
        }

        .button-container button,
        .button-container input {
            margin-bottom: 10px; /* Add some margin between buttons and input */
        }

        .button-container input {
            width: 20%; /* Make the input box fill the entire width */
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

<div class="table-container">
    <form action="updateWarehouse" method="post">
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
            <% ArrayList<InventoryModel> invList = (ArrayList<InventoryModel>) session.getAttribute("inventory");
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
                    <td><input type="number" name="quantity_<%= productID %>" value="<%= quantity %>"></td>
                </tr>
                <% 
                        }}
                %>
        </tbody>
    </table>
    <input type="submit" class="button" value="Submit Products">
    </form>
</div>
    <form action="homepage" method="post">
        <button type="submit" class="button">Back to Homepage</button>
    </form>


</body>
</html>