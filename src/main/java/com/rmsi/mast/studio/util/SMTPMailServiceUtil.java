package com.rmsi.mast.studio.util;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SMTPMailServiceUtil {

	public static void sendMail(String mailFrom,String mailTo,String subject,String msgBody) throws Exception {

		try {

			// getting config properties
			String host = ConfigurationUtil.getProperty("smtp.host");
			String smtpPort = ConfigurationUtil.getProperty("smtp.port");
			String username = ConfigurationUtil.getProperty("smtp.mail.username");
			String password = ConfigurationUtil.getProperty("smtp.mail.password");

			boolean isSmtpserver=Boolean.parseBoolean(ConfigurationUtil.getProperty("smtp.hostserver"));
			// Get system properties
			Properties properties = System.getProperties();

			// Setup mail server
			properties.setProperty("mail.smtp.host", host);
				
			if(!isSmtpserver){
				properties.put("mail.smtp.port", smtpPort);
				properties.put("mail.smtp.starttls.enable", "true");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.socketFactory.port",smtpPort);
				properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			}
			// setup mail port
			
			// Get the default Session object.
			Session session = Session.getDefaultInstance(properties);

			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set the RFC 822 "From" header field using the
			// value of the InternetAddress.getLocalAddress method.
			message.setFrom(new InternetAddress(mailFrom));

			// Add the given addresses to the specified recipient type.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));

			// Set the "Subject" header field.
			message.setSubject(subject);

			// Sets the given String as this part's content,
			// with a MIME type of "text/plain".
			//message.setText(msgBody);
			message.setSubject(subject, "UTF-8");
			message.setText(msgBody, "UTF-8");
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			//message.setContent(msgBody, "text/plain; charset=\"UTF-8\""); 

			if(isSmtpserver){
				// Send message
				Transport.send(message);
			}else{
				message.saveChanges();
				// connect to the transport
				Transport transport = session.getTransport("smtp");
				transport.connect(host, username, password); // host, user, password
				// send the msg and close the connection
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			}
		
			System.out.println("Message Send.....");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}