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
    public List<Product> searchAndSortProducts(String search, String filter, String sort) {
        EntityManager em = emf.createEntityManager();

        String query = "SELECT p FROM Product p WHERE 1=1";

        if (search != null && !search.trim().isEmpty()) {
            if ("title".equals(filter)) {
                query += " AND LOWER(p.title) LIKE :search";
            } else if ("category".equals(filter)) {
                query += " AND LOWER(p.category) LIKE :search";
            } else if ("manufacturer".equals(filter)) {
                query += " AND LOWER(p.manufacturer) LIKE :search";
            }
        }

        if ("priceAsc".equals(sort)) {
            query += " ORDER BY p.price ASC";
        } else if ("priceDesc".equals(sort)) {
            query += " ORDER BY p.price DESC";
        } else if ("titleAsc".equals(sort)) {
            query += " ORDER BY p.title ASC";
        } else if ("titleDesc".equals(sort)) {
            query += " ORDER BY p.title DESC";
        } else if ("manufacturerAsc".equals(sort)) {
            query += " ORDER BY p.manufacturer ASC";
        } else if ("manufacturerDesc".equals(sort)) {
            query += " ORDER BY p.manufacturer DESC";
        }

        javax.persistence.TypedQuery<Product> q = em.createQuery(query, Product.class);

        if (search != null && !search.trim().isEmpty()) {
            q.setParameter("search", "%" + search.toLowerCase() + "%");
        }

        List<Product> products = q.getResultList();
        em.close();
        return products;
    }
    
}