package com.iktpreobuka.projekat.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.projekat.entities.BillEntity;
import com.iktpreobuka.projekat.entities.CategoryEntity;
import com.iktpreobuka.projekat.entities.dto.ReportDto;
import com.iktpreobuka.projekat.entities.dto.ReportItemDto;
import com.iktpreobuka.projekat.repositories.BillRepository;
import com.iktpreobuka.projekat.repositories.CategoryRepository;

@Service            
public class ReportServiceImpl implements ReportService {

	@Autowired
    BillService billService;
    @Autowired
    BillRepository billRepository;
    @Autowired
    CategoryRepository categoryRepository;
    
   
    @Override
    public ReportDto generateReportByDatesBetween(LocalDate startDate, LocalDate endDate) {
        
        return generateReportByDates(startDate, endDate, generateReportItemsByDate(startDate, endDate));
    }

    @Override
    public ReportDto generateReportByDatesBetweenAndCategory(LocalDate startDate, LocalDate endDate, Integer categoryId) {
        
        return generateReportByDatesAndCategory(startDate, endDate, categoryId, 
        		generateReportItemsByDateAndCategory(startDate, endDate, categoryId));
    }
 
    
    private List<ReportItemDto> generateReportItemsByDate(LocalDate startDate, LocalDate endDate) {
        List<ReportItemDto> reportItems = new ArrayList<>();
        //prolazimo kroz svaki datum redom
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
        	//za trenutni dan nalazimo sve racune
            List<BillEntity> allBillsForOneDay = billRepository.findByBillCreated(date);
            //da li postoji lista za taj dan
            if (allBillsForOneDay != null) {
            //prolazimo kroz listu dok se ne isprazni lista i,
            //kad obradimo jedan racun(pretvorimo ga u reportItemDto) odma ga izbrisemo
            while (!allBillsForOneDay.isEmpty()) {
                ReportItemDto currentReportItem = new ReportItemDto();
                //krecemo od prvog racuna u listi
                BillEntity currentBill = allBillsForOneDay.get(0);
                //izvacenje imena kategorije po kojoj ce se spajati racuni za zadati dan (moglo je i id)
                String currentCategory = currentBill.getOffer().getCategory().getCategoryName().toString();
                currentReportItem.setDate(date);
                currentReportItem.setIncome(currentBill.getOffer().getActionPrice());
                currentReportItem.setNumberOfOffers(1);
                //brisemo ooradjeni racun
                allBillsForOneDay.remove(0);
                //prolazimo kroz ostale racune iz liste i proveramo da li je ista kategorija ponude, 
                //ako jeste dodajemo racun u ReportItemDto(povecacamo uk cenu i uk br prodaja iz te kategorije) i brisemo ga.
                 for (int i = 0; i < allBillsForOneDay.size(); i++) {
                    BillEntity bill = allBillsForOneDay.get(i);
                    if (bill.getOffer().getCategory().getCategoryName().toString().equals(currentCategory)) {
                        currentReportItem.setIncome(currentReportItem.getIncome() + bill.getOffer().getActionPrice());
                        currentReportItem.setNumberOfOffers(currentReportItem.getNumberOfOffers() +1);
                        allBillsForOneDay.remove(i);
                        i--;
                    }
                 }
                reportItems.add(currentReportItem);
            }
         }
        }
        return reportItems;
    }
    
    private List<ReportItemDto> generateReportItemsByDateAndCategory(LocalDate startDate, LocalDate endDate, Integer categoryId){
    	List<ReportItemDto> reportItems = new ArrayList<>();
    	for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            List<BillEntity> allBillsForOneDay = (List<BillEntity>) billRepository.findByBillCreated(date);
            if (!allBillsForOneDay.isEmpty()) {
            	ReportItemDto item = new ReportItemDto();	
            	item.setDate(date);
            	item.setNumberOfOffers(0);
            	item.setIncome(0.0);
            	boolean categoryIdFound = false;
            	for (BillEntity bill : allBillsForOneDay) {
    				if (categoryId == (bill.getOffer().getCategory().getId()) ) {
    					item.setIncome(item.getIncome() + bill.getOffer().getActionPrice());
    					item.setNumberOfOffers(item.getNumberOfOffers() +1);
    					categoryIdFound = true;
    				}
    			}
    			if (categoryIdFound) {
    				reportItems.add(item);
    			}
            }
    	}     
    	return reportItems;
    }

	 private ReportDto generateReportByDates(LocalDate startDate, LocalDate endDate, List<ReportItemDto> items) {
		   
	        ReportDto report = new ReportDto();
	        report.setCategoryName("All Categories");
	        report.setListOfReportItems(items);
	        report.setSumOfIncomes(0.0);
	        report.setTotalNumberOfSoldOffers(0);
	        for (ReportItemDto reportItemDto : items) {
				reportItemDto.getIncome();
				report.setSumOfIncomes(report.getSumOfIncomes() + reportItemDto.getIncome());
				report.setTotalNumberOfSoldOffers(report.getTotalNumberOfSoldOffers() + reportItemDto.getNumberOfOffers());
			}
	        return report;
	    }
	 
		private ReportDto generateReportByDatesAndCategory(LocalDate startDate, LocalDate endDate, Integer categoryId,
				List<ReportItemDto> items) {

			ReportDto report = new ReportDto();
			CategoryEntity category = categoryRepository.findById(categoryId).orElse(null);
			if (category == null) {
				return null;
			}

			report.setCategoryName(category.getCategoryName());
			report.setListOfReportItems(items);
			report.setSumOfIncomes(0.0);
			report.setTotalNumberOfSoldOffers(0);
			for (ReportItemDto reportItemDto : items) {

				reportItemDto.getIncome();
				report.setSumOfIncomes(report.getSumOfIncomes() + reportItemDto.getIncome());
				report.setTotalNumberOfSoldOffers(
						report.getTotalNumberOfSoldOffers() + reportItemDto.getNumberOfOffers());
			}
			return report;
		}


   
    
    
//		"SELECT (b.billCreated, b.offer.category.id, SUM(b.offer.actionPrice), COUNT(b.offer.id)) " +
//        "FROM bill_Entity b " and 
//        "WHERE b.billCreated >= :startDate AND b.billCreated <= :endDate " +
//        "GROUP BY b.billCreated, b.offer.category.id"
    
    
    
    
    
    
    
    
    
    
    
    
    
//    
//private List<ReportItemDto> generateReportItems(LocalDate startDate, LocalDate endDate) {
//        
//        List<ReportItemDto> reportItems = new ArrayList<>();
//        
//        for (LocalDate d = startDate; d.isBefore(endDate.plusDays(1)); d = d.plusDays(1)) {
//            List<BillEntity> billsForDate = billRepository.findByBillCreated(d);
//            ReportItemDto reportItem = new ReportItemDto();
//            reportItem.setDate(d);
//            reportItem.setNumberOfOffers((int) billsForDate.size());
//            reportItem.setIncome(billsForDate.stream()
//                    .map((e) -> e.getOffer().getActionPrice())
//                    .reduce(0.0, (a, b) -> a + b));
//            reportItems.add(reportItem);
//        }
//        
//        return reportItems;
//    
//    }
//    
//    private List<ReportItemDto> generateReportItems(LocalDate startDate, LocalDate endDate, Integer categoryId) {
//        
//        List<ReportItemDto> reportItems = new ArrayList<>();
//        
//        for (LocalDate d = startDate; d.isBefore(endDate.plusDays(1)); d = d.plusDays(1)) {
//            List<BillEntity> billsForDate = ((Collection<BillEntity>) billRepository.findByBillCreated(d)).stream()
//            		.filter((e) -> e.getOffer().getCategory().getId().equals(categoryId))
//                    .toList();
//            ReportItemDto reportItem = new ReportItemDto();
//            reportItem.setDate(d);
//            reportItem.setNumberOfOffers((int) billsForDate.size());
//            reportItem.setIncome(billsForDate.stream()
//                    .map((e) -> e.getOffer().getActionPrice())
//                    .reduce(0.0, (a, b) -> a + b));
//            reportItems.add(reportItem);
//        }
//        
//        return reportItems;
//        
//    }
//    
//    private ReportDto generateReport(LocalDate startDate, LocalDate endDate,
//    		List<ReportItemDto> items, Integer categoryId) {
//        
//        ReportDto report = new ReportDto();
//        
//        report.setCategoryName(categoryId == null ? "All" : categoryRepository.findById(categoryId).get().getCategoryName());
//        report.setTotalNumberOfSoldOffers(
//                items.stream()
//                .map(ReportItemDto::getNumberOfOffers)
//                .reduce(0, (a, b) -> a + b)
//                );
//        report.setListOfReportItems(items);
//        report.setSumOfIncomes(
//                items.stream()
//                .map(ReportItemDto::getIncome)
//                .reduce(0.0, (a, b) -> a + b)
//                );
//        
//        return report;
//    }
}
