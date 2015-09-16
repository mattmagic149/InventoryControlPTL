package utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.criterion.Criterion;

import database.Product;
import database.User;

public class Email {
	private static final boolean REDIRECT_INTO_TEXTFILE = false;
	
	private static final String heading = "<h1><span style=\"color:blue\">PTL</span>-Logistik</h1>";

	// Provider-Parameter
	private static final String emailid = "ptl_test@yahoo.com";
	private static final String username = "ptl_test@yahoo.com";
	private static final String host = "smtp.mail.yahoo.com";
	private static final String port = "465";
	private static final String password = "Welcome2Hell";
	
	//*************************************************************************************************
	// sendRegisterMail:
	// sends a message about successful registration.
	//
	public static boolean sendQuantityReminder(Product product, long quantity) {
		
		List<User> users = HibernateSupport.readMoreObjects(User.class, new ArrayList<Criterion>());
		User user;
		
		String subject = product.getName() + " Nachbestellen";		
		String text = "";			
		
		boolean success = true;
		for(int i = 0; i < users.size(); i++) {
			user = users.get(i);
			if(user.getPermission() == User.Permission.ADMIN) {
				
				text = "Lieber " + user.getFirstname() + ",\n\n"
						+ "Das Produkt: " + product.getName() + " liegt bei " + quantity
						+ " " + product.getUnity().getName() + "\n\n"
						+ "Bitte nachbestellen.\n\n"
						+ "Noch einen schönen Tag.";
				
				if(REDIRECT_INTO_TEXTFILE) {
					success = success == false ? false : writeTextfile(user.getEmail(), subject, text);
				} else {
					success = success == false ? false : sendSingleEmail(user.getEmail(),subject,text);
				}
			}
		}

		return success;
		
	}

	//*************************************************************************************************
	// writeTextfile:
	// simulates an email-sending by writing into a text-file.
	//
	private static boolean writeTextfile(String email, String subject, String text){
		
		boolean success = false;
		
		File path = new File("sent_emails");
				
		
		try{
			path.mkdir();
			File file = new File(path+"/email_to_"+email.replace('.', '_')+".txt");
		    PrintWriter writer = new PrintWriter(file);
		    
		    
		    writer.println("email to:"+email+ "   "+ (new Date()).toString());
		    writer.println();
		    writer.println("Subject: "+subject); 
		    writer.println();
		    writer.println(text);
		       
		    writer.flush();
		    writer.close();
		    success = true;
		   
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		return success;
		
	}
	
	
	//*************************************************************************************************
	// sendSingleEmail:
	// sends ONE email including the submitted text.
	//
	private static boolean sendSingleEmail(String email, String subject, String text){
		Transport transport = startEmailTransport();
		if(transport!=null && sendEmail(email,subject,text,transport) && commitEmailTransport(transport))
			return true;
		else
			return false;		
	}

	
	//*************************************************************************************************
	// sendEmail:
	// sends an email including the submitted text.
	//
	private static boolean sendEmail(String email, String subject, String text,Transport transport){
		boolean success = false;
		Session session=null;
		
		// convert to html
		text=text.replaceAll("(\r\n|\n)", "<br>");		
		
		try{
	
			MimeMessage message = new MimeMessage(session);
	 
	        message.setFrom(new InternetAddress(emailid,username));
	        message.setSentDate(new Date()); 
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	        message.setSubject(subject,"utf-8");
	        message.setContent(heading +"<p>"+ text + "</p>", "text/html; charset=UTF-8");
	        
//	       transport = session.getTransport("smtps");
//	       transport.connect(); 
	       transport.sendMessage(message, message.getAllRecipients());
//	       transport.close();
	       success = true;
	       
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
        return success;
	}
	
	
	
	private static Transport startEmailTransport() {
	    
		Properties properties = System.getProperties();
		properties.put("mail.smtps.host", host);
		properties.put("mail.smtps.auth", "true");
		properties.put("mail.debug", "false");
		properties.put("mail.smtps.port", port);
		properties.put("mail.smtp.starttls.enable", "true");
		  
	     Session session = Session.getInstance(properties,new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
      
	     Transport transport;
	     try {
	    	 transport = session.getTransport("smtps");
	     } catch (NoSuchProviderException e) {
	    	 e.printStackTrace();
	    	 return null;
	     }
	     
	     try {
	    	 transport.connect();
	     } catch (MessagingException e) {
	    	 e.printStackTrace();
	    	 return null;
	     } 

	     return transport;
	}
  
	
	private static boolean commitEmailTransport(Transport transport) {
		try {
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
  
}
