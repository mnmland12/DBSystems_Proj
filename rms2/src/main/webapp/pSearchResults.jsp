<%@ page import="DataModel.ProductModel" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
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

    <h2>Search Products</h2>
    <form id="searchForm" action="searchProduct" method="get">
        <input type="text" id="searchQuery" name ="query" placeholder="Search for a  product...">

        <select id="shirtSize" name="size">
            <option value="">Select Size</option>
            <option value="S">Small (S)</option>
            <option value="M">Medium (M)</option>
            <option value="L">Large (L)</option>
            <option value="XL">Extra Large (XL)</option>
        </select>
        <button type="submit" class="button">Search</button>
    </form>

    <% String prevSearch = (String)session.getAttribute("q");%>
    <h2>Results for <%= prevSearch %></h2>
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
            <% ArrayList<ProductModel> productList = (ArrayList<ProductModel>) session.getAttribute("searchResultsList");
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