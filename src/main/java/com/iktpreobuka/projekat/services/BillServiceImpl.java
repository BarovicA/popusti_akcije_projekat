package com.iktpreobuka.projekat.services;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.projekat.entities.BillEntity;
import com.iktpreobuka.projekat.repositories.BillRepository;

@Service
public class BillServiceImpl implements BillService {

	
	@Autowired
    private BillRepository billRepository;
	
	
	@Override
    public Iterable<BillEntity> findAllByBillCreatedBetween(LocalDate startDate, LocalDate endDate) throws IllegalArgumentException {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        Iterable<BillEntity> bills = billRepository.findAllByBillCreatedBetween(startDate, endDate);
        return bills;
    }


	@Override
	public void cancelAllBillsWithOffer(Integer offerId) {
		ArrayList<BillEntity> bills = (ArrayList<BillEntity>) billRepository.findAllByOfferId(offerId);
		for (BillEntity billEntity : bills) {
			billEntity.setPaymentCanceled(true);
		}
		
	}

	
}
