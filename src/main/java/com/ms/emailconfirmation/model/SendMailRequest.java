package com.ms.emailconfirmation.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SendMailRequest {
	
	String toEmail;
	Integer validMinutes;
	String userName;

}
