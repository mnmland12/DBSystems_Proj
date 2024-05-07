<!DOCTYPE html>
<html lang="en">
<head>
    <title>Employee Created</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .header {
            text-align: center;
            margin-bottom: 20px;
        }

        .container {
            text-align: center;
            font-size: 24px; /* Adjust font size as needed */
            margin-bottom: 20px;
        }

        .employee-id {
            font-size: 36px; /* Larger font size for employee ID */
            font-weight: bold; /* Make the ID bold */
        }

        .button-container {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="header">New Employee Created</h2>
        <%
            int empID = (Integer)(session.getAttribute("empID"));
        %>
        <p class="employee-id">New Employee ID: <%= empID %> </p>

    <div class="button-container">
        <form action="homepage" method="post">
            <button type="submit" class="button">Back to Homepage</button>
        </form>
    </div>
</div>
</body>
</html>
