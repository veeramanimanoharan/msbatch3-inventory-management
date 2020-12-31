package com.sl.ms.inventorymanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sl.ms.inventorymanagement.model.Product;
import com.sl.ms.inventorymanagement.repository.ProductRepository;
import com.sl.ms.inventorymanagement.service.ProductService;




@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class InventoryTestServices {
	
	@Autowired
	private ProductService proSer;
	
	@MockBean
	private ProductRepository proRepo;

	Product mockProd;
	Product mockProd2;
	List<Product> mockProds;
	
	@BeforeEach
	void OrderManagementApplicationTests11() throws JsonMappingException, JsonProcessingException{
		mockProd = new Product();
		mockProd.setId(1);
		mockProd.setName("Product");
		mockProd.setPrice((double) 10);
		mockProd.setQuantity(3);

		mockProd2 = new Product();
		mockProd2.setId(2);
		mockProd2.setName("Product2");
		mockProd2.setPrice((double) 10);
		mockProd2.setQuantity(3);

		mockProds = new ArrayList<Product>();
		mockProds.add(mockProd);
		mockProds.add(mockProd2);
	}
	
	@Test
	@DisplayName("Product by ID")	
	public void testPriobyIdService() {
		

		when(proRepo.findById(mockProd.getId())).thenReturn(Optional.of(mockProd));
		
		Product ordfound = proSer.getById(mockProd.getId());
		
		assertNotNull(ordfound);
		assertEquals(ordfound.getId(), mockProd.getId());
		
	}
	
	
	
	@Test
	@DisplayName("getAllProduct")	
	public void testProService() {
				
		doReturn(mockProds).when(proRepo).findAll();

		List<Product> prods = proSer.getAllProduct();
		
		assertNotNull(prods);
		assertEquals(prods.get(0).getId(), mockProd.getId());
	}
	
	@Test
	@DisplayName("Pro Save")	
	public void testOrdersSaveService() {
		
		doReturn(mockProd).when(proRepo).save(mockProd);
		
		proSer.save(mockProd);

//		
	}
	

	@Test
	@DisplayName("Orders Delete")	
	public void testOrdersDeleteService() {
		
		
		proSer.delete(mockProd.getId());
		

//		
	}
	
}
