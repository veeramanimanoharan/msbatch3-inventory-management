package com.sl.ms.inventorymanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.sl.ms.inventorymanagement.model.Product;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{

}
