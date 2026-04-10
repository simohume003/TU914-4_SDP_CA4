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

@WebServlet("/adminProduct")
public class AdminProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDao dao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = dao.getAllProducts();
        request.setAttribute("products", products);
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