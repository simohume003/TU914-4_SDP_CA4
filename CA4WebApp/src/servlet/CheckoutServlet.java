package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Customer;
import servlet.CheckoutService;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CheckoutService checkoutService = new CheckoutService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = (Customer) request.getSession().getAttribute("customer");

        if (customer == null) {
            response.sendRedirect("auth");
            return;
        }

        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = (Customer) request.getSession().getAttribute("customer");

        if (customer == null) {
            response.sendRedirect("auth");
            return;
        }

        String shippingAddress = request.getParameter("shippingAddress");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String paymentMethod = request.getParameter("paymentMethod");

        checkoutService.checkout(customer, shippingAddress, city, county, paymentMethod);

        response.sendRedirect("products");
    }
}