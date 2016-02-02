package net.meadowsnet;

import net.meadowsnet.data.Product;
import net.meadowsnet.data.ProductRepository;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyRetailApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8989")

public class MyRetailRestTests {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ApplicationContext ctx;

    Product product1, product2, product3;

    private RestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setup() {
        product1 = new Product(5555, "AEX143", "Stroller", "baby");
        product2 = new Product(5543,"IOL123", "Optimus Prime", "toys");
        product3 = new Product(7563,"XYZ904", "Sega Genesis", "toys");
        productRepository.deleteAll();
        productRepository.save(Arrays.asList(product1, product2, product3));
    }

    //Testing endpoint /products
    @Test
    public void getAllProducts() {
        Product[] expectedResults = new Product[] {product1, product2, product3};
        ResponseEntity<Product[]> productResponse = restTemplate.getForEntity("http://localhost:8989/products", Product[].class);
        assertThat(productResponse.getStatusCode(), equalTo(HttpStatus.OK));
        Product[] returnedProducts = productResponse.getBody();
        assertTrue(compareArrays(expectedResults, returnedProducts));
    }

    //Testing endpoint /product/{productId}
    @Test
    public void getProductById() {
        ResponseEntity<Product> productResponse = restTemplate.getForEntity("http://localhost:8989/product/" + product2.getId(), Product.class);
        assertThat(productResponse.getStatusCode(), equalTo(HttpStatus.OK));
        Product product = productResponse.getBody();
        assertEquals(product2, product);
    }


    // Bonus endpoint to get multiple products by id
    // Testing endpoint /products?ids=
    @Test
    public void getMultipleProductsById() {
        Product[] expectedResults = new Product[] {product1, product3};
        ResponseEntity<Product[]> productResponse = restTemplate.getForEntity("http://localhost:8989/products?ids=" + expectedResults[0].getId() + "," + expectedResults[1].getId(), Product[].class);
        assertThat(productResponse.getStatusCode(), equalTo(HttpStatus.OK));
        Product[] returnedProducts = productResponse.getBody();
        assertTrue(compareArrays(expectedResults, returnedProducts));
    }

    // Bonus endpoint to get products by category id
    // Testing endpoint /category/{category}/products
    @Test
    public void getProductsByCategory() {
        Product[] expectedResults = new Product[] {product2, product3};
        ResponseEntity<Product[]> productResponse = restTemplate.getForEntity("http://localhost:8989/category/toys/products", Product[].class);
        assertThat(productResponse.getStatusCode(), equalTo(HttpStatus.OK));
        Product[] returnedProducts = productResponse.getBody();
        assertTrue(compareArrays(expectedResults, returnedProducts));
    }

    // Does an order insensitive compare of two Product Arrays
    private boolean compareArrays(Product[] products1, Product[] products2) {
        List<Product> productArray1 = new LinkedList<Product>(Arrays.asList(products1));
        productArray1.removeAll(Arrays.asList(products2));
        return (0 ==  productArray1.size());
    }

}


