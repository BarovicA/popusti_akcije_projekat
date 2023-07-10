package com.iktpreobuka.projekat.services;

import java.time.LocalDate;
import com.iktpreobuka.projekat.entities.BillEntity;

public interface BillService {

	

	Iterable<BillEntity> findAllByBillCreatedBetween(LocalDate startDate, LocalDate endDate)
			throws IllegalArgumentException;

	public void cancelAllBillsWithOffer(Integer offerId);
}
