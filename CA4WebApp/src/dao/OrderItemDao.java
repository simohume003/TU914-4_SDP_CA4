package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
}