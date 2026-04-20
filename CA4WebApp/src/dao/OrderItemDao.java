package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Customer;
import entities.OrderItem;

public class OrderItemDao {

    protected static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("jpaPU");

    public OrderItemDao() {
    }

    public void persist(OrderItem orderItem) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(orderItem);
        em.getTransaction().commit();
        em.close();
    }
    public List<OrderItem> getOrderItemsByCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();

        List<OrderItem> items = em.createQuery(
            "SELECT oi FROM OrderItem oi WHERE oi.order.customer = :customer", OrderItem.class)
            .setParameter("customer", customer)
            .getResultList();

        em.close();
        return items;
    }
}