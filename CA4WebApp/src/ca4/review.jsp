<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.OrderItem" %>

<!DOCTYPE html>
<html>
<head>
    <title>Leave a Review</title>
</head>
<body>

<h1>Review Your Purchases</h1>

<%
    List<OrderItem> items = (List<OrderItem>) request.getAttribute("orderItems");
    if (items != null && !items.isEmpty()) {
        for (OrderItem item : items) {
%>

<form action="review" method="post">
    <h3><%= item.getProduct().getTitle() %></h3>

    <input type="hidden" name="productId" value="<%= item.getProduct().getId() %>">

    Rating:
    <select name="rating">
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
    </select>

    <br>

    Comment:
    <input type="text" name="comment">

    <br>

    <button type="submit">Submit Review</button>
</form>

<hr>

<%
        }
    } else {
%>
<p>No purchases to review.</p>
<%
    }
%>

</body>
</html>