package com.iktpreobuka.projekat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.projekat.entities.CategoryEntity;
import com.iktpreobuka.projekat.repositories.CategoryRepository;
import com.iktpreobuka.projekat.repositories.OfferRepository;

@RestController
@RequestMapping("/project/categories")
public class CategoryController {

	@Autowired
	public CategoryRepository categoryRepository;

	@Autowired
	public OfferRepository offerRepository;

	public OfferRepository getOfferRepository() {
		return offerRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<CategoryEntity> getAll() {
		return categoryRepository.findAll();
	}

//	private List<CategoryEntity> getDB() {
//	ArrayList<CategoryEntity> categories = new ArrayList<CategoryEntity>();
//	CategoryEntity c1 = (new CategoryEntity(1, "music", "description 1"));
//	CategoryEntity c2 = (new CategoryEntity(2, "food", "description 2"));
//	CategoryEntity c3 = (new CategoryEntity(3, "entertainment", "description 3"));
//	CategoryEntity c4 = (new CategoryEntity(4, "gaming", "description 4"));

	@RequestMapping(method = RequestMethod.POST)
	public CategoryEntity Categorys(@RequestBody CategoryEntity newCategory) {
		CategoryEntity category = new CategoryEntity();
		category.setCategoryDescription(newCategory.getCategoryDescription());
		category.setCategoryName(newCategory.getCategoryName());
		return categoryRepository.save(category);

	}
//	
//	@RequestMapping(method = RequestMethod.PUT , path = "/{Id}")
//	public CategoryEntity updateCategory(@PathVariable Integer Id,@RequestBody CategoryEntity updateCategots) {
//		CategoryEntity category = categoryRepository.findById(Id).get();
//		category.setCategoryDescription(updateCategots.getCategoryDescription());
//		category.setCategoryName(updateCategots.getCategoryName());
//		categoryRepository.save(category);
//		return updateCategots;
//		
//	}

	@PutMapping(path = "/{id}")
	public CategoryEntity updateCategory(@RequestBody CategoryEntity updatedCategory, @PathVariable Integer id) {
		CategoryEntity category = categoryRepository.findById(id).orElse(null);
		if (category != null) {

			if (updatedCategory.getCategoryName() != null) {
				category.setCategoryName(updatedCategory.getCategoryName());
				if (updatedCategory.getCategoryDescription() != null) {
					category.setCategoryDescription(updatedCategory.getCategoryDescription());
				}
			}
			return categoryRepository.save(category);
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{Id}")
	public CategoryEntity deleteCategory(@PathVariable Integer Id) {
		CategoryEntity category = categoryRepository.findById(Id).orElse(null);
		if (category != null) {
			categoryRepository.delete(category);
			return category;
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{Id}")
	public CategoryEntity getById(@PathVariable Integer Id) {
		return categoryRepository.findById(Id).orElse(null);
	}
	

//	private List<CategoryEntity> getDB() {
//		ArrayList<CategoryEntity> categories = new ArrayList<CategoryEntity>();
//		CategoryEntity c1 = (new CategoryEntity(1, "music", "description 1"));
//		CategoryEntity c2 = (new CategoryEntity(2, "food", "description 2"));
//		CategoryEntity c3 = (new CategoryEntity(3, "entertainment", "description 3"));
//		CategoryEntity c4 = (new CategoryEntity(4, "gaming", "description 4"));
//
//		categories.add(c1);
//		categories.add(c2);
//		categories.add(c3);
//		categories.add(c4);
//
//		return categories;
//	}
//
//	@RequestMapping(method = RequestMethod.GET)
//	public List<CategoryEntity> getAll() {
//		List<CategoryEntity> categories = getDB();
//		return categories;
//
//	}
//	/*
//	 * 2.4 Kreirati rest endpoint koji omogucava dodavanje nove kategorije putanja
//	 * /project/categories metoda treba da vrati dodatu kategoriju
//	 */
//
//	@RequestMapping(method = RequestMethod.POST)
//	public CategoryEntity novaKategorija(@RequestBody CategoryEntity category) {
//
//		return category;
//	}
//
//	/*
//	 * 2.5 Kreirati rest endpoint koji omogucava izmenu postojece kategorije putanja
//	 * /project/categories/{id}
//	 * 
//	 */
//
//	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
//	public CategoryEntity changeCat(@PathVariable("id") String id, @RequestBody CategoryEntity category) {
//		for (CategoryEntity categorys : getDB()) {
//			if (categorys.getId().toString().equals(id)) {
//				categorys.setCategoryDescription(category.getCategoryDescription());
//				categorys.setCategoryName(category.getCategoryName());
//				return categorys;
//			}
//		}
//		return null;
//
//	}
//
//	/*
//	 * 2.6 kreirati REST endpoint koji omogucava brisanje postojece kategorije
//	 * putanja /project/categories/{id} ukoliko je prosledjen ID koji ne pripada
//	 * nijednoj kategoriji metoda treba da vrati null, a u suprotnom vraca podatke o
//	 * kategoriji koja je obrisana
//	 */
//
//	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
//	public CategoryEntity deleteCategory(@PathVariable String id) {
//		for (CategoryEntity categorys : getDB()) {
//			if (categorys.getId().toString().equals(id)) {
//			List<CategoryEntity> deleteCat = getDB();
//			deleteCat.remove(categorys);
//			return categorys;
//			}
//
//		}
//		return null;
//
//	}
///*2.7 Kreirati REST endpoint koji vraca kategoriju po vrednosti
// * prosledjenoj id-a putanja/project/categories/{id}
// * u slucaju da ne postoji kategorija sa trazenom vrednoscu ID-a vratiti null
// */
//	@RequestMapping(method = RequestMethod.GET, value ="/{id}")
//	public CategoryEntity getCategory(@PathVariable String id) {
//		for (CategoryEntity categorys : getDB()) {
//			if(categorys.getId().toString().equals(id)) {
//				return categorys;
//			}
//		}
//		return null;
//	}

}
