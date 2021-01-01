package com.sl.ms.inventorymanagement.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVReader;
import com.sl.ms.inventorymanagement.model.Inventory;
import com.sl.ms.inventorymanagement.model.Product;
import com.sl.ms.inventorymanagement.service.InventoryService;
import com.sl.ms.inventorymanagement.service.ProductService;

import brave.sampler.Sampler;




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
	
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	
//	@Autowired
//	public Sampler defaultSampler() {
//		return Sampler.ALWAYS_SAMPLE;
//	}
	
	@GetMapping("/products")
	private List<Product> getAllProduct(){
		 logger.info("Into getAllProduct Controller");
		return proService.getAllProduct();
		
	}
	@GetMapping("/products/{product_id}")
	private Product getproduct(@PathVariable("product_id") int id) {

		return proService.getById(id);
	}
	@GetMapping("/supportedproducts")
	private List<Product> supportedproducts(){
		return proService.supportedproducts();
		
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
		logger.info("Into checkproductavail Controller");
		return proService.check(id);
		
	}
	//****************************************************************************

	@GetMapping("/inv")
	private List<Inventory> getInvList()
	{
//		Inventory inv = new Inventory();
//		inv.setDate(LocalDateTime.now());
//		inv.setSample("sample");
//		invService.save(inv);
		List<Inventory> invlist;
		invlist=invService.getAllInventory();
		return invlist;
	}
	
	
	@PostMapping("/products/file")
	private String uploadMultiFile(@RequestParam("file") MultipartFile[] files) throws IOException  
	{
//	System.out.println(files.length+ "    ");
	
	for (int i = 0; i < files.length; i++) {
		MultipartFile file = files[i];

		Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());

//		System.out.println(filePath);
		file.transferTo(filePath);
		readcsv(filePath.toString());
		File filed = new File(filePath.toString()); 
		filed.delete();
	}
	
			return "File Uploaded sucessfully";
	
	}
	

	@PostMapping("/upload/csv")
	private String uploadSingleFile(@RequestParam("file") MultipartFile file) throws IOException 
	{

		Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());

//		System.out.println(filePath);
		file.transferTo(filePath);
//		System.out.println(filePath.toString());
		readcsv(filePath.toString());

		return "File Uploaded sucessfully";
	
	}
	private  void readcsv(String csvfile) throws  IOException {

		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(csvfile), ',');
		

		List<Product> prods = new ArrayList<Product>();

		// read line by line
		String[] record = null;
		record = reader.readNext();
//
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

//		System.out.println(prods);
		proService.savelist(prods);
		
		ObjectMapper objectMapper = new ObjectMapper();
        //Set pretty printing of json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String arrayToJson = objectMapper.writeValueAsString(prods);
//        System.out.println("1. Convert List of person objects to JSON :");
//        System.out.println(arrayToJson);
        
        
		Inventory inv = new Inventory();
		inv.setDate(LocalDateTime.now());
		inv.setSample(arrayToJson);
		invService.save(inv);
		
		
		reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		

}
