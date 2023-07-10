package com.iktpreobuka.projekat.services;

import java.time.LocalDate;
import java.util.List;

import com.iktpreobuka.projekat.entities.dto.ReportDto;
import com.iktpreobuka.projekat.entities.dto.ReportItemDto;

public interface ReportService {

	public ReportDto generateReportByDatesBetweenAndCategory(LocalDate startDate, LocalDate endDate, Integer categoryId);

	public ReportDto generateReportByDatesBetween(LocalDate startDate, LocalDate endDate);

	
}
