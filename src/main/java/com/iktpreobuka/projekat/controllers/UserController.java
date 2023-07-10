package com.iktpreobuka.projekat.controllers;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.projekat.controllers.util.RESTError;
import com.iktpreobuka.projekat.entities.UserEntity;
import com.iktpreobuka.projekat.entities.UserEntity.UserRole;
import com.iktpreobuka.projekat.repositories.UserRepository;
import com.iktpreobuka.projekat.security.Views;


@RestController
@RequestMapping("/project/users")
public class UserController {
	

	List<UserEntity> users = new ArrayList<>();
	
	@Autowired
	public UserRepository userRepository;

	@JsonView(Views.Public.class)
	@GetMapping("/public")
	public ResponseEntity<?> getAllPublic() {
		try {
			Iterable<UserEntity> users = userRepository.findAll();
			if (users == null) {
				return new ResponseEntity<RESTError>(new RESTError(1, "No users found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Iterable<UserEntity>>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@JsonView(Views.Private.class)
	@GetMapping("/private")
	public ResponseEntity<?> getAllPrivate() {
		try {
			Iterable<UserEntity> users = userRepository.findAll();
			if (users == null) {
				return new ResponseEntity<RESTError>(new RESTError(1, "No users found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Iterable<UserEntity>>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@JsonView(Views.Administrator.class)
	@GetMapping("/admin")
	public ResponseEntity<?> getAllAdmin() {
		try {
			Iterable<UserEntity> users = userRepository.findAll();
			if (users == null) {
				return new ResponseEntity<RESTError>(new RESTError(1, "No users found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Iterable<UserEntity>>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewCustomerUser(@RequestBody UserEntity user) {
		try {
			UserEntity users = new UserEntity();
			users.setFirstName(user.getFirstName());
			users.setLastName(user.getLastName());
			users.setEmail(user.getEmail());
			users.setUsername(user.getUsername());
			users.setPassword(user.getPassword());
			users.setUserRole(UserRole.ROLE_CUSTOMER);
			userRepository.save(users);
			return new ResponseEntity<UserEntity>(users, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{Id}")
	public ResponseEntity<?> getById(@PathVariable Integer Id) {
		UserEntity user = userRepository.findById(Id).orElse(null);
		if (user != null) {
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
		try {
			UserEntity user = userRepository.findById(id).orElse(null);
			if (user != null) {
				if (updatedUser.getFirstName() != null)
					user.setFirstName(updatedUser.getFirstName());
				if (updatedUser.getLastName() != null)
					user.setLastName(updatedUser.getLastName());
				if (updatedUser.getUsername() != null && !user.getUsername().equals(updatedUser.getUsername()))
					user.setUsername(updatedUser.getUsername());
				if (updatedUser.getEmail() != null && !user.getEmail().equals(updatedUser.getEmail()))
					user.setEmail(updatedUser.getEmail());
			}
			userRepository.save(user);
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/change/{id}/role/{role}")
	public ResponseEntity<?> updateUserRole(@PathVariable Integer id, @PathVariable UserRole role) {
		try {
			UserEntity user = userRepository.findById(id).orElse(null);
			if (user != null && !user.getUserRole().equals(role))
				user.setUserRole(role);
			userRepository.save(user);
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/changePassword/{id}")
	public ResponseEntity<?> updateUserPassword(@RequestParam String oldPass, @RequestParam String newPass,
			@PathVariable Integer id) {
		UserEntity user = userRepository.findById(id).orElse(null);
		if (user != null && user.getPassword().equals(oldPass)) {
			user.setPassword(newPass);
			userRepository.save(user);
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(1, "User not found or old password doesn't match"),
				HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		UserEntity user = userRepository.findById(id).orElse(null);
		if (user != null) {
			userRepository.delete(user);
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/by-username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
		UserEntity user = userRepository.findByUsername(username);
		if (user != null) {
			return new ResponseEntity<UserEntity>(userRepository.findByUsername(username), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
	}

}
	
	
	
//	@JsonView(Views.Public.class)
//	@GetMapping("/public")
//	public Iterable<UserEntity> getAllPublic(){
//		return userRepository.findAll();	
//	}
//	
//	@JsonView(Views.Private.class)
//	@GetMapping("/private")
//	public Iterable<UserEntity> getAllPrivate(){
//		return userRepository.findAll();	
//	}
//	
//	@JsonView(Views.Administrator.class)
//	@GetMapping("/admin")
//	public Iterable<UserEntity> getAllAdmin(){
//		return userRepository.findAll();	
//	}
	
	
	
//	@RequestMapping(method = RequestMethod.POST)
//	public UserEntity addNewCustomerUser(@RequestParam String firstName,
//			@RequestParam String lastName, @RequestParam String email, 
//			@RequestParam String username, @RequestParam String password) {
//		UserEntity users = new UserEntity();
//		users.setFirstName(firstName);
//		users.setLastName(lastName);
//		users.setEmail(email);
//		users.setUsername(username);
//		users.setPassword(password);
//		users.setUserRole(UserRole.ROLE_CUSTOMER);
//		return userRepository.save(users);	
//	}
	
	
//	@RequestMapping(method = RequestMethod.GET, path="/{Id}")
//	public UserEntity getById(@PathVariable Integer Id) {
//		return userRepository.findById(Id).orElse(null);	
//	}
	
	
//	// Azuriraj korisnika
//		@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
//		public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
//			
//			UserEntity user = userRepository.findById(id).orElse(null);
//			if(user != null) {
//				if (updatedUser.getFirstName() != null )
//					user.setFirstName(updatedUser.getFirstName());
//				if (updatedUser.getLastName() != null )
//					user.setLastName(updatedUser.getLastName());
//				if (updatedUser.getUsername() != null && !user.getUsername().equals(updatedUser.getUsername()))
//					user.setUsername(updatedUser.getUsername());
//				if (updatedUser.getEmail() != null && !user.getEmail().equals(updatedUser.getEmail()))
//					user.setEmail(updatedUser.getEmail());
//			}
//			userRepository.save(user);
//			return user;
//		}
	
//	 * 1.7 kreirati REST endpoint koji omogućava izmenu atributa user_role
//	 * postojećeg korisnika putanja /project/users/change/{ role/{role} ukoliko je
//	 * prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null ,
//	 * a u suprotnom vraća podatke korisnika sa izmenjenom vrednošću atributa user
//	 * role
//	 */
	
//		// Promeni ulogu korisnika
//		@RequestMapping(method = RequestMethod.PUT, value = "/change/{id}/role/{role}")
//		public UserEntity updateUserRole(@PathVariable Integer id, @PathVariable UserRole role) {
//			UserEntity user = userRepository.findById(id).orElse(null);
//			if (user != null && !user.getUserRole().equals(role))
//				user.setUserRole(role);
//			userRepository.save(user);
//			return user;
//		}
	
	//1.8 kreirati REST endpoint koji omogućava izmenu 
	//vrednosti atributa password postojećeg korisnika•putanja/project/ changePassword /
	//{kao RequestParam proslediti vrednosti stare i nove lozinke
	//ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda
	//treba da vrati null , a u suprotnom vraća podatke korisnika sa izmenjenom vrednošću atributa password
	//NAPOMENA : da bi vrednost atributa password mogla da bude zamenjena sa novom vrednošću, neophodno je da 
	//se vrednost stare lozinke korisnika poklapa sa vrednošću stare lozinke prosleđene kao RequestParam
//	 	*/

	
//		@RequestMapping(method = RequestMethod.PUT, value = "/changePassword/{id}")
//		public UserEntity updateUserPassword(@RequestParam String oldPass, @RequestParam String newPass, @PathVariable Integer id) {
//			
//			UserEntity user = userRepository.findById(id).orElse(null);
//			if (user != null && user.getPassword().equals(oldPass)) {
//				user.setPassword(newPass);
//			}
//			
//			userRepository.save(user);
//			return user;
//		}
//	
	///* 1.9 Kreirati Rest endpoint koji omogucava brisanje postojeceg korisnika 
	// * putanja /project/users/{id}
	// * Ukoliko je prosledjen ID koji ne pripada nijednom korisniku metoda treba da vrati null,
	// * a u suprotnom vraca podatke o korisnuku
	// * koji je obrisan
	// * 
	// */
//		@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
//		public UserEntity deleteUser(@PathVariable Integer id) {
//			
//			UserEntity user = userRepository.findById(id).orElse(null);
//			if (user != null)
//				userRepository.delete(user);
//			
//			return user;
//		}
//	
//	
////1.10 kreirati REST endpoint koji vraca korisnika po vrednosti prosledjenog username-a
////putanja  /project/users/by-username/{username}
////u slucaju da ne postoji korisnik sa trazenim usernameom vrati null
//	
//		@GetMapping(value = "/by-username/{username}")
//		public UserEntity getUserByUsername(@PathVariable String username) {
//			UserEntity user = userRepository.findByUsername(username);
//			if (user != null) {
//			return userRepository.findByUsername(username);
//			}
//			return null;
//		}
//	
//	@PutMapping(path = "/changeRole/{id}")
//	public UserEntity changeUserRole(@PathVariable Integer id, @RequestParam UserRole role ) {
//		UserEntity user = userRepository.findById(id).orElse(null);
//		if (user != null) {
//			user.setUserRole(role);
//		}
//		return userRepository.save(user);
//		
//	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	
	
//
//	public List<UserEntity> getDB() {
//		ArrayList<UserEntity> users = new ArrayList<UserEntity>();
//		UserEntity u1 = (new UserEntity(1, "Vladimir", "Dimitrieski", "123123", "vlado@gmail.com",
//				UserRole.ROLE_CUSTOMER));
//		UserEntity u2 = (new UserEntity(2, "Nebojsa", "Horvat", "passowrd", "horva.n@uns.ac.rs",
//				UserRole.ROLE_CUSTOMER));
//		UserEntity u3 = (new UserEntity(3, "Aleksandar", "Ignjatijevic", "qwerty123", "a.ignjatijevic@gmail.com",
//				UserRole.ROLE_CUSTOMER));
//		UserEntity u4 = (new UserEntity(4, "Aleksa ", "Jeremic", "4321", "a.jere@gmail.com", UserRole.ROLE_CUSTOMER));
//		users.add(u1);
//		users.add(u2);
//		users.add(u3);
//		users.add(u4);
//		return users;
//	}



