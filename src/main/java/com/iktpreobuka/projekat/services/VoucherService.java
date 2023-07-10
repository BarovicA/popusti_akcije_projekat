package com.iktpreobuka.projekat.services;

import com.iktpreobuka.projekat.entities.VoucherEntity;

public interface VoucherService {

	VoucherEntity createVoucherFromBill(Integer billId);

}
