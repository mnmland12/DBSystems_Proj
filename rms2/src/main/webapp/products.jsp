<%@ page import="java.util.ArrayList" %>
<%@ page import="DataModel.LocationModel" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="DataAccess.DatabaseConnection" %>
<%@ page import="DataAccess.LocationConnect"%>

<!DOCTYPE html>
<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        body, html {
            height: 100%;
            margin: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .content {
            text-align: center;
        }
        button {
            padding: 10px 20px;
            margin: 10px;
        }
        input[type="text"],
        select {
            width: 200px; /* Adjust the width as needed */
            padding: 10px; /* Adjust padding as needed */
            margin: 5px; /* Adjust margin as needed */
            border: 1px solid #ccc; /* Add border */
            border-radius: 5px; /* Add border radius */
        }
        
        input[type="submit"] {
            width: auto; /* Allow submit button to adjust its width */
            padding: 10px 20px; /* Adjust padding as needed */
            margin: 5px; /* Adjust margin as needed */
            border: none; /* Remove border */
            border-radius: 5px; /* Add border radius */
            background-color: #007bff; /* Button background color */
            color: #fff; /* Button text color */
            cursor: pointer;
            transition: background-color 0.3s ease; /* Add transition */
        }

        input[type="submit"]:hover {
            background-color: #0056b3; /* Button background color on hover */
        }
    </style>
    </style>
</head>
<body>
<div class="content">
    <%  Connection conn = DatabaseConnection.getInstance();
        LocationConnect locConn = new LocationConnect(conn);
        ArrayList<LocationModel> locList = locConn.getAllLocations();
    %>
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

    <h2>Add Product</h2>
    <form id="addProductForm" action="addProduct" method="post">

        <h3>Product Information:</h3>
        <input type="text" id="newProductName" name="newProductName" placeholder="Enter New Product Name..."><br>

        <label for="price">Price:</label><br>
        <input type="number" id="price" name="price" min="0" step="0.01" required><br>

        <label for="sizes">Available Sizes:</label><br>
        <input type="checkbox" id="sizeS" name="sizes" value="S">
        <label for="sizeS">Small</label><br>
        <input type="checkbox" id="sizeM" name="sizes" value="M">
        <label for="sizeM">Medium</label><br>
        <input type="checkbox" id="sizeL" name="sizes" value="L">
        <label for="sizeL">Large</label><br>
        <input type="checkbox" id="sizeXL" name="sizes" value="XL">
        <label for="sizeXL">Extra Large</label><br>

        <label for="details">Details:</label><br>
        <textarea id="details" name="details" rows="4" cols="50"></textarea><br>

        <h3>Inventory Information:</h3>
        <% for (LocationModel location : locList) { %>
            <label for="location<%= location.getLocationID() %>"><%= location.getLocationName() %></label><br>
            <label for="quantity<%= location.getLocationID() %>">Quantity:</label>
            <input type="number" id="quantity<%= location.getLocationID() %>" name="quantity<%= location.getLocationID() %>" min="0" value="0" required>
            <label for="restockLevel<%= location.getLocationID() %>">Restock Level:</label>
            <input type="number" id="restockLevel<%= location.getLocationID() %>" name="restockLevel<%= location.getLocationID() %>" min="0" value="0" required><br>           
        <% } %>

        <button type="submit" class="button">Submit New Product</button>
    </form>

    <form action="homepage" method="post">
        <button type="submit" class="button">Back to Homepage</button>
    </form>
</div>
</body>
</html>