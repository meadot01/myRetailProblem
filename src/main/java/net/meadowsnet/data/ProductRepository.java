package net.meadowsnet.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Todd Meadows on 1/19/16.
 */
//@RepositoryRestResource(collectionResourceRel = "products", path="products")
public interface ProductRepository extends CrudRepository<Product, Long> {

    // CrudRepository gives us most crud operations for "free" but defining
    // a few custom find methods as well.
    List<Product> findProductBySku(@Param("sku") String sku);
    List<Product> findProductByCategory(@Param("category") String category);

}
