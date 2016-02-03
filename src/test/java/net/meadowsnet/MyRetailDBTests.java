package net.meadowsnet;

import com.google.common.collect.Iterables;

import net.meadowsnet.data.Product;
import net.meadowsnet.data.ProductRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyRetailApplication.class)

public class MyRetailDBTests {

	@Autowired
	ProductRepository productRepository;

	Product product1, product2, product3;

	@Before
	public void setup() {

		product1 = new Product(5555, "AEX143", "Stroller", "baby");
		product2 = new Product(5543,"IOL123", "Optimus Prime", "toys");
		product3 = new Product(7563,"XYZ904", "Sega Genesis", "toys");
		productRepository.deleteAll();
		productRepository.save(Arrays.asList(product1, product2, product3));
	}

	@Test
	public void saveProduct() {
		Product product4 = new Product(8888, "ZZZ123", "Pokemon Cards", "toys");
		productRepository.save(product4);
		assertNotNull(product4.getId());
		Product fetchedProduct = productRepository.findOne(product4.getId());
		assertEquals(product4, fetchedProduct);
		// Should have the last updated property set automatically
		assertNotNull(fetchedProduct.getLast_updated());
	}

	@Test
	public void getAll() {
		Iterable<Product> searchResults = productRepository.findAll();
		assertNotNull(searchResults);
		int count= 0;
		assertEquals(3, Iterables.size(searchResults));
	}

	@Test
	public void getProduct() {
		List<Product> searchResults = productRepository.findProductBySku(product2.getSku());
		assertNotNull(searchResults);
		assertEquals(1, searchResults.size());
		assertEquals(product2, searchResults.get(0));
	}

	@Test
	public void getBySku() {
		List<Product> searchResults = productRepository.findProductBySku(product3.getSku());
		assertNotNull(searchResults);
		assertEquals(1, searchResults.size());
		assertEquals(product3, searchResults.get(0));
	}

	@Test
	public void getByCategory() {
		List<Product> searchResults = productRepository.findProductByCategory("toys");
		assertNotNull(searchResults);
		assertEquals(2, searchResults.size());
	}

}