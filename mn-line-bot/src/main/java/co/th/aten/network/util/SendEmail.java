
package co.th.aten.network.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

//    private final String mailhost = "smtp.live.com";// hotmail
    private final String mailhost = "smtp.gmail.com";// gmail

    public void sendMail(String subject, String body, final String sender, String recipients) throws Exception {

    	// Hotmail
//        Properties props = new Properties();
//        props.setProperty("mail.transport.protocol", "smtp");
//        props.setProperty("mail.host", mailhost);
//        props.setProperty("mail.smtp.auth", "true");
//        props.setProperty("mail.smtp.port", "587");
//        props.setProperty("mail.smtp.socketFactory.port", "587");
////        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
////        props.setProperty("mail.smtp.socketFactory.class", "HotmailSSLSocketFactory");
////        props.setProperty("mail.smtp.socketFactory.fallback", "false");
////        props.setProperty("mail.smtp.quitwait", "false");
//        props.setProperty("mail.smtp.starttls.enable", "true");
        
        // Gmail
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", mailhost);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender, "richtime"); // the astrick key is the password
//                    	return new PasswordAuthentication("atenpunk01@gmail.com", "066442100");
                    }
                });
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        message.setSender(new InternetAddress(sender));
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=utf-8");//text/plain

        message.setFrom(new InternetAddress(sender));
        if (recipients.indexOf(',') > 0) {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        } else {
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        }
        Transport.send(message);
    }

//    private String getPassWordMD5(String pass) {
//        byte[] defaultBytes = pass.getBytes();
//        try {
//            MessageDigest algorithm = MessageDigest.getInstance("MD5");
//            algorithm.reset();
//            algorithm.update(defaultBytes);
//            byte messageDigest[] = algorithm.digest();
//
//            StringBuffer hexString = new StringBuffer();
//            for (int i = 0; i < messageDigest.length; i++) {
//                String hex = Integer.toHexString(0xFF & messageDigest[i]);
//                if (hex.length() == 1) {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//            }
//            //*** Testausgabe
////            System.out.println("pass " + pass + "   md5 version is " + hexString.toString());
//            pass = hexString + "";
//        } catch (NoSuchAlgorithmException nsae) {
//            nsae.printStackTrace();
//        }
//        return pass;
//    }
    
    public static void main(String args[]) throws Exception {
		
    	SendEmail mailutils = new SendEmail();
        mailutils.sendMail("ทดสอบส่ง E-Mail", "Test send mail </br> ทดสอบส่งข้อความ", "richtimedata@gmail.com", "aten_punk@hotmail.com");

    }
}
