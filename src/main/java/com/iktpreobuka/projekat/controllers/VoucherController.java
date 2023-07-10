package com.iktpreobuka.projekat.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.projekat.entities.OfferEntity;
import com.iktpreobuka.projekat.entities.UserEntity;
import com.iktpreobuka.projekat.entities.UserEntity.UserRole;
import com.iktpreobuka.projekat.entities.VoucherEntity;
import com.iktpreobuka.projekat.repositories.OfferRepository;
import com.iktpreobuka.projekat.repositories.UserRepository;
import com.iktpreobuka.projekat.repositories.VoucherRepository;
import com.iktpreobuka.projekat.security.Views;
import com.iktpreobuka.projekat.services.OfferService;

@RestController
@RequestMapping(value = "/project/vouchers")
public class VoucherController {

	public static long VOUCHER_EXPIRATION_DAYS = 30;
	
	@Autowired
	VoucherRepository voucherRepository;
	
	@Autowired
	OfferService offerService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OfferRepository offerRepository;
	
	
//	@JsonView(Views.Public.class)
//	@GetMapping("/public")
//	public Iterable<VoucherEntity> getAllPublic() {
//		return voucherRepository.findAll();
//	}
//
//	@JsonView(Views.Private.class)
//	@GetMapping("/private")
//	public Iterable<VoucherEntity> getAllPrivate() {
//		return voucherRepository.findAll();
//	}
//	
//	@JsonView(Views.Administrator.class)
//	@GetMapping("/admin")
//	public Iterable<VoucherEntity> getAllAdmin() {
//		return voucherRepository.findAll();
//	}	
//		
//	@PostMapping("/{offerId}/buyer/{buyerId}")
//	public VoucherEntity createNewVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId,
//			@RequestBody VoucherEntity newVoucher) {
//		
//		VoucherEntity voucher = new VoucherEntity();
//		UserEntity buyer = userRepository.findById(buyerId).orElse(null);
//		OfferEntity offer = offerRepository.findById(offerId).orElse(null);
//		
//		if (buyer != null && offer != null) {
//			if (buyer.getUserRole() == UserRole.ROLE_CUSTOMER && offerService.isOfferValid(offerId) ) {
//				voucher.setIsUsed(false);
//				voucher.setExpirationDate(LocalDate.now().plusDays(VOUCHER_EXPIRATION_DAYS));
//				voucher.setOffer(offer);
//				voucher.setUser(buyer);
//				return voucherRepository.save(voucher);
//			}
//		return null;
//		}
//	return null;
//		
//	}
//
//	@DeleteMapping("/{id}")
//	public VoucherEntity deleteVoucher(@PathVariable Integer id) {
//
//		VoucherEntity voucher = voucherRepository.findById(id).get();
//		voucherRepository.delete(voucher);
//		
//		return voucher;
//	}
//	
//	@PutMapping("/{id}")
//	public VoucherEntity updateVoucher(@PathVariable Integer id, @RequestBody VoucherEntity updatedVoucher) {
//		
//		VoucherEntity voucher = voucherRepository.findById(id).get();
//
//		if (updatedVoucher.getIsUsed() != null)
//			voucher.setIsUsed(updatedVoucher.getIsUsed());
////		if (updatedVoucher.getExpirationDate() != null)
////			voucher.setExpirationDate(updatedVoucher.getExpirationDate());
//		return voucherRepository.save(voucher);
//	}
//	
//	@GetMapping("/findByBuyer/{buyerId}")
//	public Iterable<VoucherEntity> findByBuyer(@PathVariable Integer buyerId) {
//		
//		return voucherRepository.findByUserId(buyerId);
//	}
//	
//	@GetMapping("/findByOffer/{offerId}")
//	public Iterable<VoucherEntity> findByOffer(@PathVariable Integer offerId) {
//		
//		return voucherRepository.findByOfferId(offerId);
//	}
//	
//	@GetMapping("/findNonExpiredVoucher")
//	public Iterable<VoucherEntity> findNonExpiredVoucher() {
//		
//		return voucherRepository.findByExpirationDateGreaterThanEqual(LocalDate.now());
//	}
	
	@JsonView(Views.Public.class)
	@GetMapping("/public")
	public ResponseEntity<Iterable<VoucherEntity>> getAllPublic() {
		return new ResponseEntity<>(voucherRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(Views.Private.class)
	@GetMapping("/private")
	public ResponseEntity<Iterable<VoucherEntity>> getAllPrivate() {
		return new ResponseEntity<>(voucherRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(Views.Administrator.class)
	@GetMapping("/admin")
	public ResponseEntity<Iterable<VoucherEntity>> getAllAdmin() {
		return new ResponseEntity<>(voucherRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping("/{offerId}/buyer/{buyerId}")
	public ResponseEntity<VoucherEntity> createNewVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@RequestBody VoucherEntity newVoucher) {

		VoucherEntity voucher = new VoucherEntity();
		UserEntity buyer = userRepository.findById(buyerId).orElse(null);
		OfferEntity offer = offerRepository.findById(offerId).orElse(null);

		if (buyer != null && offer != null) {
			if (buyer.getUserRole() == UserRole.ROLE_CUSTOMER && offerService.isOfferValid(offerId)) {
				voucher.setIsUsed(false);
				voucher.setExpirationDate(LocalDate.now().plusDays(VOUCHER_EXPIRATION_DAYS));
				voucher.setOffer(offer);
				voucher.setUser(buyer);
				return new ResponseEntity<>(voucherRepository.save(voucher), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<VoucherEntity> deleteVoucher(@PathVariable Integer id) {

		VoucherEntity voucher = voucherRepository.findById(id).orElse(null);
		if (voucher != null) {
			voucherRepository.delete(voucher);
			return new ResponseEntity<>(voucher, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<VoucherEntity> updateVoucher(@PathVariable Integer id,
			@RequestBody VoucherEntity updatedVoucher) {

		VoucherEntity voucher = voucherRepository.findById(id).orElse(null);

		if (voucher == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (updatedVoucher.getIsUsed() != null) {
			voucher.setIsUsed(updatedVoucher.getIsUsed());
		}

		voucher = voucherRepository.save(voucher);
		return new ResponseEntity<>(voucher, HttpStatus.OK);
	}

	@GetMapping("/findByBuyer/{buyerId}")
	public ResponseEntity<Iterable<VoucherEntity>> findByBuyer(@PathVariable Integer buyerId) {
		return ResponseEntity.ok(voucherRepository.findByUserId(buyerId));
	}
	
	@GetMapping("/findByOffer/{offerId}")
	public ResponseEntity<Iterable<VoucherEntity>> findByOffer(@PathVariable Integer offerId) {
		
		return ResponseEntity.ok(voucherRepository.findByOfferId(offerId));
	}

	@GetMapping("/findNonExpiredVoucher")
	public ResponseEntity<Iterable<VoucherEntity>> findNonExpiredVoucher() {
		
		return ResponseEntity.ok(voucherRepository.findByExpirationDateGreaterThanEqual(LocalDate.now()));
	}
	
	
}
