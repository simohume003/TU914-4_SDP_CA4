package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.OrderItemDao;
import dao.ProductDao;
import dao.ReviewDao;
import entities.*;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private OrderItemDao orderItemDao = new OrderItemDao();
    private ProductDao productDao = new ProductDao();
    private ReviewDao reviewDao = new ReviewDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = (Customer) request.getSession().getAttribute("customer");

        if (customer == null) {
            response.sendRedirect("auth");
            return;
        }

        List<OrderItem> items = orderItemDao.getOrderItemsByCustomer(customer);
        request.setAttribute("orderItems", items);

        request.getRequestDispatcher("/review.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = (Customer) request.getSession().getAttribute("customer");

        int productId = Integer.parseInt(request.getParameter("productId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        Product product = productDao.getProductById(productId);

        Review review = new Review(customer, product, rating, comment, new Date());

        reviewDao.persist(review);

        response.sendRedirect("review");
    }
}

