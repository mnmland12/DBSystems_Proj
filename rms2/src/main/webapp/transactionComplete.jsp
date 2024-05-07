<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction Complete</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        body, html {
            height: 100%;
            margin: 0;
            display: flex;
            justify-content: center;
        }

        form {
            text-align: center;
        }

        h1 {
            margin-bottom: 20px; 
        }
        .container {
            text-align: center;
        }

    </style>
</head>
<body>

<div class="container">
    <h1>Transaction Completed Successfully!</h1>

    <form action="chooseAction" method="post">
        <button type="submit" class="button" name="action" value="startTransaction" id="newTransaction">New Transaction</button>
        <button type ="submit" class="button" name="action" value="homepage">Back to Homepage</button>
    </form>
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
    </script>
</body>