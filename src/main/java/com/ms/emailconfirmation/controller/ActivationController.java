package com.ms.emailconfirmation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.emailconfirmation.entity.ActivationToken;
import com.ms.emailconfirmation.model.SendMailRequest;
import com.ms.emailconfirmation.service.MailService;

@RestController
@RequestMapping("/activation")
public class ActivationController {

	@Autowired
	MailService mailService;

	@PostMapping("/sendmail")
	public ResponseEntity<ActivationToken> sendMail(@RequestBody SendMailRequest mailRequest) throws Exception {

		return mailService.sendMail(mailRequest);

	}

	@GetMapping("/verify/{encodedVerificationToken}")
	public ResponseEntity<String> verifyActivationMail(
			@PathVariable("encodedVerificationToken") String encodedVerificationToken) throws Exception {

		return mailService.verifyAccount(encodedVerificationToken);

	}

}
