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
import java.util.Arrays;
import java.util.List;

/**
 * Created by todd on 1/29/16.
 */
@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

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
        //return product;
    }

    @RequestMapping(value = "/category/{categoryName}/products", method = RequestMethod.GET)
    public Iterable<Product> getProductsByCategory(@PathVariable String categoryName) {
        return productRepository.findProductByCategory(categoryName);
    }


}
