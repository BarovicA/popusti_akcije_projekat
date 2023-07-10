package com.iktpreobuka.projekat.controllers;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.projekat.entities.CategoryEntity;
import com.iktpreobuka.projekat.entities.OfferEntity;
import com.iktpreobuka.projekat.entities.UserEntity;
import com.iktpreobuka.projekat.entities.UserEntity.UserRole;
import com.iktpreobuka.projekat.entities.OfferEntity.OfferStatus;
import com.iktpreobuka.projekat.repositories.CategoryRepository;
import com.iktpreobuka.projekat.repositories.OfferRepository;
import com.iktpreobuka.projekat.repositories.UserRepository;
import com.iktpreobuka.projekat.services.BillService;
import com.iktpreobuka.projekat.services.OfferService;

@RestController
@RequestMapping(value = "/project/offers")
public class OfferController { 
	
	private static String UPLOAD_DIRECTORY = "C:\\Images For Offers\\";
	
	private final int DAYS_OFFER_VALUE = 30;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private BillService billService;
	
	public OfferEntity getOfferRepository() {
		return getOfferRepository();
	}

	public void setOfferRepository(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}
	
	
		
	@PostMapping("/{categoryId}/seller/{sellerId}")
	public OfferEntity addOffer(@PathVariable Integer categoryId, 
			@PathVariable Integer sellerId, @RequestBody OfferEntity newOffer) {
		
		OfferEntity offer = new OfferEntity();
		UserEntity user = userRepository.findById(sellerId).orElse(null);
		CategoryEntity category = categoryRepository.findById(categoryId).orElse(null);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, DAYS_OFFER_VALUE);
		
		if (user != null && category != null) {
			if (user.getUserRole().equals(UserRole.ROLE_SELLER)) {
				if (newOffer.getOfferName() != null)
					offer.setOfferName(newOffer.getOfferName());
				if (newOffer.getOfferDescription() != null)
					offer.setOfferDescription(newOffer.getOfferDescription());
				if (newOffer.getImagePath() != null)
				offer.setImagePath(newOffer.getImagePath());
				offer.setAvailableOffers(newOffer.getAvailableOffers());
				offer.setActionPrice(newOffer.getActionPrice());
				offer.setRegularPrice(newOffer.getRegularPrice());
				offer.setOfferCreated(new Date());
				offer.setOfferExpires(cal.getTime());
				offer.setBoughtOffers(0);
				offer.setOfferStatus(OfferStatus.WAIT_FOR_APPROVING);
				offer.setUser(user);
				offer.setCategory(category);
				return offerRepository.save(offer);
			}
			return null;
		}
		return null;
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<OfferEntity> getALL(){
		return offerRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/{Id}" )
	public OfferEntity findById(@PathVariable Integer Id ){
	
		return offerRepository.findById(Id).orElse(null);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, path ="/{id}/category/{categoryId}")
	public OfferEntity changeOffer(@PathVariable Integer id, @PathVariable Integer categoryId 
			,@RequestBody OfferEntity updatedOffer) {
		OfferEntity offer = offerRepository.findById(id).orElse(null);
		CategoryEntity category = categoryRepository.findById(categoryId).orElse(null);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, DAYS_OFFER_VALUE);
		if (offer != null && (category != null && category.getId() == offer.getCategory().getId())) {
			
			if (updatedOffer.getActionPrice() != null) 
				offer.setActionPrice(updatedOffer.getActionPrice());//
			if (updatedOffer.getOfferName() != null)
					offer.setOfferName(updatedOffer.getOfferName());//
			if (updatedOffer.getOfferDescription() != null)
					offer.setOfferDescription(updatedOffer.getOfferDescription());//
			if (updatedOffer.getRegularPrice() != null)
					offer.setRegularPrice(updatedOffer.getRegularPrice());//
			if (updatedOffer.getAvailableOffers() != null)		
					offer.setAvailableOffers(updatedOffer.getAvailableOffers());//
			if (updatedOffer.getBoughtOffers() != null)
					offer.setBoughtOffers(updatedOffer.getBoughtOffers());//
			if (updatedOffer.getImagePath() != null) 		
					offer.setImagePath(updatedOffer.getImagePath());//	
					
		offer.setOfferStatus(OfferStatus.WAIT_FOR_APPROVING);
		offer.setOfferCreated(new Date());
		offer.setOfferExpires(cal.getTime());
			
		return offerRepository.save(offer);
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path ="/{Id}")
	public OfferEntity deleteOffer(@PathVariable Integer Id){
		OfferEntity offer = offerRepository.findById(Id).orElse(null);
		if (offer != null) {
			offerRepository.deleteById(Id);
			return offer;
		}
		return null;
				
	}
	
	/* 3.8 kreirati REST endpoint koji omogucava promenu vrednosti atributa offer status postojece ponude	
	 * putanja /project/offers/changeOffer/{id}/status/{status}
	 */
	
	@RequestMapping(method = RequestMethod.PUT, path="/changeOffer/{Id}/status/{status}")
	public OfferEntity findByIdPatchByStatus(@PathVariable Integer Id ,
			@PathVariable OfferStatus offerStatus){
		if(offerRepository.findById(Id).isEmpty()) {
			return null;
		}OfferEntity offer= offerRepository.findById(Id).get();
		offer.setOfferStatus(offerStatus);
		offerRepository.save(offer);
		return offer;	
		}
	
//	/*3.9 kreirati REST endpoin koji omogucava pronalazak svih ponuda cija se akcijska cena nalazi u odgovarajucem rasponu 
//	 * putanja/project/offers/findByPrice/{lowerPrice}/and/{upperPrice}

	
	 
	
	@RequestMapping(method = RequestMethod.GET, path="/findByPrice/{lowerPrice}/and/{upperPrice}")
	public Iterable<OfferEntity> findALLByActionPriceBetween(@PathVariable double lowerPrice, @PathVariable double upperPrice){
		Iterable<OfferEntity> offer =  offerRepository.findALLByActionPriceBetween(lowerPrice, upperPrice);
		return offer;
	}

//	3.2 kreirati REST endpoint koji omogućava upload slike
//	za kreiranu ponudu, putanja "/project/uploadImage/{id}"
	
	
	
//	In Postman, create a PUT request and set the body type to "form-data". 
//	Then you can add a key-value pair where the key is the name of the parameter that handles, 
//	the file and the value is the file itself. 
//	In the value section you will find a button to select files to upload.
	
	@PutMapping("/uploadImage/{offerId}")
    public OfferEntity uploadImage(@PathVariable Integer offerId, 
    		@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        OfferEntity offer = offerRepository.findById(offerId).orElse(null);
        if (offer == null) {
            return null;
        }
        try {
            
            byte[] bytes = imageFile.getBytes();
            Path path = Paths.get(UPLOAD_DIRECTORY + imageFile.getOriginalFilename());
            Files.write(path, bytes);
            
            offer.setImagePath(path.toString());
            return offerRepository.save(offer);
      

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	
//	3.3 ukoliko se ponuda proglasi isteklom potrebno je
//	otkazati sve račune koji sadrže tu ponudu
//	•
//	u okviru servisa zaduženog za rad sa računima napisati metodu
//	koja otkazuje sve račune odgovarajuće ponude
//	•
//	pozvati je u okviru metode za promenu statusa ponude u
//	OfferController u
//	
	
	@PutMapping("/changeStatus/{id}")
	public OfferEntity updateOfferStatus(@PathVariable Integer id, @RequestParam OfferStatus newStatus) {
		
		OfferEntity offer = offerRepository.findById(id).orElse(null);
//		EOfferStatus off = EOfferStatus.valueOf(newStatus);	
//		offer.setOfferStatus(off);
		
		if(offer != null) {
			offer.setOfferStatus(newStatus);
;
			if (offer.getOfferStatus().compareTo(OfferStatus.EXPIRED) == 0 ) {
				billService.cancelAllBillsWithOffer(offer.getId());
				offerService.updateCanceledOffer(id);
				offerRepository.save(offer);
			}
			offerRepository.save(offer);
		}
		return null;	
	}
	
	
	
	
	
	







//	public List<OfferEntity> getDB() {
//		ArrayList<OfferEntity> offers = new ArrayList<OfferEntity>();
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//		cal.add(Calendar.DATE, 5);
//
//		OfferEntity o1 = new OfferEntity(1, "2 tickets for killers concert", "EnjoY!!!", new Date(), cal.getTime(),
//				100000.00, 6500.00, " ", 10, 0, OfferStatus.WAIT_FOR_APPROVING);
//		OfferEntity o2 = new OfferEntity(2, "VIVAX 24LE76T2", "Don't miss this fantastic offer!", new Date(),
//				cal.getTime(), 20000.00, 16500.00, " ", 5, 0, OfferStatus.WAIT_FOR_APPROVING);
//		OfferEntity o3 = new OfferEntity(3, "Dinner for two in Aqua Doria", "Excellent offer", new Date(),
//				cal.getTime(), 6000.00, 3500.00, " ", 4, 0, OfferStatus.WAIT_FOR_APPROVING);
//
//		offers.add(o1);
//		offers.add(o2);
//		offers.add(o3);
//
//		return offers;
	
}
