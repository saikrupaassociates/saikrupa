package com.saikrupa.app.util;

import com.saikrupa.app.dto.DeliveryStatus;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.OrderStatus;
import com.saikrupa.app.dto.PaymentStatus;

public abstract class OrderUtil {

	public static int getOrderStatusCode(OrderData order) {
		int status = -1;
		switch (order.getOrderStatus()) {
		case CREATED:
			status = 0;
			break;
		case PLACED:
			status = 1;
			break;
		case CONFIRMED:
			status = 2;
			break;
		case SHIPPED:
			status = 3;
			break;
		case SHIPPING:
			status = 4;
			break;
		case DELIVERED:
			status = 5;
			break;
		case COMPLETED:
			status = 6;
			break;
		default:
			return -1;
		}
		return status;
	}

	public static int getPaymentStatusCode(OrderData order) {
		int status = -1;
		switch (order.getPaymentStatus()) {
		case PAID:
			status = 0;
			break;
		case PENDING:
			status = 1;
			break;
		case PARTIAL:
			status = 2;
			break;			
		default:
			return -1;
		}
		return status;
	}

	public static int getDeliveryStatusCode(OrderData order) {
		int status = -1;
		switch (order.getDeliveryStatus()) {
		case SHIPPED:
			status = 0;
			break;
		case SHIPPING:
			status = 1;
			break;
		default:
			return -1;
		}
		return status;
	}
	
	public static int getDeliveryStatusCode(DeliveryStatus deliveryStatus) {
		int status = -1;
		switch (deliveryStatus) {
		case SHIPPED:
			status = 0;
			break;
		case SHIPPING:
			status = 1;
			break;
		default:
			return -1;
		}
		return status;
	}	
	
	public static OrderStatus getOrderStatusByCode(int code) {
		OrderStatus status = OrderStatus.CREATED;
		switch (code) {
		case 0:
			status = OrderStatus.CREATED;
			break;
		case 1:
			status = OrderStatus.PLACED;
			break;
		case 2:
			status = OrderStatus.CONFIRMED;
			break;
		case 3:
			status = OrderStatus.SHIPPED;
			break;
		case 4:
			status = OrderStatus.SHIPPING;
			break;
		case 5:
			status = OrderStatus.DELIVERED;
			break;
		case 6:
			status = OrderStatus.COMPLETED;
			break;
		default:
			status = OrderStatus.CREATED;
			break;
		}
		return status;
	}
	
	public static int getStatusCodeByOrderStatus(OrderStatus status) {
		int code = -1;
		switch (status) {		
		case CREATED:
			code = 0;
			break;
		case PLACED:
			code = 1;
			break;
		case CONFIRMED:
			code = 2;
			break;
		case SHIPPED:
			code = 3;
			break;	
		case SHIPPING:
			code = 4;
			break;	
		case DELIVERED:
			code = 5;
			break;	
		case COMPLETED:
			code = 6;
			break;				
		default:			
			break;
		}
		return code;
	}

	public static PaymentStatus getPaymentStatusByCode(int code) {
		PaymentStatus payment = PaymentStatus.PENDING;
		switch (code) {
		
		case 0:
			payment = PaymentStatus.PAID;
			break;
		case 1:
			payment = PaymentStatus.PENDING;
			break;
		case 2:
			payment = PaymentStatus.PARTIAL;
			break;			
		default:
			payment = PaymentStatus.PENDING;
			break;
		}
		return payment;
	}
	
	public static int getCodeByPaymentStatus(PaymentStatus status) {
		int code = -1;
		switch (status) {		
		case PAID:
			code = 0;
			break;
		case PENDING:
			code = 1;
			break;
		case PARTIAL:
			code = 2;
			break;			
		default:			
			break;
		}
		return code;
	}

	public static DeliveryStatus getDeliveryStatusByCode(int code) {
		DeliveryStatus status = DeliveryStatus.SHIPPING;
		switch (code) {
		case 0:
			status = DeliveryStatus.SHIPPED;
			break;
		case 1:
			status = DeliveryStatus.SHIPPING;
			break;
		default:
			status = DeliveryStatus.SHIPPING;
			break;
		}
		return status;
	}

}
