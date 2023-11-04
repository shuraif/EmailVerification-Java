package com.ms.emailconfirmation.service;

import java.time.LocalDateTime;
import java.util.Base64;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ms.emailconfirmation.entity.ActivationToken;
import com.ms.emailconfirmation.model.SendMailRequest;
import com.ms.emailconfirmation.repo.ActivationTokenRepo;
import com.ms.emailconfirmation.utils.CommonUtil;
import com.ms.emailconfirmation.utils.DateTimeUtil;

@Service
public class MailService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	ActivationTokenRepo tokenRepo;

	@Autowired
	ResourceLoader resourceLoader;

	public ResponseEntity<ActivationToken> sendMail(SendMailRequest mailRequest) throws Exception {

		ActivationToken token = new ActivationToken();

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		String verificationToken = CommonUtil.generateRandomString(120);

		token.setEmailId(mailRequest.getToEmail());
		token.setCreatedOn(LocalDateTime.now());
		token.setExpireOn(DateTimeUtil.addHoursToCurrentTime(mailRequest.getValidMinutes()));
		token.setTokenValue(verificationToken);
		token.setUserName(mailRequest.getUserName());

		token = tokenRepo.save(token);

		String encodedVerificationToken = Base64.getEncoder()
				.encodeToString((token.getTokenId().toString() + verificationToken).getBytes());

		String htmlContent = CommonUtil.readTemplate(resourceLoader, "activationToken.html");

		try {

			htmlContent = htmlContent.replace("${userName}", mailRequest.getUserName());
			htmlContent = htmlContent.replace("${encodedVerificationToken}", encodedVerificationToken);
			htmlContent = htmlContent.replace("${emailSignature}", "CyberSpace Ltd");

			helper.setText(htmlContent, true);
			helper.setTo(mailRequest.getToEmail());
			helper.setSubject("Confirm Your Email Address");
			helper.setFrom("expedireinnovations@gmail.com");
			mailSender.send(mimeMessage);

		} catch (Exception e) {
			System.out.println("Unable to send mail");
			e.printStackTrace();
			throw e;
		}
		
		return new ResponseEntity<>(token, HttpStatus.OK);

	}

	public ResponseEntity<String> verifyAccount(String encodedVerificationToken) throws Exception {

		ActivationToken token = null;

		String verificationTokenString = new String(Base64.getDecoder().decode(encodedVerificationToken));

		Long tokenId = Long.valueOf(verificationTokenString.substring(0, verificationTokenString.length() - 120));

		token = tokenRepo.findByTokenId(tokenId);

		String verificationToken = verificationTokenString.substring(verificationTokenString.length() - 120);

		if (null != token && null == token.getActivationStatus() && LocalDateTime.now().isBefore(token.getExpireOn())
				&& verificationToken.equals(token.getTokenValue())) {
			System.out.println("Token is verified");

			String htmlContent = CommonUtil.readTemplate(resourceLoader, "activationSuccess.html");
			htmlContent = htmlContent.replace("${userName}", token.getUserName());
			htmlContent = htmlContent.replace("${emailSignature}", "CyberSpace Ltd");

			token.setActivationStatus("Verified");
			tokenRepo.save(token);
			
			return new ResponseEntity<>(htmlContent, HttpStatus.OK);

		} else {

			String htmlContent = CommonUtil.readTemplate(resourceLoader, "activationFailure.html");
			if (null != token) {
				token.setActivationStatus("Failed");
				tokenRepo.save(token);

				htmlContent = htmlContent.replace("${userName}", token.getUserName());
			} else {
				htmlContent = htmlContent.replace("${userName}", "User");
			}
			htmlContent = htmlContent.replace("${emailSignature}", "CyberSpace Ltd");
			
			return new ResponseEntity<>(htmlContent, HttpStatus.OK);
		}

	}

}
