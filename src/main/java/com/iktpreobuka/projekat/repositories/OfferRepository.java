package com.iktpreobuka.projekat.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.projekat.entities.OfferEntity;

public interface OfferRepository extends CrudRepository<OfferEntity, Integer> {
	 
	
	Iterable<OfferEntity>findALLByActionPriceBetween(double lowerPrice, double upperPrice);
}
