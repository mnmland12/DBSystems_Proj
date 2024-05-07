<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="DataModel.ProductModel" %>
<%@ page import="DataModel.TransactionModel" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Location Sales Information</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        h2 {
            margin-top: 50px;
        }
        body, html {
            height: 100%;
            margin: 250px;
            display: flex;
            justify-content: center;
            align-items: center;
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
            margin-top: 20px;
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
    <h2>Location Sales Information</h2>

    <!-- Display total sales from each day -->
    <h3>Total Sales by Day</h3>
    <table>
        <thead>
            <tr>
                <th>Day</th>
                <th>Total Sales</th>
            </tr>
        </thead>
        <tbody>
            <!-- Iterate over sales data and display in table -->
            <%
                HashMap<Date, Double> dailySales = (HashMap<Date, Double>) (session.getAttribute("totals"));
                DecimalFormat df = new DecimalFormat("#.##");
                for (Map.Entry<Date, Double> entry : dailySales.entrySet()) {
            %>
            <tr>
                <td><%= entry.getKey() %></td>
                <td>$<%= df.format(entry.getValue()) %></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <h3>Most Sold Products Yesterday</h3>
    <table>
        <thead>
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Total Sales</th>
            </tr>
        </thead>
        <tbody>
            <%
                HashMap<ProductModel, Integer> pastDay = (HashMap<ProductModel, Integer>) (session.getAttribute("pastDay"));
                for (Map.Entry<ProductModel, Integer> entry : pastDay.entrySet()) {
                    ProductModel product = entry.getKey();
            %>
            <tr>
                <td><%= product.getProductID() %></td>
                <td><a href="productDetailsServlet?productId=<%= product.getProductID() %>"><%= product.getProductName() %></td>
                <td><%= entry.getValue()  %></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <h3>Most Sold Products Last Week</h3>
    <table>
        <thead>
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Total Sales</th>
            </tr>
        </thead>
        <tbody>
            <%
                HashMap<ProductModel, Integer> pastWeek = (HashMap<ProductModel, Integer>) (session.getAttribute("pastWeek"));
                for (Map.Entry<ProductModel, Integer> entry : pastWeek.entrySet()) {
                    ProductModel product = entry.getKey();
            %>
            <tr>
                <td><%= product.getProductID() %></td>
                <td><a href="productDetailsServlet?productId=<%= product.getProductID() %>"><%= product.getProductName() %></td>
                <td><%= entry.getValue()  %></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <h3>Most Sold Products Last Month</h3>
    <table>
        <thead>
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Total Sales</th>
            </tr>
        </thead>
        <tbody>
            <%
                HashMap<ProductModel, Integer> pastMonth = (HashMap<ProductModel, Integer>) (session.getAttribute("pastMonth"));
                for (Map.Entry<ProductModel,Integer> entry : pastMonth.entrySet()) {
                    ProductModel product = entry.getKey();
            %>
            <tr>
                <td><%= product.getProductID() %></td>
                <td><a href="productDetailsServlet?productId=<%= product.getProductID() %>"><%= product.getProductName() %></td>
                <td><%= entry.getValue()  %></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <h2>Choose a New Store Location</h2>
    <form id="locForm" action="getPastInfo" method="post">
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
        <button type="submit" class="button">Get Information</button>
    </form>
    <form action="homepage" method="post">
        <button type="submit" class="button">Back to Homepage</button>
    </form>
</body>
</html>
