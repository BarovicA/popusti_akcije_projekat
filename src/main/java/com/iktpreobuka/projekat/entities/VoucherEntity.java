package com.iktpreobuka.projekat.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.projekat.security.Views;

@Entity
	public class VoucherEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@JsonView(Views.Public.class)
		private Integer id;
		
		@Column(nullable = false)
		@JsonView(Views.Administrator.class)
		private Boolean isUsed;
		
		@Column(nullable = false)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
		@JsonView(Views.Public.class)
		private LocalDate expirationDate;
		
		@JsonView(Views.Private.class)
		@ManyToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
		@JoinColumn(name="offer")
		private OfferEntity offer;
		
		@JsonView(Views.Private.class)
		@JsonProperty("buyer")
		@ManyToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
		@JoinColumn(name="user")
		private UserEntity user;

		public VoucherEntity() {
			super();
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Boolean getIsUsed() {
			return isUsed;
		}

		public void setIsUsed(Boolean isUsed) {
			this.isUsed = isUsed;
		}

		public LocalDate getExpirationDate() {
			return expirationDate;
		}

		public void setExpirationDate(LocalDate expirationDate) {
			this.expirationDate = expirationDate;
		}

		public OfferEntity getOffer() {
			return offer;
		}

		public void setOffer(OfferEntity offer) {
			this.offer = offer;
		}

		public UserEntity getUser() {
			return user;
		}

		public void setUser(UserEntity user) {
			this.user = user;
		}
		
		
	
	
	
}
