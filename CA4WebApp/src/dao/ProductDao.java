package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Product;

public class ProductDao {

    protected static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("jpaPU");

    public ProductDao() {
    }

    public void persist(Product product) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
        em.close();
    }

    public void remove(Product product) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(product));
        em.getTransaction().commit();
        em.close();
    }

    public Product merge(Product product) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product updatedProduct = em.merge(product);
        em.getTransaction().commit();
        em.close();
        return updatedProduct;
    }

    public List<Product> getAllProducts() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Product> productsFromDB = new ArrayList<Product>();
        productsFromDB = em.createNamedQuery("Product.findAll", Product.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return productsFromDB;
    }
    public Product getProductById(int id) {
        EntityManager em = emf.createEntityManager();
        Product product = em.find(Product.class, id);
        em.close();
        return product;
    }
}