package com.saikrupa.app.dao;

import java.util.List;

import com.saikrupa.app.dto.PaymentTypeData;

public interface PaymentModeDAO {
	public List<PaymentTypeData> getPaymentModes();
	public PaymentTypeData getPaymentModeByCode(String code);
}
