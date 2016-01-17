package com.mycompany.servermail;

import Util.HibernateUtil;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author can
 */
public class ClientParse {
    
    public void ClientObject(){
        JSONObject obj = new JSONObject();
        obj.put("from", "ce.cankerem@gmail.com");
        obj.put("password", "mail-password");
        obj.put("to", "ce.cankerem@yandex.com");
        obj.put("content", "Napisen Bremini elemyon");
        obj.put("port", "587");
        obj.put("host", "smtp.gmail.com");
        
        try {
            FileWriter file = new FileWriter("/home/can/test.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
        //System.out.print(obj);        
    }
    
    public void Parser() throws org.json.simple.parser.ParseException{
        
        JSONParser parser = new JSONParser();
        
        try{
            Object obj = parser.parse(new FileReader("/home/can/test.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String from = (String) jsonObject.get("from");
            String password = (String) jsonObject.get("password");
            String to = (String) jsonObject.get("to");
            String content = (String) jsonObject.get("content");
            String port = (String) jsonObject.get("port");
            String host = (String) jsonObject.get("host");
            if(true){
                Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
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
            }else
                System.out.println("istek yok");
            
            //System.out.println(from);
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
}
