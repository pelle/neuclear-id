package org.neuclear.senders;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 14, 2003
 * Time: 9:52:38 AM
 * $Id: SmtpSender.java,v 1.1 2003/09/19 14:41:29 pelle Exp $
 * $Log: SmtpSender.java,v $
 * Revision 1.1  2003/09/19 14:41:29  pelle
 * Initial revision
 *
 * Revision 1.1  2003/02/14 21:10:35  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The NamedObject has a new log() method that logs it's contents at it's parent NameSpace's logger.
 * The NameSpace object also has a new method send() which allows one to send a named object to the NameSpace's
 * default receiver.
 *
 */

import org.neuclear.id.NamedObject;
import org.neuclear.utils.NeudistException;
import org.neuclear.utils.Utility;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class SmtpSender extends Sender {
    public void send(String endpoint, NamedObject obj) throws NeudistException {
        Properties props = System.getProperties();
        if (endpoint.startsWith("mailto:"))
            endpoint=endpoint.substring(7);
        // -- Attaching to default Session, or we could start a new one --

        props.put("mail.smtp.host", "neuclear.org");// TODO Remove this hardcoded mail server
        Session session = Session.getDefaultInstance(props, null);

        try {
            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("pelle@neuclear.org"));// TODO Remove this hardcoded email
            msg.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(endpoint, false));

            // -- We could include CC recipients too --
            // if (cc != null)
            // msg.setRecipients(Message.RecipientType.CC
            // ,InternetAddress.parse(cc, false));

            // -- Set the subject and body text --
            msg.setSubject("You have received a New Named Object: "+obj.getName());
            BodyPart body=new MimeBodyPart();
            body.setText("This message contains a signed named object. Please see http://neuclear.org for more info.");

            Multipart multi=new MimeMultipart();
            multi.addBodyPart(body);
            BodyPart objpart=new MimeBodyPart();
            objpart.setText(obj.asXML());
            objpart.setHeader("Content-type","application/nsdl");
            multi.addBodyPart(objpart);
            msg.setContent(multi);

            msg.setHeader("X-Mailer", "NeuDist Framework");
            msg.setSentDate(new Date());

            // -- Send the message --
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            Utility.rethrowException(e);
        }


    }
}
