package net.meadowsnet.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * The following is a rest controller for Product.  Only read operations
 * are needed.
 *
 * Created by Todd Meadows on 1/29/16.
 */
@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    /*
    Request mapping to get all products.
    Example: /products will return all products.
    You can also pass a parameter of ids containing a comma separated list
    of ids to return specific products.
    Example: /products?ids=5555,5556 will return only products with id 5555 and 5556
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public Iterable<Product> getProducts(@RequestParam(value="ids", required=false) String ids) {
        if (!StringUtils.isEmpty(ids)) {
            List<Long> idList = new ArrayList<Long>();
            for (String s : ids.split(",")) {
                idList.add(Long.parseLong(s));
            }
            return productRepository.findAll(idList);

        }
        return productRepository.findAll();
    }


    /*
    Request mapping to return a product for a specific product id.
    Example: /product/5555 will return product with id 5555.
    If the product does not exist a status of 404 will be returned.
     */
    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        Product product = null;
        if (productId != null) {
            product = productRepository.findOne(productId);
        }
        if (product != null) {
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    Request mapping to retrieve all products for a specified category name.
    Example : /category/toys/products will return all products with a
    toys category
     */
    @RequestMapping(value = "/category/{categoryName}/products", method = RequestMethod.GET)
    public Iterable<Product> getProductsByCategory(@PathVariable String categoryName) {
        return productRepository.findProductByCategory(categoryName);
    }


}
