package com.mycompany.servermail;

import Util.HibernateUtil;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.hibernate.Session;
/**
 *
 * @author can
 */
public class MailSender extends Authenticator {
    
    public static void sendEmailConfirmation(String name, String surname, String email, String subject, String content) throws ParseException {

        JSONParser parser = new JSONParser();
        
        try{
            Object obj2 = parser.parse(new FileReader("/home/can/test.json"));
            JSONObject jsonObject = (JSONObject) obj2;
            String from = (String) jsonObject.get("from");
            String password = (String) jsonObject.get("password");
            String to = (String) jsonObject.get("to");          
            String port = (String) jsonObject.get("port");
            String host = (String) jsonObject.get("host");
            
//            ClientParse runn = new ClientParse();
//            runn.Parser();
             org.hibernate.Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
             ses.beginTransaction();
             
             Server input = new Server();
             
             input.setFrom(from);
             input.setPassword(password);
             input.setTo(to);
             input.setPort(port);
             input.setServer(host);
             input.setContent(content);

            ses.save(input);
            ses.getTransaction().commit();
                
            final String username = from;
            final String fromm = from;
            final String passwordd = password;
            final String mail = to;
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, passwordd);
                }
            });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromm));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
                message.setSubject(subject);
                // message.setText();
                message.setContent("Sayın " + name + " " + surname
                            + ",<br><br>Metin Gövdesi" + "<br><br>" + content,
                            "text/html; charset=utf-8");
                Transport.send(message);
                System.out.println("Mail Gönderildi");
                
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
        
            //System.out.println(from);
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
}
