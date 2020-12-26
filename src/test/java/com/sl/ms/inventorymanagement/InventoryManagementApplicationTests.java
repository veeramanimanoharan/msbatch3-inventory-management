package com.sl.ms.inventorymanagement;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sl.ms.inventorymanagement.model.Product;
import com.sl.ms.inventorymanagement.service.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class InventoryManagementApplicationTests {

	@Test
	void contextLoads() {
	}

	@MockBean
	private ProductService proSer;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("product by ID")
	public void testProductbyId () throws Exception {
		
		Product mockProd = new Product();
		mockProd.setId(1);
		mockProd.setName("Product");
		mockProd.setPrice((double) 10);
		mockProd.setQuantity(3);
		
		doReturn(mockProd).when(proSer).getById(mockProd.getId());
		
		 mockMvc.perform(MockMvcRequestBuilders.get("/products/{product_id}",1))		 
				 .andExpect(status().isOk())
				 .andExpect(jsonPath("$.id").value(1))
				 .andDo(print())
		 	;
		
		
	}
	
}
