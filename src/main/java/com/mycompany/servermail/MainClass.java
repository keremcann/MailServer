package com.mycompany.servermail;

import Util.HibernateUtil;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author can
 */
public class MainClass {
    public static void main(String[] args) throws ParseException {
        
        Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
        ses.beginTransaction(); 
        
        JSONParser parser = new JSONParser();
        try{
            Object obj2 = parser.parse(new FileReader("/home/can/test.json"));
            JSONObject jsonObject = (JSONObject) obj2;
            
            String to = (String) jsonObject.get("to");
            String name = (String) jsonObject.get("name");
            String surname = (String) jsonObject.get("surname");
            String subject = (String) jsonObject.get("subject");
            String content = (String) jsonObject.get("content");
            
            MailSender run = new MailSender();
            run.sendEmailConfirmation(name, surname, to, subject, content);
        if(true){
            Messages inp = new Messages();
            inp.setSending(1);//gönderildi
            inp.setMessage(content);
            ses.save(inp);
            ses.getTransaction().commit();
            
        }
        else{
            Messages inp = new Messages();
            inp.setSending(0);//gönderilmedi
            inp.setMessage("");
            ses.save(inp);
            ses.getTransaction().commit();
        }
            //System.out.println(from);
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} 
        
    }
}
