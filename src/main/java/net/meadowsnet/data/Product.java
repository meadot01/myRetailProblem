package net.meadowsnet.data;

import net.meadowsnet.TimestampedEntityAuditListener;

import org.springframework.data.annotation.LastModifiedDate;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by Todd Meadows on 1/19/16.
 */
@Entity
//@EntityListeners({AuditingEntityListener.class})
// For some reason AuditingEntityListener is not working - using custom
// listener to automatically set last_updated field.
@EntityListeners({TimestampedEntityAuditListener.class})
public class Product {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String sku;
    private String name;
    private String category;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_updated;

    public Product() {
    }

    public Product(long id, String sku, String name, String category) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.sku = sku;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (!sku.equals(product.sku)) return false;
        if (!name.equals(product.name)) return false;
        return category.equals(product.category);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + sku.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }
}