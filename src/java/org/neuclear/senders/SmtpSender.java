package org.neuclear.senders;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 14, 2003
 * Time: 9:52:38 AM
 * $Id: SmtpSender.java,v 1.12 2003/11/11 21:18:43 pelle Exp $
 * $Log: SmtpSender.java,v $
 * Revision 1.12  2003/11/11 21:18:43  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.11  2003/11/10 17:42:36  pelle
 * The AssetController interface has been more or less finalized.
 * CurrencyController fully implemented
 * AssetControlClient implementes a remote client for communicating with AssetControllers
 *
 * Revision 1.10  2003/11/08 01:40:53  pelle
 * WARNING this rev is majorly unstable and will almost certainly not compile.
 * More major refactoring in neuclear-pay.
 * Got rid of neuclear-ledger like features of pay such as Account and Issuer.
 * Accounts have been replaced by Identity from neuclear-id
 * Issuer is now Asset which is a subclass of Identity
 * AssetController supports more than one Asset. Which is important for most non ecurrency implementations.
 * TransferRequest/Receipt and its Held companions are now SignedNamedObjects. Thus to create them you must use
 * their matching TransferRequest/ReceiptBuilder classes.
 * PaymentProcessor has been renamed CurrencyController. I will extract a superclass later to be named AbstractLedgerController
 * which will handle all neuclear-ledger based AssetControllers.
 *
 * Revision 1.9  2003/11/06 23:48:59  pelle
 * Major Refactoring of CurrencyController.
 * Factored out AssetController to be new abstract parent class together with most of its support classes.
 * Created (Half way) AssetControlClient, which can perform transactions on external AssetControllers via NeuClear.
 * Created the first attempt at the ExchangeAgent. This will need use of the AssetControlClient.
 * SOAPTools was changed to return a stream. This is required by the VerifyingReader in NeuClear.
 *
 * Revision 1.8  2003/10/25 00:39:54  pelle
 * Fixed SmtpSender it now sends the messages.
 * Refactored CommandLineSigner. Now it simply signs files read from command line. However new class IdentityCreator
 * is subclassed and creates new Identities. You can subclass CommandLineSigner to create your own variants.
 * Several problems with configuration. Trying to solve at the moment. Updated PicoContainer to beta-2
 *
 * Revision 1.7  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.6  2003/09/29 23:17:32  pelle
 * Changes to the senders. Now the senders only work with NamedObjectBuilders
 * which are the only NamedObject representations that contain full XML.
 *
 * Revision 1.5  2003/09/26 00:22:07  pelle
 * Cleanups and final changes to code for refactoring of the Verifier and Reader part.
 *
 * Revision 1.4  2003/09/24 23:56:48  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.3  2003/09/23 19:16:28  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:29  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.1  2003/02/14 21:10:35  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
 * default receiver.
 *
 */

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.commons.Utility;
import org.neuclear.xml.XMLException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class SmtpSender extends Sender {
    public SignedNamedObject send(String endpoint, NamedObjectBuilder obj) throws NeuClearException {
        Properties props = System.getProperties();
        if (endpoint.startsWith("mailto:"))
            endpoint = endpoint.substring(7);
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
            msg.setSubject("You have received a New Named Object: " + obj.getName());
            BodyPart body = new MimeBodyPart();
            body.setText("This message contains a signed named object. Please see http://neuclear.org for more info.");

            Multipart multi = new MimeMultipart();
            multi.addBodyPart(body);
            BodyPart objpart = new MimeBodyPart();
            objpart.setText(obj.asXML());
            objpart.setHeader("Content-type", "application/nsdl");
            multi.addBodyPart(objpart);
            msg.setContent(multi);

            msg.setHeader("X-Mailer", "NeuDist Framework");
            msg.setSentDate(new Date());

            // -- Send the message --
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            Utility.rethrowException(e);
        } catch (XMLException e) {
            e.printStackTrace();
            Utility.rethrowException(e);
        }

        return null;// We never receive a response
    }
}
