package com.sl.ms.inventorymanagement;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sl.ms.inventorymanagement.model.Product;
import com.sl.ms.inventorymanagement.service.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class InventoryTestsController {

	@Test
	void contextLoads() {
	}

	@MockBean
	private ProductService proSer;

	@Autowired
	private MockMvc mockMvc;

	Product mockProd;
	Product mockProd2;
	List<Product> mockProds;
	
	@Autowired
	private WebApplicationContext webApplicationContext;


	@BeforeEach
	void OrderTestsContoller() {

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
	@DisplayName("product by ID")
	public void testProductbyId () throws Exception {

		doReturn(mockProd).when(proSer).getById(mockProd.getId());

		mockMvc.perform(MockMvcRequestBuilders.get("/products/{product_id}",mockProd.getId()))		 
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(mockProd.getId()))
//				.andDo(print())
		;		
	}

	@Test
	@DisplayName("product all")
	public void testProductAllGet () throws Exception {

		doReturn(mockProds).when(proSer).getAllProduct();

		mockMvc.perform(MockMvcRequestBuilders.get("/products"))		 
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value(mockProd.getId()))
		//				 .andDo(print())
		;		
	}


	@Test
	@DisplayName("Post /products/{product_id}")
	public void testPostproduct_id() throws JsonProcessingException, Exception {
		 mockMvc.perform(MockMvcRequestBuilders.post("/products/"+mockProd.getId())
				 .contentType(MediaType.APPLICATION_JSON_VALUE)
				 .content(new ObjectMapper().writeValueAsString(mockProd)))
//		 .andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(mockProd.getId()))
		;
	}

	@Test
	@DisplayName("Post /products")
	public void testPostproducts() throws JsonProcessingException, Exception {
		 mockMvc.perform(MockMvcRequestBuilders.post("/products")
				 .contentType(MediaType.APPLICATION_JSON_VALUE)
				 .content(new ObjectMapper().writeValueAsString(mockProds)))
//		 .andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value(mockProd.getId()))
		;

	}
	@Test
	@DisplayName("@DeleteMapping(/products/{product_id}")
	public void testDeleteMapping() throws Exception {
		
		 mockMvc.perform(MockMvcRequestBuilders.delete("/products/{product_id}",mockProd.getId()))
			.andExpect(status().isOk());

	}
	@Test
	@DisplayName("@PutMapping(/products/{product_id}")
	public void testPutMapping() throws JsonProcessingException, Exception {
		doReturn(mockProd).when(proSer).getById(mockProd.getId());
				
		 mockMvc.perform(MockMvcRequestBuilders.put("/products/"+mockProd.getId())
				 .contentType(MediaType.APPLICATION_JSON_VALUE)
				 .content(new ObjectMapper().writeValueAsString(mockProd2)))
//		 .andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value(mockProd2.getName()))
		;

	}
	@Test
	@DisplayName("@GetMapping(/checkproductavail/{product_id}")
	public void testGetMappingcheckproductavail() throws JsonProcessingException, Exception {
		doReturn(true).when(proSer).check(mockProd.getId());
		
		 mockMvc.perform(MockMvcRequestBuilders.get("/checkproductavail/{product_id}",mockProd.getId()))
//		 .andDo(print())
		.andExpect(status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString()
		.compareTo("true")
//		.andExpect( content().string("true"))
		;

	}
	
	@Test
	@DisplayName("False @GetMapping(/checkproductavail/{product_id}")
	public void testGetMappingcheckproductavailFalse() throws JsonProcessingException, Exception {
		doReturn(false).when(proSer).check(mockProd.getId());
		
		 mockMvc.perform(MockMvcRequestBuilders.get("/checkproductavail/{product_id}",mockProd.getId()))
//		 .andDo(print())
		.andExpect(status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString()
		.compareTo("false")
//		.andExpect( content().string("true"))
		;

	}
	
	@Test
	@DisplayName("@GetMapping(/inv")
	public void testetMappinginv() {
		

	}
	@Test
	@DisplayName("@PostMapping(/products/file")
	public void testPostMappingproductsfile() throws Exception {
		
		   MockMultipartFile file 
		      = new MockMultipartFile(
		        "file", 
		        "Samp_inv.csv", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        "1,Item1,1,5\r\n2,Item2,2,6".getBytes()
		      );
		   
		   System.out.println(file.toString());

		    MockMvc mockMvc 
		      = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		    mockMvc.perform(multipart("/products/file").file(file))
//		    .andDo(print())
		      .andExpect(status().isOk())
		      .andExpect(content().string("File Uploaded sucessfully"))
//		      .andExpect(content().string("Veera"))
		      ;

	}
	@Test
	@DisplayName("@PostMapping(/upload/csv")
	public void testPostMappinguploadcsv() throws Exception {
		

		   MockMultipartFile file 
		      = new MockMultipartFile(
		        "file", 
		        "Samp_inv.csv", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        "1,Item1,1,5\r\n2,Item2,2,6".getBytes()
		      );
		   
		   System.out.println(file.toString());

		    MockMvc mockMvc 
		      = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		    mockMvc.perform(multipart("/upload/csv").file(file))
//		    .andDo(print())
		      .andExpect(status().isOk())
		      .andExpect(content().string("File Uploaded sucessfully"))
//		      .andExpect(content().string("Veera"))
		      ;

	}


}
