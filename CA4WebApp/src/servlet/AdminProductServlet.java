package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDao;
import entities.Product;
import dao.CustomerDao;
import dao.OrderDao;
import entities.Customer;
import entities.Order;

@WebServlet("/adminProduct")
public class AdminProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDao dao = new ProductDao();
    private CustomerDao customerDao = new CustomerDao();
    private OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String search = request.getParameter("search");

        String filter = request.getParameter("filter");

        String sort = request.getParameter("sort");

        List<Product> products;

        if ((search != null && !search.trim().isEmpty()) || (sort != null && !sort.trim().isEmpty())) {

            products = dao.searchAndSortProducts(search, filter, sort);

        } else {

            products = dao.getAllProducts();
        }
        List<Customer> customers = customerDao.getAllCustomers();
        List<Order> orders = orderDao.getAllOrders();
        request.setAttribute("products", products);
        request.setAttribute("customers", customers);
        request.setAttribute("orders", orders);

        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String title = request.getParameter("title");
            String manufacturer = request.getParameter("manufacturer");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));
            int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
            String imageUrl = request.getParameter("imageUrl");
            String description = request.getParameter("description");

            Product product = new Product(title, manufacturer, category, price, stockQuantity, imageUrl, description);
            dao.persist(product);
        }
            else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String title = request.getParameter("title");
                String manufacturer = request.getParameter("manufacturer");
                String category = request.getParameter("category");
                double price = Double.parseDouble(request.getParameter("price"));
                int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
                String imageUrl = request.getParameter("imageUrl");
                String description = request.getParameter("description");

                Product product = dao.getProductById(id);

                if (product != null) {
                    product.setTitle(title);
                    product.setManufacturer(manufacturer);
                    product.setCategory(category);
                    product.setPrice(price);
                    product.setStockQuantity(stockQuantity);
                    product.setImageUrl(imageUrl);
                    product.setDescription(description);

                    dao.merge(product);
                }
           
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = dao.getProductById(id);
            if (product != null) {
                dao.remove(product);
            }
        }

        response.sendRedirect("adminProduct");
      
    }
}