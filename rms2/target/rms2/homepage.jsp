<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>       
        .container {
            text-align: center;
            margin-top: 50px; /* Adjust the margin as needed */
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Welcome, <%= session.getAttribute("firstName")%> <%= session.getAttribute("lastName")%>!</h2>
        <p>You have access as a <%= session.getAttribute("position")%>.</p>
        <div class="button-container">
            <form action="chooseAction" method="post">
                <button type="submit" class="button" name="action" value="startTransaction" id="newTransaction">New Transaction</button>
                <button type="submit" class="button" name="action" value="searchProducts">Products</button>
                <button type="submit" class="button" name="action" value="getStockInfo" id="getLocation">Inventory Information</button>
                <!--<button type="submit" name="action" value="getWarehouseInfo" id="updateWarehouse">Update Warehouse Inventory</button>-->
                <button type="submit" class="button" name="action" value="getPastData">Sales Data</button>
                <button type="submit" class="button" name="action" value="manageEmployees">Manage Employees</button>
                <button type="submit" class="button" name="action" value="getFutureInfo">Future Analysis</button>
            </form><br>
        </div>
    </div>    
    <script>
        // to get all the product info for the newTransaction page
        document.getElementById("newTransaction").addEventListener("click", function() {

            var xhr = new XMLHttpRequest();
            var url = 'processTransaction';
            xhr.open('GET', url, true);

            xhr.onload = function() {
                if (xhr.status >= 200 && xhr.status < 300) {
                    console.log('Data loaded successfully:', xhr.responseText);
                } else {
                    console.error('Failed to load data:', xhr.status, xhr.statusText);
                }
            };

            xhr.send();
        });

        //get all location info for the stock page
        document.getElementById("getLocation").addEventListener("click", function(){
            var xhr = new XMLHttpRequest();
            var url = 'getLocation';
            xhr.open('GET', url, true);

            xhr.onload = function() {
                if (xhr.status >= 200 && xhr.status < 300) {
                    console.log('Data loaded successfully:', xhr.responseText);
                } else {
                    console.error('Failed to load data:', xhr.status, xhr.statusText);
                }
            };

            xhr.send();
        });
    </script>
    <div class="container">
    <form action="logout" method="post">
        <button type="submit" class="button">Logout</button>
    </form>
    </div>
</body>
</html>
