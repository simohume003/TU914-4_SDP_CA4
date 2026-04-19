package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    private int rating;
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewDate;

    public Review() {}

    public Review(Customer customer, Product product, int rating, String comment, Date reviewDate) {
        this.customer = customer;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }
}