# Email Account Confirmation

This Spring Boot application demonstrates account confirmation through email.

## Table of Contents

- [Overview](#overview)
- [Endpoints](#endpoints)
  - [Send Confirmation Email](#send-confirmation-email)
  - [Verify Email](#verify-email)
- [Configuration](#configuration) 
- [Next Steps](#next-steps)

## Overview

The application has two main features:

1. Sending a confirmation email with a verification token to a new user's email address
2. Verifying the token when the user clicks on the link in the email

It uses Spring Mail to send emails via Gmail SMTP. User data and tokens are stored in an H2 database.

## Endpoints

### Send Confirmation Email

Sends an email with a verification token to confirm the user's email address. 

**URL** : `/activation/sendmail`

**Method** : `POST`

**Request Body**

```json
{
  "toEmail": "sample@gmail.com",
  "validMinutes": 5,
  "userName": "User"  
}
```

**Response**

```json
{
  "tokenId": 1,
  "emailId": "sample@gmail.com",
  "tokenValue": "2SwCHjABNPb0Vb7yqeddLvmpZEubJwU7bV7cZ148FlxnOENQLrUvPOLLap22AxpvpIO68oL5PqibvHuG2zlpBXU3ILjuTAXjP07VJqBxjHRjp41huTdJg9PO",
  "createdOn": "2023-11-05T00:03:24.158",
  "expireOn": "2023-11-05T00:08:24.159",
  "userName": "User",
  "activationStatus": null
}  
```

### Verify Email

Verifies the token from the confirmation email link.

**URL** : `/verify/{encodedVerificationToken}`

**Method** : `GET` 

**Response** : 200 OK if verification successful, 404 Not Found if invalid token

## Configuration

The Spring Mail properties are configured in \`application.properties\`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.username=myapp@gmail.com
spring.mail.password=password
```

The H2 database configuration is also defined in \`application.properties\`.

  
## Next Steps 

- Integrate with actual user registration flow
- More robust error handling
- Logging and monitoring
```
