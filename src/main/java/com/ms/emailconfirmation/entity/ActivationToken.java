package com.ms.emailconfirmation.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name="ActivationTokens")
public class ActivationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tokenId;
	  
	String emailId;
	String tokenValue;
	LocalDateTime createdOn;
	LocalDateTime expireOn;
	String userName;
	
	@JsonIgnore
	String activationStatus; 
	
}
