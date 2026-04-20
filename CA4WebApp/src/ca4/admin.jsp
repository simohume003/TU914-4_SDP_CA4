<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Product" %>
<%@ page import="entities.Customer" %>
<%@ page import="entities.Order" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Loops and Looms</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }

        h1, h2 {
            color: #222;
        }

        .form-container, .table-container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 30px;
            border: 1px solid #ccc;
        }

        input, textarea {
            width: 100%;
            padding: 8px;
            margin: 8px 0 15px 0;
            box-sizing: border-box;
        }

        button {
            padding: 10px 16px;
            cursor: pointer;
            margin-right: 8px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        table, th, td {
            border: 1px solid #ccc;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        .delete-btn {
            background-color: #d9534f;
            color: white;
            border: none;
        }

        .edit-btn {
            background-color: #0275d8;
            color: white;
            border: none;
        }

        .add-btn {
            background-color: #5cb85c;
            color: white;
            border: none;
        }
        
        .table-input {
    	width: 100%;
   		 padding: 6px;
    	box-sizing: border-box;
}
    </style>
</head>
<body>

    <h1>Admin Page</h1>
            <button onclick="window.location.href='products'">Home</button>
    

    <div class="form-container">
        <h2>Add New Product</h2>
        <form action="adminProduct" method="post">
            <input type="hidden" name="action" value="add">

            <label>Title</label>
            <input type="text" name="title" required>

            <label>Manufacturer</label>
            <input type="text" name="manufacturer" required>

            <label>Category</label>
            <input type="text" name="category" required>

            <label>Price</label>
            <input type="number" step="0.01" name="price" required>

            <label>Stock Quantity</label>
            <input type="number" name="stockQuantity" required>

            <label>Image URL</label>
            <input type="text" name="imageUrl">

            <label>Description</label>
            <textarea name="description" rows="4"></textarea>

            <button type="submit" class="add-btn">Add Product</button>
        </form>
    </div>

    <div class="table-container">
        <h2>Current Products</h2>
        <form action="adminProduct" method="get" style="display:flex; gap:10px; flex-wrap:wrap; margin-bottom:15px;">
    <input type="text" name="search" placeholder="Search products...">

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

        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null && !products.isEmpty()) {
        %>
 <table>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Manufacturer</th>
        <th>Category</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Image URL</th>
        <th>Description</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>

    <%
        for (Product p : products) {
    %>
    <tr>
        <form action="adminProduct" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= p.getId() %>">

            <td><%= p.getId() %></td>
            <td><input class="table-input" type="text" name="title" value="<%= p.getTitle() %>" required></td>
            <td><input class="table-input" type="text" name="manufacturer" value="<%= p.getManufacturer() %>" required></td>
            <td><input class="table-input" type="text" name="category" value="<%= p.getCategory() %>" required></td>
            <td><input class="table-input" type="number" step="0.01" name="price" value="<%= p.getPrice() %>" required></td>
            <td><input class="table-input" type="number" name="stockQuantity" value="<%= p.getStockQuantity() %>" required></td>
            <td><input class="table-input" type="text" name="imageUrl" value="<%= p.getImageUrl() %>"></td>
            <td><input class="table-input" type="text" name="description" value="<%= p.getDescription() %>"></td>
            <td><button type="submit" class="edit-btn">Update</button></td>
        </form>

        <td>
            <form action="adminProduct" method="post" style="display:inline;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%= p.getId() %>">
                <button type="submit" class="delete-btn">Delete</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>
        <%
            } else {
        %>
            <p>No products found.</p>
        <%
            }
        %>
    </div>
    <div class="table-container">
    <h2>Customers</h2>

    <%
        List<Customer> customers = (List<Customer>) request.getAttribute("customers");
        if (customers != null && !customers.isEmpty()) {
    %>
        <table>
            <tr>
                <th>ID</th>
                <th>Email</th>
            </tr>

            <%
                for (Customer c : customers) {
            %>
            <tr>
                <td><%= c.getId() %></td>
                <td><%= c.getEmail() %></td>
            </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <p>No customers found.</p>
    <%
        }
    %>
</div>
<div class="table-container">
    <h2>Orders / Purchase History</h2>

    <%
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders != null && !orders.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Order ID</th>
                <th>Customer Email</th>
                <th>Order Date</th>
                <th>Shipping Address</th>
                <th>City</th>
                <th>County</th>
                <th>Payment Method</th>
                <th>Total Amount</th>
            </tr>

            <%
                for (Order o : orders) {
            %>
            <tr>
                <td><%= o.getId() %></td>
                <td><%= o.getCustomer().getEmail() %></td>
                <td><%= o.getOrderDate() %></td>
                <td><%= o.getShippingAddress() %></td>
                <td><%= o.getCity() %></td>
                <td><%= o.getCounty() %></td>
                <td><%= o.getPaymentMethod() %></td>
                <td>€<%= o.getTotalAmount() %></td>
            </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <p>No orders found.</p>
    <%
        }
    %>
</div>

</body>
</html>