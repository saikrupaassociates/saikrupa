package com.saikrupa.app.ui.test;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saikrupa.app.dao.ProductDAO;
import com.saikrupa.app.dao.impl.DefaultProductDAO;
import com.saikrupa.app.dto.ProductData;

public class TestJson {

	public TestJson() {
		try {
			ProductDAO s = new DefaultProductDAO();
			List<ProductData> products = s.findAllProducts();
			run(products.get(0));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void run(ProductData p) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File("D:\\product.json"), p);
		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(p);
		System.out.println(jsonInString);
	}

	public static void main(String[] args) {
		TestJson j = new TestJson();

	}

}
