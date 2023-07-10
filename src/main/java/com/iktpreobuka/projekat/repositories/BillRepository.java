package com.iktpreobuka.projekat.repositories;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.projekat.entities.BillEntity;
import com.iktpreobuka.projekat.entities.OfferEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer> {

	Iterable<BillEntity> findByUserId(Integer buyerId);

	Iterable<BillEntity> findByOfferCategoryId(Integer categoryId);

	Iterable<BillEntity> findAllBybillCreatedBetween(LocalDate date, LocalDate date2);

	Iterable<BillEntity> findAllByBillCreatedBetween(LocalDate startDate, LocalDate endDate);
	
	Iterable<BillEntity> findAllByOfferId(Integer offerId);
	
	List<BillEntity> findByBillCreated(LocalDate billCreatedDate);
	
	
}
