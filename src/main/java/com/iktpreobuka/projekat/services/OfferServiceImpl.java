package com.iktpreobuka.projekat.services;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.projekat.entities.OfferEntity;
import com.iktpreobuka.projekat.repositories.OfferRepository;

@Service
public class OfferServiceImpl implements OfferService {

	
	@Autowired
	private OfferRepository offerRepository;
	
	

	
	@Override
	public OfferEntity updateAvailableBoughtOffer(Integer offerId)  throws EntityNotFoundException, IllegalArgumentException {
	    OfferEntity offer = offerRepository.findById(offerId).orElse(null);
	    if (offer == null) {
	        throw new EntityNotFoundException("Offer with id " + offerId + " not found.");
	    }
	    if (offer.getAvailableOffers() <= 0) {
	        throw new IllegalArgumentException("There are no available offers left for this product.");
	    }

	    Integer aO = offer.getAvailableOffers();
	    Integer bO = offer.getBoughtOffers();

	    offer.setAvailableOffers(aO - 1);
	    offer.setBoughtOffers(bO + 1);

	    return offerRepository.save(offer);
	}
	
	
	@Override
	public OfferEntity updateCanceledOffer(Integer offerId)  throws EntityNotFoundException, IllegalArgumentException {
	    OfferEntity offer = offerRepository.findById(offerId).orElse(null);
	    if (offer == null) {
	        throw new EntityNotFoundException("Offer with id " + offerId + " not found.");
	    }
	    if (offer.getAvailableOffers() <= 0) {
	        throw new IllegalArgumentException("There are no available offers left for this product.");
	    }

	    Integer aO = offer.getAvailableOffers();
	    Integer bO = offer.getBoughtOffers();

	    offer.setAvailableOffers(aO + 1);
	    offer.setBoughtOffers(bO - 1);

	    return offerRepository.save(offer);
	}


	@Override
	public boolean isOfferValid(Integer offerId) throws EntityNotFoundException {
		
		OfferEntity offer = offerRepository.findById(offerId).orElse(null);
		
		if (offer == null) {
	        throw new EntityNotFoundException("Offer with id " + offerId + " not found.");
	    }
		
		if (offer.getOfferExpires().after(new Date())) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
