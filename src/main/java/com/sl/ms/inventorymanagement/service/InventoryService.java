package com.sl.ms.inventorymanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ms.inventorymanagement.model.Inventory;

import com.sl.ms.inventorymanagement.repository.InventoryRepository;


@Service
public class InventoryService {

	@Autowired
	InventoryRepository repo;
	
	private static Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	public List<Inventory> getAllInventory(){
		logger.info("Into getAllInv Services");
		List<Inventory> allitems=  new ArrayList<Inventory>();
		repo.findAll().forEach(itm -> allitems.add(itm));;
		logger.info("Exit getAllInv Services");
		return allitems;
		
	}
	public void save(Inventory itm) {
		logger.info("Into SaveInv Services");
		repo.save(itm);
	}
	public Inventory getById(int id) {
		logger.info("Into getgetbyIDInv Services");
		return repo.findById(id).get();
	}
	public void delete(int id) {
		logger.info("Into DeleteInv Services");
		repo.deleteById(id);
	}
}
