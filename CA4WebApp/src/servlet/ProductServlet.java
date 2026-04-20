package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import dao.ReviewDao;
import entities.Review;

import dao.ProductDao;
import entities.Product;



@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReviewDao reviewDao = new ReviewDao();
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDao dao = new ProductDao();
        String search = request.getParameter("search");
        String filter = request.getParameter("filter");
        String sort = request.getParameter("sort");
        
        List<Product> products = dao.getAllProducts();
        if ((search != null && !search.trim().isEmpty()) || (sort != null && !sort.trim().isEmpty())) {

            products = dao.searchAndSortProducts(search, filter, sort);

        } else {

            products = dao.getAllProducts();

        }
        Map<Integer, List<Review>> reviewsMap = new HashMap<>();
        
    for (Product p : products) {
        List<Review> reviews = reviewDao.getReviewsByProduct(p);
        reviewsMap.put(p.getId(), reviews);
    }
    request.setAttribute("products", products);
    request.setAttribute("reviewsMap", reviewsMap);
    request.getRequestDispatcher("/home.jsp").forward(request, response);
    
}
}
