package org.neuclear.senders;

import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.time.TimeTools;
import org.neudist.crypto.Base64;
import org.neudist.utils.NeudistException;
import org.neudist.utils.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 14, 2003
 * Time: 1:23:05 PM
 * $Id: LogSender.java,v 1.6 2003/09/26 23:53:10 pelle Exp $
 * $Log: LogSender.java,v $
 * Revision 1.6  2003/09/26 23:53:10  pelle
 * Changes mainly in receiver and related fun.
 * First real neuclear stuff in the payment package. Added TransferContract and PaymentReceiver.
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
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.2  2003/02/16 00:23:05  pelle
 * LogSender now works and there is a corresponding server side cgi script to do the logging in
 * http://neuclear.org/logger/ Site is not yet up but will be soon.
 *
 * Revision 1.1  2003/02/14 21:10:34  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
 * default receiver.
 *
 */
public class LogSender extends Sender {
    public void send(String endpoint, SignedNamedObject obj) throws NeudistException {
        try {
            String digest = URLEncoder.encode(Base64.encode(obj.getDigest().getBytes()), "UTF-8");
            String name = URLEncoder.encode(obj.getName(), "UTF-8");
            URL url = new URL(Utility.denullString(endpoint, LOGGER) + "?nohtml=1&name=" + name + "&digest=" + digest);
            url.openStream();

//            BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
//            String line=reader.readLine();
//            if (!line.substring(0,2).equals("OK")) // TODO We need to be able to sense if there is a real error
//                System.err.println("Error logging: "+line);
            //throw new NeudistException("Object wasn't logged");
        } catch (MalformedURLException e) {
            Utility.rethrowException(e);
        } catch (IOException e) {
            Utility.rethrowException(e);
        }

    }

    public static void main(String args[]) {
        try {
            logObject("neu://free");
            logObject("neu://free/pelle");
            logObject("neu://pelle");
            System.out.println("Object neu://free/pelle was logged at: " + getTimeStamp(NSResolver.resolveIdentity("neu://free/pelle")));
        } catch (NeudistException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    public static Timestamp getTimeStamp(String endpoint, byte rdigest[]) throws NeudistException {
        try {
            String digest = Base64.encode(rdigest);
//            System.out.println(digest);
            String encdigest = URLEncoder.encode(digest, "UTF-8");
            URL url = null;
            url = new URL(LOGGER + "?mode=Query&nohtml=1&digest=" + encdigest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = reader.readLine();
//            System.out.println(line);
            int pos = line.indexOf('\t');
            if (pos >= 0) {
                String stamp = line.substring(0, pos);
                return TimeTools.parseTimeStamp(stamp);
            }
        } catch (MalformedURLException e) {
            Utility.rethrowException(e);  //To change body of catch statement use Options | File Templates.
        } catch (IOException e) {
            Utility.rethrowException(e);  //To change body of catch statement use Options | File Templates.
        }

        return null;
    }

    public static Timestamp getTimeStamp(SignedNamedObject obj) throws NeudistException {
        return getTimeStamp(Utility.denullString(obj.getSignatory().getLogger(), LOGGER), obj.getDigest().getBytes());

    }

    private static void logObject(String name) throws NeudistException {
        System.out.print("Fetching...");
        SignedNamedObject obj = NSResolver.resolveIdentity(name);
        System.out.println("Got " + obj.getName());
        Sender log = new LogSender();
        System.out.print("Logging...");
        log.send(LOGGER, obj);
        System.out.println("Done");
    }

    public static final String LOGGER = "http://logger.neuclear.org/log.cgi";
}
