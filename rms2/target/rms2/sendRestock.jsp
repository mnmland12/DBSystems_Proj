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
    <h2>Inventory Sent</h2>

    <h2>New Location Inventory</h2>
       <table>
        <thead>
            <tr>
                <th>ProductID</th>
                <th>Product Name</th>
                <th>Quantity In Stock</th>
            </tr>
        </thead>
        <tbody>
            <% 
                ArrayList<InventoryModel> newLocInv = (ArrayList<InventoryModel>) session.getAttribute("newInventoryList");
                if (newLocInv != null) {
                    for (int i = 0 ; i < newLocInv.size() ; i++) {
                        InventoryModel inv = newLocInv.get(i);
                        ProductModel p = inv.getProduct();
                        int pID = p.getProductID();
                        String pName = p.getProductName();
                        int quantity = inv.getStockQuantity();
                %>
                <tr>
                    <td><%= pID %></td>
                    <td><%= pName %></td>
                    <td><%= quantity %></td>
                </tr>
                <% 
                        }}
                %>
        </tbody>
    </table>

    <h2>New Warehouse Inventory</h2>
       <table>
        <thead>
            <tr>
                <th>ProductID</th>
                <th>Product Name</th>
                <th>Quantity In Stock</th>
            </tr>
        </thead>
        <tbody>
            <% 
                ArrayList<InventoryModel> newWarehouseInv = (ArrayList<InventoryModel>) session.getAttribute("newWarehouseInv");
                if (newWarehouseInv != null) {
                    for (int i = 0 ; i < newWarehouseInv.size() ; i++) {
                        InventoryModel inv = newWarehouseInv.get(i);
                        ProductModel p = inv.getProduct();
                        int pID = p.getProductID();
                        String pName = p.getProductName();
                        int quantity = inv.getStockQuantity();
                %>
                <tr>
                    <td><%= pID %></td>
                    <td><%= pName %></td>
                    <td><%= quantity %></td>
                </tr>
                <% 
                        }}
                %>
        </tbody>
    </table>

    <form action="homepage" method="post">
        <button type="submit" class="button">Back to Homepage</button>
    </form>
</body>
</html>