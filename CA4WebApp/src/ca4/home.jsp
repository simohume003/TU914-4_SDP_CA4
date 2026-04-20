<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Product" %>
<%@ page import="entities.Customer" %>
<%@ page import="java.util.Map" %>
<%@ page import="entities.Review" %>
<!DOCTYPE html>
<html lang="en">
<%
    Customer customer = (Customer) session.getAttribute("customer");
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loops and Looms</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        header {
            background-color: #222;
            color: white;
            text-align: center;
            padding: 20px;
        }

        .top-bar {
            background-color: #ddd;
            padding: 15px;
            display: flex;
            justify-content: center;
            gap: 10px;
            flex-wrap: wrap;
         
        }

        .top-bar input[type="text"] {
            padding: 8px;
            width: 250px;
        }

        .top-bar button {
            padding: 8px 14px;
            cursor: pointer;
        }
        .top-bar select {
    padding: 8px;
}

        .main-content {
            padding: 30px;
        }

        .product-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
        }

        .product-card {
            background-color: white;
            border: 1px solid #ccc;
            padding: 15px;
            text-align: center;
            border-radius: 8px;
        }

        .product-image {
            width: 100%;
            height: 300px;
            background-color: #e0e0e0;
            line-height: 150px;
            margin-bottom: 10px;
            border-radius: 5px;
        }

        .product-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            display: block;
        }

        .product-card h3 {
            margin: 10px 0 5px;
        }

        .product-card p {
            margin: 5px 0;
        }

        .product-card button {
            margin-top: 10px;
            padding: 8px 12px;
            cursor: pointer;
        }
        .reviews-section {
    margin-top: 10px;
    font-size: 14px;
    background-color: #fafafa;
    padding: 8px;
    border-radius: 5px;
}

        @media (max-width: 900px) {
            .product-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 500px) {
            .product-grid {
                grid-template-columns: 1fr;
            }
            .login-status {
   				 text-align: center;
   				 padding: 10px;
   				 background-color: #eee;
    				font-weight: bold;
					}
        }
    </style>
</head>
<body>

    <header>
        <h1>Loops and Looms</h1>
        <p>Fashion “compiled” perfectly</p>
    </header>

    <div class="top-bar">
       <form action="products" method="get" style="display:flex; gap:10px; flex-wrap:wrap; align-items:center;">
    <input type="text" name="search" placeholder="Search for clothes...">

    <select name="filter">
        <option value="title">Title</option>
        <option value="category">Category</option>
        <option value="manufacturer">Manufacturer</option>
    </select>

    <select name="sort">
        <option value="">No Sort</option>
        <option value="priceAsc">Price Low-High</option>
        <option value="priceDesc">Price High-Low</option>
        <option value="titleAsc">Title A-Z</option>
        <option value="titleDesc">Title Z-A</option>
        <option value="manufacturerAsc">Manufacturer A-Z</option>
        <option value="manufacturerDesc">Manufacturer Z-A</option>
    </select>

    <button type="submit">Search</button>
</form>
        <button onclick="window.location.href='auth'">Customer Login</button>
        <a href="adminlogin.jsp"><button >Admin Login</button></a>
        <button onclick="window.location.href='cart'">Shopping Cart</button>
        <button onclick="window.location.href='review'">Leave Review</button>
    </div>
    <div class="login-status">
        <% if (customer != null) { %>
            Signed in as: <strong><%= customer.getEmail() %></strong>
        <% } else { %>
            Not signed in
        <% } %>
    </div>

    <div class="main-content">
        <h2>Shop Items</h2>

        <div class="product-grid">
            <%
                List<Product> products = (List<Product>) request.getAttribute("products");
                if (products != null && !products.isEmpty()) {
                    for (Product p : products) {
            %>
                <div class="product-card">
                    <div class="product-image">
                        <% if (p.getImageUrl() != null && !p.getImageUrl().isEmpty()) { %>
                            <img src="<%= p.getImageUrl() %>" alt="<%= p.getTitle() %>">
                        <% } else { %>
                            Image
                        <% } %>
                    </div>
                    <h3><%= p.getTitle() %></h3>
                    <p><strong>Manufacturer:</strong> <%= p.getManufacturer() %></p>
                    <p><strong>Category:</strong> <%= p.getCategory() %></p>
                    <p><strong>Price:</strong> €<%= p.getPrice() %></p>
                    <p><strong>Stock:</strong> <%= p.getStockQuantity() %></p>
                    <%
    Map<Integer, List<Review>> reviewsMap =
        (Map<Integer, List<Review>>) request.getAttribute("reviewsMap");

 List<Review> reviews = (reviewsMap != null) ? reviewsMap.get(p.getId()) : null;
%>
<div class="reviews-section">

<%
    if (reviews != null && !reviews.isEmpty()) {

        double avg = 0;
        for (Review r : reviews) {
            avg += r.getRating();
        }
        avg = avg / reviews.size();
%>

    <p><strong>⭐ Rating:</strong> <%= String.format("%.1f", avg) %> / 5</p>

    <%
        for (Review r : reviews) {
    %>
        <p>"<%= r.getComment() %>" — <%= r.getCustomer().getEmail() %></p>
    <%
        }
    %>

<%
    } else {
%>
    <p>No reviews yet</p>
<%
    }
%>

</div>
                    <form action="cart" method="post">
    <input type="hidden" name="action" value="add">
    <input type="hidden" name="productId" value="<%= p.getId() %>">
    <button type="submit">Add to Cart</button>
</form>
                </div>
            <%
                    }
                } else {
            %>
                <p>No products found.</p>
            <%
                }
            %>
        </div>
    </div>

</body>
</html>