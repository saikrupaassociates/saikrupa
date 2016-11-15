package com.saikrupa.app.service.report.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.PaymentStatus;
import com.saikrupa.app.service.report.common.CellValueType;
import com.saikrupa.app.service.report.component.ReportTableDataCell;
import com.saikrupa.app.service.report.component.ReportTableHeaderCell;

public class DeliveredPendingPaymentOrderReportService extends AbstractReportService {
	
	private List<OrderData> orders;

	public DeliveredPendingPaymentOrderReportService(List<OrderData> orders) {
		setOrders(orders);
	}
	
	public void saveReport(final String reportName) {
		Document pdfDocument = createBlankDocument(reportName);
		pdfDocument.close();
	}

	@Override
	public void createPageTableContent(Document document) throws DocumentException {
		PdfPTable dataTable = new PdfPTable(8);
		dataTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		dataTable.getDefaultCell().setBorder(2);

		dataTable.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
		dataTable.setWidthPercentage(100F);
		ReportTableHeaderCell header_OrderNo = new ReportTableHeaderCell("Order Number");
		ReportTableHeaderCell header_customer = new ReportTableHeaderCell("Customer");
		ReportTableHeaderCell header_OrderDate = new ReportTableHeaderCell("Ordered On");
		ReportTableHeaderCell header_OrderBy = new ReportTableHeaderCell("Unit Price");
		ReportTableHeaderCell orderQuantity = new ReportTableHeaderCell("Quantity");
		ReportTableHeaderCell amount = new ReportTableHeaderCell("Order Total");
		ReportTableHeaderCell paymentStatus = new ReportTableHeaderCell("Payment");
		ReportTableHeaderCell deliveryStatus = new ReportTableHeaderCell("Delivery");
		
		dataTable.addCell(header_OrderNo);
		dataTable.addCell(header_customer);
		dataTable.addCell(header_OrderDate);
		dataTable.addCell(header_OrderBy);
		dataTable.addCell(orderQuantity);
		dataTable.addCell(amount);
		dataTable.addCell(paymentStatus);
		dataTable.addCell(deliveryStatus);
		
		for(OrderData data : getOrders()) {
			ReportTableDataCell data_OrderNo = new ReportTableDataCell(String.valueOf(data.getCode()), CellValueType.TEXT);
			ReportTableDataCell data_customer = new ReportTableDataCell(String.valueOf(data.getCustomer().getName()), CellValueType.TEXT);
			ReportTableDataCell data_OrderDate = new ReportTableDataCell(data.getCreatedDate(), CellValueType.DATE);
			ReportTableDataCell data_OrderedBy = new ReportTableDataCell(data.getOrderEntries().get(0).getPrice(), CellValueType.AMOUNT);
			ReportTableDataCell data_quantity = new ReportTableDataCell(Double.valueOf(data.getOrderEntries().get(0).getOrderedQuantity()), CellValueType.QUANTITY);
			ReportTableDataCell data_amount = new ReportTableDataCell(data.getTotalPrice(), CellValueType.AMOUNT);			
			ReportTableDataCell data_paymentStatus = new ReportTableDataCell(data.getPaymentStatus().toString(), CellValueType.TEXT);
			ReportTableDataCell data_deliveryStatus = new ReportTableDataCell(data.getDeliveryStatus().toString(), CellValueType.TEXT);
			
			dataTable.addCell(data_OrderNo);
			dataTable.addCell(data_customer);
			dataTable.addCell(data_OrderDate);
			dataTable.addCell(data_OrderedBy);
			dataTable.addCell(data_quantity);
			dataTable.addCell(data_amount);
			dataTable.addCell(data_paymentStatus);
			dataTable.addCell(data_deliveryStatus);
		}
		
		PdfPTable consolidationTable = new PdfPTable(5);
		ReportTableHeaderCell totalOrder = new ReportTableHeaderCell("Total Order Amount");
		ReportTableHeaderCell receivedPayment = new ReportTableHeaderCell("Payment Received");
		ReportTableHeaderCell pendingPayment = new ReportTableHeaderCell("Payment Pending");
		
		ReportTableDataCell totalOrderValue = new ReportTableDataCell(getOrderTotalMap().get("TOTAL_ORDER_VALUE"), CellValueType.AMOUNT, true);
		ReportTableDataCell paidValue = new ReportTableDataCell(getOrderTotalMap().get("TOTAL_ORDER_PAID"), CellValueType.AMOUNT, true);
		ReportTableDataCell pendingValue = new ReportTableDataCell(getOrderTotalMap().get("TOTAL_ORDER_PENDING"), CellValueType.AMOUNT, true);
		
		consolidationTable.addCell(totalOrder);
		totalOrderValue.setColspan(4);
		consolidationTable.addCell(totalOrderValue);
		
		consolidationTable.addCell(receivedPayment);
		paidValue.setColspan(4);
		consolidationTable.addCell(paidValue);
		
		consolidationTable.addCell(pendingPayment);
		pendingValue.setColspan(4);
		consolidationTable.addCell(pendingValue);
		
		PdfPTable outerTable = new PdfPTable(1);
		outerTable.setWidthPercentage(100F);
		outerTable.addCell(consolidationTable);
		outerTable.addCell(dataTable);
		
		document.add(outerTable);
	}
	
	private Map<String, Double> getOrderTotalMap() {
		double totalValue = 0;
		double totalPaid = 0;
		for(OrderData data : getOrders()) {
			totalValue = totalValue + data.getTotalPrice();
			if(data.getPaymentStatus() == PaymentStatus.PAID){
				totalPaid = totalPaid + data.getTotalPrice();
			}
		}
		HashMap<String, Double> map = new HashMap<String, Double>();
		map.put("TOTAL_ORDER_VALUE", totalValue);
		map.put("TOTAL_ORDER_PAID", totalPaid);
		map.put("TOTAL_ORDER_PENDING", totalValue - totalPaid);
		return map;
			
	}

	@Override
	public void addMetaData(Document document) {
		document.addTitle("Order Report - Delivered :: Pending Payment");
		
	}

	@Override
	public String getPageTitle() {
		return "Order Report - Delivered :: Pending Payment";
	}

	public List<OrderData> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderData> orders) {
		this.orders = orders;
	}
}
