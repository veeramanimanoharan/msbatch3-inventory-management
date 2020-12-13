package com.sl.ms.inventorymanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ms.inventorymanagement.model.Product;




@RestController
public class ProductController {
	/*
	 * GET /products Fetch the list of product inventory in system.
		GET /products/{product_id} Fetch the specific details of product details by passing product_id
		POST /products/{product_id} Insert a new inventory for s specific product via Rest end point.
		POST /products Post data for more than one product at a time.
		POST /products/file Post data for inventory update as a file. (simple csv)
		PUT /products/{product_id} Update specific product inventory
		DELETE /products/{product_id} Delete a specific product from system. (Soft delete, only make product quantity as 0)
		GET /supported products Fetch the unique list of products supported by system. Return product_id, product_name
	 */
	
	
	@GetMapping("/products")
	private List<Product> getAllProduct(){
		return null;
		
	}
	@GetMapping("/products/{product_id}")
	private Product getproduct(@PathVariable("product_id") int id) {

		return null;
	}
	@PostMapping("/products/{product_id}")
	private Product saveproduct(@RequestBody Product product) {
//		prodservice.save(product);
		return product;

	}
	@PostMapping("/products")
	private List<Product> saveproducts(@RequestBody List<Product> products) {
//		prodservice.save(product);
		return products;

	}
	
	@DeleteMapping("/products/{product_id}")
	private Product deleteproduct(@PathVariable("product_id") int id) {
//		Product tt = Productservice.getById(id);
//		Productservice.delete(id);
		return null;
	}

}
