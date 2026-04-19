package dao;

import java.util.List;
import javax.persistence.*;

import entities.Product;
import entities.Review;

public class ReviewDao {

    protected static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("jpaPU");

    public void persist(Review review) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(review);
        em.getTransaction().commit();
        em.close();
    }

    public List<Review> getReviewsByProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        List<Review> reviews = em.createQuery(
                "SELECT r FROM Review r WHERE r.product = :product", Review.class)
                .setParameter("product", product)
                .getResultList();
        em.close();
        return reviews;
    }
}