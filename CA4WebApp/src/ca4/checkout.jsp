<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="entities.Customer" %>
<%
    Customer customer = (Customer) session.getAttribute("customer");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 20px;
        }

        .container {
            max-width: 600px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            border: 1px solid #ccc;
        }

        input {
            width: 100%;
            padding: 10px;
            margin: 8px 0 15px 0;
            box-sizing: border-box;
        }

        button {
            padding: 10px 16px;
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Checkout</h1>

    <% if (customer != null) { %>
        <p>Ordering as: <strong><%= customer.getEmail() %></strong></p>
    <% } %>

    <form action="checkout" method="post">
        <label>Shipping Address</label>
        <input type="text" name="shippingAddress" required>

        <label>City</label>
        <input type="text" name="city" required>

        <label>County</label>
        <input type="text" name="county" required>

        <label>Payment Method / Fake Card</label>
        <input type="text" name="paymentMethod" required>

        <button type="submit">Confirm Order</button>
    </form>
</div>

</body>
</html>