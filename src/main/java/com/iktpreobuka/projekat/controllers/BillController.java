package com.iktpreobuka.projekat.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.iktpreobuka.projekat.controllers.util.RESTError;
import com.iktpreobuka.projekat.entities.BillEntity;
import com.iktpreobuka.projekat.entities.UserEntity;
import com.iktpreobuka.projekat.entities.dto.ReportDto;
import com.iktpreobuka.projekat.entities.dto.ReportItemDto;
import com.iktpreobuka.projekat.repositories.BillRepository;
import com.iktpreobuka.projekat.repositories.UserRepository;
import com.iktpreobuka.projekat.security.Views;
import com.iktpreobuka.projekat.services.BillService;
import com.iktpreobuka.projekat.services.EmailService;
import com.iktpreobuka.projekat.services.OfferService;
import com.iktpreobuka.projekat.services.ReportService;
import com.iktpreobuka.projekat.services.VoucherService;

@RestController
@RequestMapping("/project/bills")
public class BillController {

	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	VoucherService voucherService;
	 
	@Autowired
    ReportService reportService;
	
//	@JsonView(Views.Public.class)
//	@GetMapping("/public")
//	public Iterable<BillEntity> getAllPublicBills() {
//		return billRepository.findAll();
//	}
//
//	@JsonView(Views.Private.class)
//	@GetMapping("/private")
//	public Iterable<BillEntity> getAllPrivateBills() {
//		return billRepository.findAll();
//	}
//	
//	
//	@JsonView(Views.Administrator.class)
//	@GetMapping("/admin")
//	public Iterable<BillEntity> getAllAdminBills() {
//		return billRepository.findAll();
//	}
//	
//	
//	@PostMapping("/{offerId}/buyer/{buyerId}")
//	public BillEntity addNewBill(@PathVariable Integer offerId, @PathVariable Integer buyerId) {
//	
//		BillEntity bill = new BillEntity();
//		UserEntity user = userRepository.findById(buyerId).orElse(null);
//		
//		if (user != null && offerService.isOfferValid(offerId)) {
//			bill.setBillCreated(LocalDate.now());
//			bill.setPaymentMade(false);
//			bill.setPaymentCanceled(false);
//			bill.setOffer(offerService.updateAvailableBoughtOffer(offerId));
//			bill.setUser(user);
//			return billRepository.save(bill);
//		}
//		return null;
//	}
//	
//	@PutMapping("/{Id}")
//	public BillEntity updateBill(@RequestBody BillEntity updatedBill, @PathVariable Integer Id) {
//		
//		BillEntity bills = billRepository.findById(Id).orElse(null);
//
//		if (bills != null) {
//			
//			bills.setPaymentCanceled(updatedBill.getPaymentCanceled());
//			
//				if (updatedBill.getPaymentCanceled() == true) {
//				offerService.updateCanceledOffer(bills.getOffer().getId());
//				return billRepository.save(bills);
//				}
//			
//			bills.setPaymentMade(updatedBill.getPaymentMade());
//			
//				if (updatedBill.getPaymentMade() == true) {
//					emailService.sendVoucherEmail(bills.getUser().getEmail(), voucherService.createVoucherFromBill(Id));    
//					return billRepository.save(bills);
//				}
//		}
//		return null;
//	}
//	
//	@DeleteMapping("/{Id}")
//	public BillEntity deleteBill(@RequestBody BillEntity deleteBill,@PathVariable Integer Id){
//		BillEntity bills = billRepository.findById(Id).get();
//		billRepository.delete(bills);
//		return bills;	
//	
//	}
//	@GetMapping(value = "/findByBuyer/{buyerId}")
//	public Iterable<BillEntity> findByBuyer(@PathVariable Integer buyerId) {
//		return billRepository.findByUserId(buyerId);
//	}
//	@GetMapping(value = "/findByCategory/{categoryId}")
//	public Iterable<BillEntity> findByCategory(@PathVariable Integer categoryId) {
//		return billRepository.findByOfferCategoryId(categoryId);
//	}
//
//	@GetMapping(value = "/findByDate/{startDate}/and/{endDate}")
//	public Iterable<BillEntity> findByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//											@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
//		return billService.findAllByBillCreatedBetween(startDate, endDate);
//	}

	@JsonView(Views.Public.class)
	@GetMapping("/public")
	public ResponseEntity<Iterable<BillEntity>> getAllPublicBills() {
		Iterable<BillEntity> bills = billRepository.findAll();
		return new ResponseEntity<>(bills, HttpStatus.OK);
	}

	@JsonView(Views.Private.class)
	@GetMapping("/private")
	public ResponseEntity<Iterable<BillEntity>> getAllPrivateBills() {
		Iterable<BillEntity> bills = billRepository.findAll();
		return new ResponseEntity<>(bills, HttpStatus.OK);
	}

	@JsonView(Views.Administrator.class)
	@GetMapping("/admin")
	public ResponseEntity<Iterable<BillEntity>> getAllAdminBills() {
		Iterable<BillEntity> bills = billRepository.findAll();
		return new ResponseEntity<>(bills, HttpStatus.OK);
	}

	@PostMapping("/{offerId}/buyer/{buyerId}")
	public ResponseEntity<BillEntity> addNewBill(@PathVariable Integer offerId, 
												@PathVariable Integer buyerId) {

		BillEntity bill = new BillEntity();
		UserEntity user = userRepository.findById(buyerId).orElse(null);

		if (user != null && offerService.isOfferValid(offerId)) {
			bill.setBillCreated(LocalDate.now());
			bill.setPaymentMade(false);
			bill.setPaymentCanceled(false);
			bill.setOffer(offerService.updateAvailableBoughtOffer(offerId));
			bill.setUser(user);
			return new ResponseEntity<>(billRepository.save(bill), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/{Id}")
	public ResponseEntity<BillEntity> updateBill(@RequestBody BillEntity updatedBill, @PathVariable Integer Id) {

		BillEntity bills = billRepository.findById(Id).orElse(null);

		if (bills != null) {

			bills.setPaymentCanceled(updatedBill.getPaymentCanceled());

			if (updatedBill.getPaymentCanceled() == true) {
				offerService.updateCanceledOffer(bills.getOffer().getId());
				return new ResponseEntity<>(billRepository.save(bills), HttpStatus.OK);
			}

			bills.setPaymentMade(updatedBill.getPaymentMade());

			if (updatedBill.getPaymentMade() == true) {
				emailService.sendVoucherEmail(bills.getUser().getEmail(), voucherService.createVoucherFromBill(Id));
				return new ResponseEntity<>(billRepository.save(bills), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{Id}")
	public ResponseEntity<BillEntity> deleteBill(@PathVariable Integer Id){
		BillEntity bill = billRepository.findById(Id).orElse(null);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		billRepository.delete(bill);
		return new ResponseEntity<>(bill, HttpStatus.OK);	
	}
	
	@GetMapping(value = "/findByBuyer/{buyerId}")
	public ResponseEntity<Iterable<BillEntity>> findByBuyer(@PathVariable Integer buyerId) {
		Iterable<BillEntity> bills = billRepository.findByUserId(buyerId);
		if (!bills.iterator().hasNext()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(bills, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findByCategory/{categoryId}")
	public ResponseEntity<Iterable<BillEntity>> findByCategory(@PathVariable Integer categoryId) {
		Iterable<BillEntity> bills = billRepository.findByOfferCategoryId(categoryId);
		if (!bills.iterator().hasNext()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(bills, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findByDate/{startDate}/and/{endDate}")
	public ResponseEntity<Iterable<BillEntity>> findByDate(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
											@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
		Iterable<BillEntity> bills = billService.findAllByBillCreatedBetween(startDate, endDate);
		if (!bills.iterator().hasNext()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(bills, HttpStatus.OK);
	}
	
	@GetMapping("/generateReportByDate/{startDate}/and/{endDate}")
	public ResponseEntity<?> generateReportByDate(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate, 
	                                                                 @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
	    ReportDto report = reportService.generateReportByDatesBetween(startDate, endDate);
	    if (report == null) {
	    return new ResponseEntity<RESTError>(new RESTError(2, "No reports for dates"), HttpStatus.NOT_FOUND);
	    }
	    return new ResponseEntity<ReportDto>(report, HttpStatus.OK);
	}
	
	
	@GetMapping("generateReport/{startDate}/and/{endDate}/category/{categoryId}")
	public ResponseEntity<?> generateReportByDate(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate, 
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,@PathVariable Integer categoryId) {
				
		ReportDto report = reportService.generateReportByDatesBetweenAndCategory(startDate, endDate, categoryId);
		if (report == null) {
		    return new ResponseEntity<RESTError>(new RESTError(2 , "Category does not exist!"), HttpStatus.NOT_FOUND);
		    }
		if (report.getTotalNumberOfSoldOffers() == 0) {
			return new ResponseEntity<RESTError>(new RESTError(3 , "No sold bills for " + 
		report.getCategoryName().toString() + " category."), HttpStatus.NOT_FOUND);
		}
		    return new ResponseEntity<ReportDto>(report, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	

}
