package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Order;

public class OrderDao {

    protected static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("jpaPU");

    public OrderDao() {
    }

    public void persist(Order order) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();
        em.close();
    }

    public Order merge(Order order) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Order updatedOrder = em.merge(order);
        em.getTransaction().commit();
        em.close();
        return updatedOrder;
    }

    public Order getOrderById(int id) {
        EntityManager em = emf.createEntityManager();
        Order order = em.find(Order.class, id);
        em.close();
        return order;
    }
}