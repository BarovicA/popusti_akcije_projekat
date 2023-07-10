package com.iktpreobuka.projekat.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.projekat.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {
	
	public enum UserRole {ROLE_CUSTOMER, ROLE_ADMIN, ROLE_SELLER};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	protected Integer id;
	@Column(nullable = false)
	@JsonView(Views.Private.class)
	protected String firstName;
	@Column(nullable = false)
	@JsonView(Views.Private.class)
	protected String lastName;
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	protected String username;
	@Column(nullable = false)
	@JsonIgnore
	protected String password;
	@JsonView(Views.Private.class)
	@Column(nullable = false)
	protected String email;
	@Column(nullable = false)
	@JsonView(Views.Administrator.class)
	protected UserRole userRole;
	@Version
	private Integer version;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "user",
			cascade = CascadeType.REFRESH, fetch =FetchType.LAZY)
	@JsonView(Views.Private.class)
	private List<OfferEntity> offers = new ArrayList<OfferEntity>();

	@JsonView(Views.Private.class)
	@JsonIgnore
	@OneToMany(mappedBy="user", cascade= {CascadeType.ALL}, fetch=FetchType.LAZY)
	private List<BillEntity> bills;
	
	@JsonView(Views.Private.class)
	@JsonIgnore
	@OneToMany(mappedBy="user", cascade= {CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<VoucherEntity> vouchers;

	
	public List<OfferEntity> getOffers() {
		return offers;
	}

	public void setOffers(List<OfferEntity> offers) {
		this.offers = offers;
	}

	public List<BillEntity> getBills() {
		return bills;
	}

	public void setBills(List<BillEntity> bills) {
		this.bills = bills;
	}

	public UserEntity() {
		super();
	}

	public UserEntity(Integer id, String firstName, String lastName, String password, String email, UserRole userRole) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
	
	

}
