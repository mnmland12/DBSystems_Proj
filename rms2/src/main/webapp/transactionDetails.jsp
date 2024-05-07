<%@ page import="DataModel.TransactionModel" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="DataModel.LocationModel" %>
<%@ page import="DataModel.ProductModel" %>
<%@ page import="DataModel.EmployeeModel" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction Details</title>
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
    <h2>Transaction Details</h2>

    <%  TransactionModel tMod = (TransactionModel)(session.getAttribute("transaction"));

    %>
    <h2>Transaction Total: $<%= tMod.getTotal() %> On <%= tMod.getDate() %></h2>
    <h2>Sold By <%= tMod.getEmployee().getFirstName() %> <%= tMod.getEmployee().getLastName() %> at <%= tMod.getLocation().getLocationName() %></h2>
    <h3>Notes: <%= tMod.getNotes() %></h3>
    <h2>Products Sold</h2>
    <table>
        <thead>
            <tr>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
            <%  HashMap<ProductModel, Integer> soldProducts = (HashMap<ProductModel, Integer>)(session.getAttribute("soldProducts"));
                for (Map.Entry<ProductModel, Integer> entry : soldProducts.entrySet()){
                    ProductModel p = entry.getKey();
                    int productID = p.getProductID();
                    int q = entry.getValue();
                    double price = p.getPrice() * q;
            %>
            <tr>
                <td><a href="productDetailsServlet?productId=<%= productID %>"><%= p.getProductName() %></td>
                <td><%= q %></td>
                <td><%= price %></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>


    <form action="chooseAction" method="post">
        <button type="submit" class="button" name="action" value="homepage">Back to Homepage</button>
        <button type="submit" class="button" name="action" value="getPastData">Sales Data</button>
    </form>

</body>
</html>