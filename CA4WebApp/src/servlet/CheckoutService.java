package servlet;

import java.util.Date;
import java.util.List;

import dao.CartItemDao;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.ProductDao;
import entities.CartItem;
import entities.Customer;
import entities.Order;
import entities.OrderItem;
import entities.Product;

public class CheckoutService {

    private CartItemDao cartDao = new CartItemDao();
    private OrderDao orderDao = new OrderDao();
    private OrderItemDao orderItemDao = new OrderItemDao();
    private ProductDao productDao = new ProductDao();

    public void checkout(Customer customer, String shippingAddress, String city,
                         String county, String paymentMethod) {

        List<CartItem> cartItems = cartDao.getCartItemsByCustomer(customer);

        if (cartItems == null || cartItems.isEmpty()) {
            return;
        }

        double totalAmount = 0.0;

        for (CartItem item : cartItems) {
            totalAmount += item.getProduct().getPrice() * item.getQuantity();
        }

        Order order = new Order(customer, shippingAddress, city, county,
                paymentMethod, totalAmount, new Date());

        orderDao.persist(order);

        for (CartItem item : cartItems) {
            Product product = item.getProduct();

            OrderItem orderItem = new OrderItem(
                    order,
                    product,
                    item.getQuantity(),
                    product.getPrice()
            );
            orderItemDao.persist(orderItem);

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productDao.merge(product);
        }

        cartDao.clearCartByCustomer(customer);
    }
}