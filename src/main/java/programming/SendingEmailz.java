package programming;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/*

 */
public class SendingEmailz {

    public static void main(String[] args){

        /*
        Sending a plain text and an HTML Email

        First, we need to configure the library with our email service provider's credentials. Then we'll create a Session that'll
        be used in constructing our message for sending.
         */
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mailtrap.io");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        // Now let’s move further by creating a session with our username and password
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return super.getPasswordAuthentication();
            }
        });

        try {
            // Now that we have a mail Session object, let’s create a MimeMessage for sending:
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("jv1019@nyu.edu"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("amadeus2194@gmail.com"));
            message.setSubject("First email!");

            // message
            String messageBody = "Hi, this is a message from Jeff's computer!";
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(messageBody, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);

        } catch ( MessagingException e){
            e.printStackTrace();
        }
    }

}
