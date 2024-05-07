<%@ page import="DataModel.ProductModel" %>
<%@ page import="DataModel.InventoryModel" %>
<%@ page import="DataModel.TransactionModel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Connection" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Details</title>
    <style>
        .container {
            text-align: center;
            margin-top: 50px; /* Adjust the margin as needed */
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
        .flex-container {
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
        }
        .flex-item {
            flex-basis: 45%; /* Adjust the width of each item */
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <% ProductModel p =(ProductModel)(request.getAttribute("product"));
        ArrayList<TransactionModel> tList = (ArrayList<TransactionModel>)(request.getAttribute("pTransactions"));
        ArrayList<InventoryModel> invList = (ArrayList<InventoryModel>)(request.getAttribute("inventoryList"));
    %>

    <div class="container">
        <h2><%= p.getProductName() %> Details</h2>
        <p>Product ID: <%= p.getProductID() %></p>
        <p>Product Name: <%= p.getProductName() %></p>
        <p>Size: <%= p.getSize()%></p>
        <p>Price: $<%= p.getPrice() %></p>
        <% String notes = p.getDescription();
            if(notes != null && !notes.isEmpty()) {%>
        <p>Notes: <%= notes%></p>
        <% } %>
    </div>

    <div class="flex-container">
        <div class="flex-item">
            <h2>Inventory Information</h2>

            <table>
                <thead>
                    <tr>
                        <th>Location</th>
                        <th>Stock Quantity</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(InventoryModel inventory : invList){
                    %>
                    <tr>
                            <td><%= inventory.getLocation().getLocationName()%></td>
                            <td><%= inventory.getStockQuantity() %></td>
                        <% } %>
                    </tr>
                </tbody>
            </table>
    </div>

        <div class="flex-item">
            <h2>Recent Transactions</h2>
            
            <table>
                <thead>
                    <tr>
                        <th>TransactionID</th>
                        <th>Date</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(TransactionModel transaction : tList){
                    %>
                    <tr>
                        <td>
                            <form id="transactionForm" action="recentTransactions" method="post">
                            <input type="hidden" name="transactionID" value="<%= transaction.getTransactionID() %>">
                                <a href="#" onclick="document.getElementById('transactionForm').submit(); return false;">
                                <%= transaction.getTransactionID() %>
                                </a>
                            </form>
                        </td>
                        <td><%= transaction.getDate() %></td>
                        <td><%= transaction.getTotal() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <form action="homepage" method="post">
        <button type="submit" class="button">Back to Homepage</button>
    </form>


</body>
</html>