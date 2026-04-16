package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import entities.Customer;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerDao dao = new CustomerDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/auth.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if ("signup".equals(action)) {
            Customer existingCustomer = dao.getCustomerByEmail(email);

            if (existingCustomer != null) {
                request.setAttribute("message", "Email already exists. Try logging in.");
                request.getRequestDispatcher("/auth.jsp").forward(request, response);
                return;
            }

            Customer newCustomer = new Customer(email, password);
            dao.persist(newCustomer);

            request.getSession().setAttribute("customer", newCustomer);
            response.sendRedirect("products");
            return;
        

        } else if ("login".equals(action)) {
                Customer customer = dao.getCustomerByEmailAndPassword(email, password);

                if (customer != null) {
                    request.getSession().setAttribute("customer", customer);
                    response.sendRedirect("products");
                    return;
                } else {
                    request.setAttribute("message", "Invalid email or password.");
                    request.getRequestDispatcher("/auth.jsp").forward(request, response);
                    return;
                }
            }
    }
}
