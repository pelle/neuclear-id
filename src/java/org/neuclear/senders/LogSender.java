package org.neuclear.senders;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.Base64;
import org.neuclear.commons.time.TimeTools;
import org.neuclear.id.SignedNamedObject;

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
 * $Id: LogSender.java,v 1.12 2003/11/21 04:45:13 pelle Exp $
 * $Log: LogSender.java,v $
 * Revision 1.12  2003/11/21 04:45:13  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.11  2003/11/19 23:33:59  pelle
 * Signers now can generatekeys via the generateKey() method.
 * Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
 * SignedNamedObject now contains the full xml which is returned with getEncoded()
 * This means that it is now possible to further send on or process a SignedNamedObject, leaving
 * NamedObjectBuilder for its original purposes of purely generating new Contracts.
 * NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
 * Updated all major interfaces that used the old model to use the new model.
 *
 * Revision 1.10  2003/11/11 21:18:43  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 * <p/>
 * Revision 1.9  2003/11/09 03:27:19  pelle
 * More house keeping and shuffling about mainly pay
 * <p/>
 * Revision 1.8  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 * <p/>
 * Revision 1.7  2003/09/29 23:17:32  pelle
 * Changes to the senders. Now the senders only work with NamedObjectBuilders
 * which are the only NamedObject representations that contain full XML.
 * <p/>
 * Revision 1.6  2003/09/26 23:53:10  pelle
 * Changes mainly in receiver and related fun.
 * First real neuclear stuff in the payment package. Added TransferContract and AssetControllerReceiver.
 * <p/>
 * Revision 1.5  2003/09/26 00:22:07  pelle
 * Cleanups and final changes to code for refactoring of the Verifier and Reader part.
 * <p/>
 * Revision 1.4  2003/09/24 23:56:48  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 * <p/>
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 * <p/>
 * Revision 1.3  2003/09/23 19:16:28  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 * <p/>
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 * <p/>
 * Revision 1.1.1.1  2003/09/19 14:41:29  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 * <p/>
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 * <p/>
 * <p/>
 * Revision 1.2  2003/02/16 00:23:05  pelle
 * LogSender now works and there is a corresponding server side cgi script to do the logging in
 * http://neuclear.org/logger/ Site is not yet up but will be soon.
 * <p/>
 * Revision 1.1  2003/02/14 21:10:34  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
 * default receiver.
 */
public final class LogSender extends Sender {
    public final SignedNamedObject send(final String endpoint, final SignedNamedObject obj) throws NeuClearException {
        try {
            final String digest = URLEncoder.encode(Base64.encode(obj.getDigest()), "UTF-8");
            final String name = URLEncoder.encode(obj.getName(), "UTF-8");
            final URL url = new URL(Utility.denullString(endpoint, LOGGER) + "?nohtml=1&name=" + name + "&digest=" + digest);
            url.openStream();

//            BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
//            String line=reader.readLine();
//            if (!line.substring(0,2).equals("OK")) // TODO We need to be able to sense if there is a real error
//                System.err.println("Error logging: "+line);
            //throw new NeuClearException("Object wasn't logged");
        } catch (MalformedURLException e) {
            Utility.rethrowException(e);
        } catch (IOException e) {
            Utility.rethrowException(e);
        }
        return null;//

    }

/*
    public static void main(String args[]) {
        try {
            logObject("neu://free");
            logObject("neu://free/pelle");
            logObject("neu://pelle");
            System.out.println("Object neu://free/pelle was logged at: " + getTimeStamp(NSResolver.resolveIdentity("neu://free/pelle")));
        } catch (NeuClearException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }
*/

    public static Timestamp getTimeStamp(final String endpoint, final byte[] rdigest) throws NeuClearException {
        try {
            final String digest = Base64.encode(rdigest);
//            System.out.println(digest);
            final String encdigest = URLEncoder.encode(digest, "UTF-8");
            URL url = null;
            url = new URL(LOGGER + "?mode=Query&nohtml=1&digest=" + encdigest);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            final String line = reader.readLine();
//            System.out.println(line);
            final int pos = line.indexOf('\t');
            if (pos >= 0) {
                final String stamp = line.substring(0, pos);
                return TimeTools.parseTimeStamp(stamp);
            }
        } catch (MalformedURLException e) {
            Utility.rethrowException(e);  //To change body of catch statement use Options | File Templates.
        } catch (IOException e) {
            Utility.rethrowException(e);  //To change body of catch statement use Options | File Templates.
        }

        return null;
    }

    public static Timestamp getTimeStamp(final SignedNamedObject obj) throws NeuClearException {
        return getTimeStamp(Utility.denullString(obj.getSignatory().getLogger(), LOGGER), obj.getEncoded().getBytes());

    }

/*
    private static void logObject(String name) throws NeuClearException {
        System.out.print("Fetching...");
        SignedNamedObject obj = NSResolver.resolveIdentity(name);
        System.out.println("Got " + obj.getName());
        Sender log = new LogSender();
        System.out.print("Logging...");
        log.send(LOGGER, obj);
        System.out.println("Done");
    }

*/
    public static final String LOGGER = "http://logger.neuclear.org/log.cgi";
}
