package com.iktpreobuka.projekat.entities.dto;

import java.util.List;

public class ReportDto {

	private String categoryName;
	private List<ReportItemDto> listOfReportItems;
	private Double sumOfIncomes;
	private Integer totalNumberOfSoldOffers;
	
	public ReportDto() {
		super();
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<ReportItemDto> getListOfReportItems() {
		return listOfReportItems;
	}

	public void setListOfReportItems(List<ReportItemDto> listOfReportItems) {
		this.listOfReportItems = listOfReportItems;
	}

	public Double getSumOfIncomes() {
		return sumOfIncomes;
	}

	public void setSumOfIncomes(Double sumOfIncomes) {
		this.sumOfIncomes = sumOfIncomes;
	}

	public Integer getTotalNumberOfSoldOffers() {
		return totalNumberOfSoldOffers;
	}

	public void setTotalNumberOfSoldOffers(Integer totalNumberOfSoldOffers) {
		this.totalNumberOfSoldOffers = totalNumberOfSoldOffers;
	}
	
	
	
	
}
