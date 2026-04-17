package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.CartItem;
import entities.Customer;
import entities.Product;

public class CartItemDao {

    protected static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("jpaPU");

    public CartItemDao() {
    }

    public void persist(CartItem cartItem) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(cartItem);
        em.getTransaction().commit();
        em.close();
    }

    public CartItem merge(CartItem cartItem) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CartItem updatedCartItem = em.merge(cartItem);
        em.getTransaction().commit();
        em.close();
        return updatedCartItem;
    }

    public void remove(CartItem cartItem) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(cartItem));
        em.getTransaction().commit();
        em.close();
    }

    public CartItem getCartItemById(int id) {
        EntityManager em = emf.createEntityManager();
        CartItem cartItem = em.find(CartItem.class, id);
        em.close();
        return cartItem;
    }

    public List<CartItem> getCartItemsByCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        List<CartItem> cartItems = em.createQuery(
                "SELECT c FROM CartItem c WHERE c.customer = :customer", CartItem.class)
                .setParameter("customer", customer)
                .getResultList();
        em.close();
        return cartItems;
    }
    
    public void clearCartByCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM CartItem c WHERE c.customer = :customer")
          .setParameter("customer", customer)
          .executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public CartItem getCartItemByCustomerAndProduct(Customer customer, Product product) {
        EntityManager em = emf.createEntityManager();
        List<CartItem> cartItems = em.createQuery(
                "SELECT c FROM CartItem c WHERE c.customer = :customer AND c.product = :product", CartItem.class)
                .setParameter("customer", customer)
                .setParameter("product", product)
                .getResultList();
        em.close();

        if (cartItems.isEmpty()) {
            return null;
        }
        return cartItems.get(0);
    }
}