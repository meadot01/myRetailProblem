package net.meadowsnet.data;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by Todd Meadows on 1/30/16.
 *
 * Since could not get AuditingEntityListener working am creating custom
 * listener.  Normally would not do this for simple case like this.
 *
 * If we had more than one DB object would create an abstract "updatable"
 * Entity - but since we don't this will be coded to work with only Product.
 */
public class TimestampedEntityAuditListener {
    @PrePersist
    public void touchForCreate(Product target) {
        target.setLast_updated(new Date());
    }

    @PreUpdate
    public void touchForUpdate(Product target) {
        target.setLast_updated(new Date());
    }
}
