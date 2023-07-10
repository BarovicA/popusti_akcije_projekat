package com.iktpreobuka.projekat.services;

import javax.persistence.EntityNotFoundException;

import com.iktpreobuka.projekat.entities.OfferEntity;

public interface OfferService {

	
	public OfferEntity updateAvailableBoughtOffer(Integer offerId)  throws EntityNotFoundException, IllegalArgumentException;

	public OfferEntity updateCanceledOffer(Integer offerId) throws EntityNotFoundException, IllegalArgumentException;
	
	public boolean isOfferValid (Integer offerId) throws EntityNotFoundException;
	
	
	
}
