package com.iktpreobuka.projekat.services;

import com.iktpreobuka.projekat.entities.VoucherEntity;

public interface EmailService {

	String createVoucherTableHtml(VoucherEntity voucher);

	void sendVoucherEmail(String userEmail, VoucherEntity voucher);

}
