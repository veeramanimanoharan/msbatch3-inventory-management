package com.sl.ms.inventorymanagement.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVReader;
import com.sl.ms.inventorymanagement.model.Product;
import com.sl.ms.inventorymanagement.service.InventoryService;
import com.sl.ms.inventorymanagement.service.ProductService;




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
	@Autowired
	InventoryService invService;
	@Autowired
	ProductService proService;
	
	@GetMapping("/products")
	private List<Product> getAllProduct(){
		return proService.getAllProduct();
		
	}
	@GetMapping("/products/{product_id}")
	private Product getproduct(@PathVariable("product_id") int id) {

		return proService.getById(id);
	}
	@PostMapping("/products/{product_id}")
	private Product saveproduct(@PathVariable("product_id") int id, @RequestBody Product product) {
		product.setId(id);
		proService.save(product);
		return product;

	}
//	@PostMapping("/product")
//	private Product saveproduct(@RequestBody Product product) {
//		proService.save(product);
//		return product;
//
//	}
	@PostMapping("/products")
	private List<Product> saveproducts(@RequestBody List<Product> products) {
		proService.savelist(products);
		return products;

	}
	
	@DeleteMapping("/products/{product_id}")
	private Product deleteproduct(@PathVariable("product_id") int id) {
		Product tt = proService.getById(id);
		proService.delete(id);
		return tt;
	}
	@PutMapping("/products/{product_id}")
	private Product updateproduct(@PathVariable("product_id") int id, @RequestBody Product product1) {
		Product product = proService.getById(id);
		product.setName(product1.getName());
		product.setPrice(product1.getPrice());
		product.setQuantity(product1.getQuantity());
		proService.save(product);
		return product;
		
	}
	@GetMapping("/checkproductavail/{product_id}")
	private boolean checkProductAvail(@PathVariable("product_id") int id){
		return proService.check(id);
		
	}
	//****************************************************************************


	@GetMapping("/upload/csv")
	private void ttt() throws IOException 
	{
	
	readcsv();
	}
	private  void readcsv() throws  IOException {
		System.out.println("Veera1");
		
	
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader("C:\\samp_inv.csv"), ',');
		

		List<Product> prods = new ArrayList<Product>();

		// read line by line
		String[] record = null;
		record = reader.readNext();

		while ((record = reader.readNext()) != null) {
			Product pro = new Product();
			pro.setId(Integer.parseInt(record[0]));
			pro.setName(record[1]);
			pro.setPrice(Double.parseDouble(record[2]));
			pro.setQuantity(Integer.parseInt(record[3]));
			prods.add(pro);
//			System.out.println(pro);
//			proService.save(pro);
			
		}

		System.out.println(prods);
		proService.savelist(prods);
		
		
		reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		

}
