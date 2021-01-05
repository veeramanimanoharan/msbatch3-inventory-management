package com.sl.ms.inventorymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sl.ms.inventorymanagement.model.Product;
import com.sl.ms.inventorymanagement.repository.ProductRepository;


@Service
public class ProductService {
	@Autowired
	ProductRepository prorepo;
	private static Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	public List<Product> getAllProduct(){
		logger.info("Into getAllProduct Services");
		List<Product> allitems=  new ArrayList<Product>();
		prorepo.findAll().forEach(itm -> allitems.add(itm));;
		logger.info("Exit getAllProduct Services");
		return allitems;
		
	}
	@Cacheable("support")
	public List<Product> supportedproducts() {
		logger.info("Into Support Services");
		System.out.println("supportedproducts");
		logger.info("Exit Support Services");
		return getAllProduct();
	}
	public void save(Product itm) {
		prorepo.save(itm);
	}
	public void savelist(List<Product> itm) {
		itm.forEach(it->prorepo.save(it));
	}
	public Product getById(int id) {
		return prorepo.findById(id).get();
	}
	public void delete(int id) {
		prorepo.deleteById(id);
	}
	public boolean check(int id) {
		logger.info("Into checkproductavail Services");
		Optional<Product> pro = prorepo.findById(id);
		if (pro.isPresent()) {
			if (pro.get().getQuantity()!=0){
				logger.info("Exit checkproductavail Services - "+ pro.get().getName()+" is found " +true);
				return true;
			}
			
		}
		logger.info("Exit checkproductavail Services - "+ false);
		return false;
	
	}
	
}
