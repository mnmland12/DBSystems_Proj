<%@ page import="java.util.ArrayList" %>
<%@ page import="DataModel.InventoryModel" %>
<%@ page import="DataModel.ProductModel" %>
<%@ page import="DataModel.TransactionModel" %>
<%@ page import="DataModel.ProductsSoldModel" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th{
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
            background-color: #f2f2f2;
        }
        .container {
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
    </style>
</head>

<body>
    <h2>Payment page</h2>

    <table>
        <thead>
            <tr>
                <th>ProductID</th>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
            <% ArrayList<ProductsSoldModel> PSList = (ArrayList<ProductsSoldModel>) session.getAttribute("productsSoldModel");
                ArrayList<ProductModel> products = (ArrayList<ProductModel>) session.getAttribute("selectedProducts");
        
                if (PSList != null && products != null) {
                    for (int i = 0; i < PSList.size(); i++) {
                        ProductsSoldModel p = PSList.get(i);
                        ProductModel product = products.get(i);

                        int productID = p.getProductID();
                        String productName = product.getProductName();
                        int quantity = p.getQuantity();
                        double price = p.getTotalPrice();
            %>
                <tr>
                    <td><%= productID %></td>
                    <td><%= productName %></td>
                    <td><%= quantity %></td>
                    <td><%= price %></td>
                </tr>
            <% 
                }}
                TransactionModel t = (TransactionModel) session.getAttribute("transactionModel");
                double total = t.getTotal();
            %>
            <tr>
                <td colspan="3"><b>TOTAL = </b></td>
                <td><%= total %></td>
            </tr>
        </tbody>
    </table>
    <div class="container">
    <form action="completeTransaction" method="post">
        <label for="transactionNote">Transaction Note:</label><br>
        <textarea id="transactionNote" name="transactionNote" rows="4" cols="50"></textarea><br><br>
        <button type="submit" class="button" name="paymentMethod" value="Credit">Credit Card</button>
        <button type="submit" class="button" name="paymentMethod" value="Debit">Debit Card</button>
        <button type="submit" class="button" name="paymentMethod" value="Cash">Cash</button><br>
    </form>
    </div>
</body>
</html>