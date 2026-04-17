package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CartItemDao;
import dao.ProductDao;
import entities.CartItem;
import entities.Customer;
import entities.Product;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CartItemDao cartDao = new CartItemDao();
    private ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = (Customer) request.getSession().getAttribute("customer");//get customer from session

        if (customer == null) {
            response.sendRedirect("auth");
            return;
        }

        List<CartItem> cartItems = cartDao.getCartItemsByCustomer(customer);
        request.setAttribute("cartItems", cartItems);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = (Customer) request.getSession().getAttribute("customer");

        if (customer == null) {
            response.sendRedirect("auth");
            return;
        }

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = productDao.getProductById(productId);

            CartItem existingItem = cartDao.getCartItemByCustomerAndProduct(customer, product);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + 1);
                cartDao.merge(existingItem);
            } else {
                CartItem newItem = new CartItem(customer, product, 1);
                cartDao.persist(newItem);
            }

            response.sendRedirect("products");
            return;
        }

        if ("update".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            CartItem cartItem = cartDao.getCartItemById(cartItemId);
            if (cartItem != null && quantity > 0) {
                cartItem.setQuantity(quantity);
                cartDao.merge(cartItem);
            }

            response.sendRedirect("cart");
            return;
        }

        if ("delete".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));

            CartItem cartItem = cartDao.getCartItemById(cartItemId);
            if (cartItem != null) {
                cartDao.remove(cartItem);
            }

            response.sendRedirect("cart");
        }
    }
}
