package com.sl.ms.inventorymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ms.inventorymanagement.model.Product;
import com.sl.ms.inventorymanagement.repository.ProductRepository;


@Service
public class ProductService {
	@Autowired
	ProductRepository prorepo;
	public List<Product> getAllProduct(){
		List<Product> allitems=  new ArrayList<Product>();
		prorepo.findAll().forEach(itm -> allitems.add(itm));;
		
		return allitems;
		
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
		Optional<Product> pro = prorepo.findById(id);
		if (pro.isPresent()) {
			return true;
		}
		return false;
	
	}
	
}
