<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.CartItem" %>
<%@ page import="entities.Customer" %>
<%
    Customer customer = (Customer) session.getAttribute("customer");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }

        .container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            border: 1px solid #ccc;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table, th, td {
            border: 1px solid #ccc;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        input[type="number"] {
            width: 70px;
        }

        button {
            padding: 8px 12px;
            cursor: pointer;
        }

        .total {
            margin-top: 20px;
            font-size: 18px;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Shopping Cart</h1>

    <% if (customer != null) { %>
        <p>Logged in as: <strong><%= customer.getEmail() %></strong></p>
    <% } %>

    <%
        List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
        double total = 0.0;

        if (cartItems != null && !cartItems.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Subtotal</th>
                <th>Update</th>
                <th>Delete</th>
            </tr>

            <%
                for (CartItem item : cartItems) {
                    double subtotal = item.getProduct().getPrice() * item.getQuantity();
                    total += subtotal;
            %>
            <tr>
                <td><%= item.getProduct().getTitle() %></td>
                <td>€<%= item.getProduct().getPrice() %></td>
                <td>
                    <form action="cart" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="cartItemId" value="<%= item.getId() %>">
                        <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1">
                </td>
                <td>€<%= subtotal %></td>
                <td>
                        <button type="submit">Update</button>
                    </form>
                </td>
                <td>
                    <form action="cart" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="cartItemId" value="<%= item.getId() %>">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>

        <div class="total">
            Total: €<%= total %>
        </div>
    <%
        } else {
    %>
        <p>Your cart is empty.</p>
    <%
        }
    %>
</div>
<form action="checkout" method="get">
    <button type="submit">Proceed to Checkout</button>
</form>

</body>
</html>