package com.iktpreobuka.projekat.repositories;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.projekat.entities.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer> {

	Iterable<VoucherEntity> findByUserId(Integer userId);

	Iterable<VoucherEntity> findByOfferId(Integer offerId);

	Iterable<VoucherEntity> findByExpirationDateGreaterThanEqual(LocalDate date);

}
