package com.iktpreobuka.projekat.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.projekat.controllers.VoucherController;
import com.iktpreobuka.projekat.entities.BillEntity;
import com.iktpreobuka.projekat.entities.VoucherEntity;
import com.iktpreobuka.projekat.repositories.BillRepository;
import com.iktpreobuka.projekat.repositories.VoucherRepository;

@Service
public class VoucherServiceImpl implements VoucherService {

	@Autowired
	VoucherRepository voucherRepository;
	

	@Autowired
    private BillRepository billRepository;
	
	@Override
	public VoucherEntity createVoucherFromBill (Integer billId) {
		
		
	        BillEntity bill = billRepository.findById(billId).orElse(null);
	        VoucherEntity voucher = new VoucherEntity();
	        
	        voucher.setIsUsed(false);
			voucher.setExpirationDate(LocalDate.now().plusDays(VoucherController.VOUCHER_EXPIRATION_DAYS));
			voucher.setOffer(bill.getOffer());
			voucher.setUser(bill.getUser());
			return voucherRepository.save(voucher);

	}
}
