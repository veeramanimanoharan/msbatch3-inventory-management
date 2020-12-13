package com.sl.ms.inventorymanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sl.ms.inventorymanagement.model.Inventory;



@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Integer>{

}
