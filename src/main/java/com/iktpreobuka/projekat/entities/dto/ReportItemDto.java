package com.iktpreobuka.projekat.entities.dto;

import java.time.LocalDate;

public class ReportItemDto {

	private LocalDate date;
	
	private Double income;
	
	private Integer numberOfOffers;

	
	public ReportItemDto() {
		super();
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Integer getNumberOfOffers() {
		return numberOfOffers;
	}

	public void setNumberOfOffers(Integer numberOfOffers) {
		this.numberOfOffers = numberOfOffers;
	}
	
	
}
