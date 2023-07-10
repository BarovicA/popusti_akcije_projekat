package com.iktpreobuka.projekat.repositories;



import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.projekat.entities.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

	//Iterable<CategoryEntity> findAllByCategory(Integer Id);


	
}
