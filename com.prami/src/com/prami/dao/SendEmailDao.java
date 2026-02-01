package com.prami.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmailDao {

	public Boolean sendMail(String userEmail, String emailType) {
		Boolean isMailSent = false;
		String htmlText = "";
		AuthDAO auth= new AuthDAO();
		final String username = "pramitrends@gmail.com";
		final String password = "Critic#123";
		final String host_name = "smtp.gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host_name);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("contactus@prami.in"));
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(userEmail));
			
			MimeMultipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();

			//Set key values
			Map<String, String> input = new HashMap<String, String>();

			switch (emailType) {
			case "forgot_password":
				HashMap<String, String> profile = auth.generatePassword(userEmail);
				input.put("new-password", profile.get("password"));
				message.setSubject("Forgot Password?");
				htmlText = readEmailFromHtml("/var/www/email-template/reset-password.html",input);
				break;
			case "welcome_male":
				message.setSubject("Welcome to Prami.in!");
				 //htmlText = readEmailFromHtml("/front/static/email-template/welcome_men.html",input);
				 htmlText = readEmailFromHtml("/var/www/email-template/welcome_men.html",input);
				break;
			case "welcome_female":
				message.setSubject("Welcome to Prami.in!");
				//htmlText = readEmailFromHtml("/front/static/email-template/welcome_female.html",input);
				htmlText = readEmailFromHtml("/var/www/email-template/welcome_female.html",input);
				break;
			default:
				message.setSubject("Welcome to Prami.in!");
				//htmlText = readEmailFromHtml("/front/static/email-template/welcome_female.html",input);
				htmlText = readEmailFromHtml("/var/www/email-template/general_welcome_email.html",input);
				break;
			}
			
			messageBodyPart.setContent(htmlText, "text/html");

			multipart.addBodyPart(messageBodyPart); 
			message.setContent(multipart);

			//Conect to smtp server and send Email
			Transport transport = session.getTransport("smtp");            
			transport.connect(host_name, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Mail sent successfully..."); 

			isMailSent = true;

		} catch (MessagingException e) {
			isMailSent = false;
			e.printStackTrace();
			System.out.println("failed at SendEmail --> sendMail"+e.getMessage());
		}
		return isMailSent;
	}
	//Method to replace the values for keys
		protected static String readEmailFromHtml(String filePath, Map<String, String> input)
		{
		    String msg = readContentFromFile(filePath);
		    try
		    {
		    Set<Entry<String, String>> entries = input.entrySet();
		    for(Map.Entry<String, String> entry : entries) {
		        msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
		    }
		    }
		    catch(Exception exception)
		    {
		        exception.printStackTrace();
		    }
		    return msg;
		}
		//Method to read HTML file as a String 
		private static String readContentFromFile(String fileName)
		{
		    StringBuffer contents = new StringBuffer();
		    
		    try {
		      //use buffering, reading one line at a time
		      BufferedReader reader =  new BufferedReader(new FileReader(fileName));
		      try {
		        String line = null; 
		        while (( line = reader.readLine()) != null){
		          contents.append(line);
		          contents.append(System.getProperty("line.separator"));
		        }
		      }
		      finally {
		          reader.close();
		      }
		    }
		    catch (IOException ex){
		      ex.printStackTrace();
		    }
		    return contents.toString();
		}
	
	
}