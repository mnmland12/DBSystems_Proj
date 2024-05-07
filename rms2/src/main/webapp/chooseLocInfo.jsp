<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="DataModel.LocationModel" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Choose Location</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h2>Choose a Store Location</h2>
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