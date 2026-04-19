package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;

import entities.Customer;

public class CustomerDao {

    protected static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("jpaPU");

    public CustomerDao() {
    }

    public void persist(Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
        em.close();
    }

    public Customer getCustomerByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        List<Customer> customers = em.createQuery(
                "SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                .setParameter("email", email)
                .getResultList();
        em.close();

        if (customers.isEmpty()) {
            return null;
        }
        return customers.get(0);
    }

    public Customer getCustomerByEmailAndPassword(String email, String password) {
        EntityManager em = emf.createEntityManager();
        List<Customer> customers = em.createQuery(
                "SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password", Customer.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
        em.close();

        if (customers.isEmpty()) {
            return null;
        }
        return customers.get(0);
    }
    public List<Customer> getAllCustomers() {
        EntityManager em = emf.createEntityManager();
        List<Customer> customers = em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
        em.close();
        return customers;
    }
}