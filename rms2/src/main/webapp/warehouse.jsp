<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="DataModel.ProductModel" %>
<%@ page import="DataModel.InventoryModel" %>
<%@ page import="java.util.List" %>


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
    <h2>Warehouse Transfer Form</h2>
    <%
        HashMap<Integer, String> restockErrors = (HashMap<Integer, String>) request.getAttribute("restockErrors");
    %>

    <% if (restockErrors != null && !restockErrors.isEmpty()) { %>
        <script>
            alert("The following products cannot be fully restocked:\n\n<%= String.join("\n", restockErrors.values()) %>");
        </script>
    <% } %>

    <table>
        <thead>
            <tr>
                <th>ProductID</th>
                <th>Product Name</th>
                <th>Quantity to Send</th>
                <th>New Location Stock Quantity</th>
                <th>New Warehouse Stock Quantity</th>
            </tr>
        </thead>
        <tbody>
            <%  HashMap<ProductModel, Integer> restock = (HashMap<ProductModel, Integer>)(request.getAttribute("restockMap"));
                HashMap<Integer, String> restockError = (HashMap<Integer, String>)(request.getAttribute("restockError"));
                ArrayList<InventoryModel> invList = (ArrayList<InventoryModel>) (request.getAttribute("inventoryList"));
                ArrayList<InventoryModel> warehouseInv = (ArrayList<InventoryModel>)(request.getAttribute("warehouseInv"));
                ArrayList<InventoryModel> newInv = new ArrayList<InventoryModel>();
                ArrayList<InventoryModel> newWInv = new ArrayList<InventoryModel>();
                if(restock != null && !restock.isEmpty()){

                    for(Map.Entry<ProductModel, Integer> entry : restock.entrySet()){
                        int toRestock = entry.getValue();
                        if(toRestock > 0){
                            ProductModel p = entry.getKey();
                            int pID = p.getProductID();
                            String pName = p.getProductName();
                            InventoryModel matchingInventory = null;
                            for (InventoryModel inv : invList) {
                                if (inv.getProduct().getProductID() == pID) {
                                    matchingInventory = inv;
                                    break; 
                                }
                            } 
                            InventoryModel matchingWarehouseInv = null;
                            for (InventoryModel inv : warehouseInv) {
                                if (inv.getProduct().getProductID() == pID) {
                                    matchingWarehouseInv = inv;
                                    break; 
                                }
                            }   
                            int nStock = matchingInventory.getStockQuantity() + toRestock;       
                            matchingInventory.setStockQuantity(nStock);
                            int nWStock = matchingWarehouseInv.getStockQuantity() - toRestock;
                            matchingWarehouseInv.setStockQuantity(nWStock);
                            newInv.add(matchingInventory);       
                            newWInv.add(matchingWarehouseInv);
            %>
                <tr>
                    <td><%= pID %></td>
                    <td><%= pName %></td>
                    <td><%= toRestock %></td>
                    <td><%= nStock %></td>
                    <td><%= nWStock %></td>
                </tr>
            <%
                    }
                    }}
                request.getSession().setAttribute("newInventoryList", newInv);
                request.getSession().setAttribute("newWarehouseInv", newWInv);
            %>
        </tbody>
    </table>

    <form action="getTransferForm" method="post">
        <button type="submit" class="button">Send Products</button>
    </form>
    <form action="homepage" method="post">
        <button type="submit" class="button">Back to Homepage</button>
    </form>

</body>
</html>