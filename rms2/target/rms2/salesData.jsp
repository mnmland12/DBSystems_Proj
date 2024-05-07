<%@ page import="DataModel.TransactionModel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DataModel.LocationModel" %>
<%@ page import="DataModel.EmployeeModel" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transactions</title>
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
    <h2>Recent Transactions</h2>
    <table>
        <thead>
            <tr>
                <th>TransactionID</th>
                <th>Date</th>
                <th>Location</th>
                <th>Employee</th>
                <th>Total</th>
            </tr>
        </thead>
        <tbody>
            <%  ArrayList<TransactionModel> tList = (ArrayList<TransactionModel>)(session.getAttribute("transactionList"));
                for(TransactionModel transaction : tList){
                    LocationModel loc = transaction.getLocation();
                    EmployeeModel emp = transaction.getEmployee();
                    int transactionID = transaction.getTransactionID();
            %>
            <tr>
                <td>
                    <form id="transactionForm<%= transactionID %>" action="recentTransactions" method="post">
                    <input type="hidden" name="transactionID" value="<%= transactionID %>">
                        <a href="#" onclick="document.getElementById('transactionForm<%= transactionID %>').submit(); return false;">
                        <%= transactionID %>
                        </a>
                    </form>
                </td>
                <td><%= transaction.getDate() %></td>
                <td><%= loc.getLocationName() %></td>
                <td><%= emp.getFirstName() %> <%= emp.getLastName() %></td>
                <td><%= transaction.getTotal() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <form action="homepage" method="post">
        <button type="submit" class="button">Back to Homepage</button>
    </form>

</body>
</html>