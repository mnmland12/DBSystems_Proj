<%@ page import="java.util.ArrayList" %>
<%@ page import="DataModel.ProductModel" %>

<!DOCTYPE html>
<html>
<head>
    <title>Confirm Insertion</title>
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
    <h2>Add Product Successful.</h2>
    <h3>Check Added Product Details</h3>
        <table>
        <thead>
            <tr>
                <th>ProductID</th>
                <th>Product Name</th>
                <th>Product Size</th>
                <th>More Info</th>
            </tr>
        </thead>
        <tbody>
            <% ArrayList<ProductModel> productList = (ArrayList<ProductModel>) request.getAttribute("productList");
                if (productList != null) {
                    for (ProductModel p : productList) {
                        int productID = p.getProductID();
                        String productName = p.getProductName();
                        String size = p.getSize();
                %>
                <tr>
                    <td><%= productID %></td>
                    <td><%= productName %></td>
                    <td><%= size %></td>
                    <td><a href="productDetailsServlet?productId=<%= productID %>">More Info</a></td>
                </tr>
                <% 
                        }}else{
                %>
                    <td></td>
                    <td>No Results</td>
                    <td></td>
                <%
                        }
                %>
        </tbody>
    </table>

    <form action="homepage" method="post">
        <button type="submit" class="button">Back to Homepage</button>
    </form>
</body>
</html>