package com.backend.fundoo.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class MailServiceProvider {

	public static void sendEmail(String toEmail, String subject, String msg) {
		String username = "tejasdev92@gmail.com";
		String password = "Qaz@1234";
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		
		Authenticator auth = new Authenticator() 
		{
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		Session ses = Session.getInstance(prop, auth);
		send(ses, username, toEmail, subject, msg);
	}
	
	private static void send(Session session, String fromEmail, String toEmail, String subject, String msg) 
	{
		try 
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail, "Tejas Dev"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setText(msg);
			//log.info("Mime Message: "+message);
			Transport.send(message);
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
}
