package com.sl.ms.inventorymanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ms.inventorymanagement.model.Inventory;

import com.sl.ms.inventorymanagement.repository.InventoryRepository;


@Service
public class InventoryService {

	@Autowired
	InventoryRepository repo;
	public List<Inventory> getAllInventory(){
		List<Inventory> allitems=  new ArrayList<Inventory>();
		repo.findAll().forEach(itm -> allitems.add(itm));;
		
		return allitems;
		
	}
	public void save(Inventory itm) {
		repo.save(itm);
	}
	public Inventory getById(int id) {
		return repo.findById(id).get();
	}
	public void delete(int id) {
		repo.deleteById(id);
	}
}
