package com.saikrupa.app.service.main;

import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.service.report.impl.CustomerOrderReportService;

public class ServiceMainTest {

	public ServiceMainTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		List<OrderData> data = new ArrayList<OrderData>();
		CustomerOrderReportService service = new CustomerOrderReportService(data);
		service.saveReport("Consolidated Order Report");

	}

}
