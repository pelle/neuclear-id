/*
 * $Id: NSTools.java,v 1.10 2003/10/22 23:16:00 pelle Exp $
 * $Log: NSTools.java,v $
 * Revision 1.10  2003/10/22 23:16:00  pelle
 * Cleaned up some unused stuff in NSTools
 *
 * Revision 1.9  2003/10/22 23:11:43  pelle
 * Updated the getParentURI method to support the new neu://test@home format.
 *
 * Revision 1.8  2003/10/22 22:12:33  pelle
 * Replaced the dependency for the Apache Regex library with JDK1.4's Regex implementation.
 * Changed the valid format of NeuClear ID's to include neu://bob@hello/ formatted ids.
 * These ids are not identical to neu://hello/bob however in both cases neu://hello has to sign the Identity document.
 *
 * Revision 1.7  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.6  2003/10/01 19:08:31  pelle
 * Changed XML Format. Now NameSpace has been modified to Identity also the
 * xml namespace prefix nsdl has been changed to neuid.
 * The standard constants for using these have been moved into NSTools.
 * The NamedObjectBuilder can also now take an Element, such as an unsigned template.
 *
 * Revision 1.5  2003/09/26 00:22:06  pelle
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
 * Revision 1.3  2003/09/23 19:16:27  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:01  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:40:58  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.11  2003/02/14 21:10:28  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
 * default receiver.
 *
 * Revision 1.10  2003/02/14 14:04:28  pelle
 * The New Source Classes work and NS resolution works as well.
 * I've renamed Target to TargetReference to prepare for the other main refactoring ahead. Implementation of
 * Senders.
 *
 * Revision 1.9  2003/02/14 05:37:33  pelle
 * Preliminary tests show that it now works with HttpSource
 *
 * Revision 1.8  2003/02/14 05:10:12  pelle
 * New Source model is implemented.
 * It doesnt quite verify things correctly yet. I'm not yet sure why.
 * CommandLineSigner is simplified to make it easier to use.
 *
 * Revision 1.7  2003/02/09 00:15:52  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.6  2002/12/17 21:40:53  pelle
 * First part of refactoring of SignedNamedObject and SignedObject Interface/Class parings.
 *
 * Revision 1.5  2002/12/17 20:34:39  pelle
 * Lots of changes to core functionality.
 * First of all I've refactored most of the Resolving and verification code. I have a few more things to do
 * on it before I'm happy.
 * There is now a NSResolver class, which handles all the namespace resolution. I took most of the functionality
 * for this out of SignedNamedObject.
 * Then there is the veriifer, which verifies a given SignedNamedObject using the NSResolver.
 * This has simplified the SignedNamedObject classes drastically, leaving them as mainly data objects, which is what they
 * should be.
 * I have also gone around and tightened up security on many different classes, making clases and/or methods final where appropriate.
 * NSCache now operates using http://www.waterken.com's fantastic ADT collections library.
 * Something important has been added, which is a SignRequest named object. This signed object, embeds an unsigned
 * named object for signing by an end users' signing service.
 * Now were almost ready to start seriously implementing AssetIssuers and Transfers, which will be the most important
 * part of the framework.
 *
 * Revision 1.4  2002/12/04 13:52:47  pelle
 * Biggest change is a fix to the Canonicalization Writer It should be a lot more compliant with C14N now.
 * I think the only thing left to do is to sort the element attributes.
 *
 * Revision 1.3  2002/10/02 21:03:44  pelle
 * Major Commit
 * I completely redid the namespace resolving code.
 * It now works correctly with the new store attribute of the namespace
 * And can correctly work out the location of a namespace file
 * by hierarchically signing it.
 * I have also included several top level namespaces and finalised
 * the root namespace.
 * In short all of the above means that we can theoretically call
 * Neubia live now. (Well on my first deployment anyway).
 * There is a new CommandLineSigner utility class which creates and signs
 * namespaces using standard java keystores.
 * I'm now working on updating the documentation, so other people
 * than me might have a chance at using it.
 *
 * Revision 1.2  2002/09/21 23:11:13  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * User: pelleb
 * Date: Sep 10, 2002
 * Time: 2:03:51 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.id;

import org.dom4j.DocumentHelper;
import org.dom4j.Namespace;
import org.neuclear.commons.NeuClearException;
import org.neudist.crypto.CryptoTools;
import org.neudist.utils.Utility;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NSTools {
    private NSTools() {
    };

    /**
     * Takes a valid NEU Name and Creates a URI
     * 
     * @param name Valid NEU Name
     * @return Valid URI
     * @throws NeuClearException If name isn't a valid NEU Name
     */
    public static String normalizeNameURI(String name) throws NeuClearException {
        if (!isValidName(name))
            throw new NeuClearException("Name: '" + name + "' is not valid");
        if (!name.startsWith("neu://"))
            return "neu:/" + name;
        return name;
    }

    /**
     * Name must follow the follwing rules:<br>
     * Available Characters: <pre>a..zA..Z0..9_/.-</pre>      <br>
     * <p/>
     * Name starts with either URI prefix <tt>neu://</tt>
     * or <tt>/</tt>
     * 
     * @param name to test
     * @return boolean
     */
    public static boolean isValidName(String name) {
        if (Utility.isEmpty(name))
            return false;
        Matcher matcher = VALID.matcher(name);
        return (matcher.matches());
    }

    /**
     * Returns the URI of the parent Identity for a given NEU Name
     * 
     * @param name a valid NEU Name
     * @return Parent URI or null if name is the root
     * @throws NeuClearException if name is invalid
     */
    public static String getParentNSURI(String name) throws NeuClearException {
        String uri = normalizeNameURI(name);
        int slash = uri.lastIndexOf('/');
        int at = uri.indexOf('@');
        if (slash < at)
            return uri.substring(0, slash + 1) + uri.substring(at + 1);
        if (uri.equals("neu://") || (slash < 5))
            return "neu://";
        if (slash == 5)
            return uri.substring(0, slash + 1);
        return uri.substring(0, slash);
    }

    public static String createUniqueNamedID(String nameSpace, String reqNameSpace) {
        // Yeah, yeah there are better ways to do this
        String ms = new Long(System.currentTimeMillis() + new Random().nextLong()).toString(); //TODO seed the Random number generator
        byte ticketsrc[] = new byte[ms.length() + reqNameSpace.length()];
        System.arraycopy(ms.getBytes(), 0, ticketsrc, 0, ms.length());
        System.arraycopy(reqNameSpace.getBytes(), 0, ticketsrc, ms.length(), reqNameSpace.length());
        String ticket = CryptoTools.formatAsURLSafe(CryptoTools.digest256(ticketsrc));
        //Lets reuse ticketsrc for memory reasons
        int offset = ms.length() + 1;
        if (reqNameSpace.startsWith("neu://"))
            offset += 5;


        for (int i = offset; i < ticketsrc.length; i++) {
            if (ticketsrc[i] == (byte) '/')
                ticketsrc[i] = (byte) '.';
        }
/*
        byte ticketName[]=new byte[userNameSpace.length()+33]; // Create new Name byte array to hold userNameSpace a '/' and the generated ticket (size 32)
        System.arraycopy(userNameSpace.getBytes(),0,ticketName,0,userNameSpace.length());
        ticketName[userNameSpace.length()]=(byte)'/';
        System.arraycopy(ticket,0,ticketName,userNameSpace.length()+1,ticket.length);
*/
        return nameSpace + '/' + new String(ticketsrc, offset, ticketsrc.length - offset) + '.' + ticket;
    }

    public static String url2path(String name) {
        if (!Utility.isEmpty(name)) {
            int loc = name.indexOf("://");
            if (loc >= 0)
                return name.substring(loc + 2); //leave in one '/'
            else if (name.substring(0, 1).equals("/"))
                return name;
            else
                return "/" + name;
        }
        return "/";
    }

    public static final String NEUID_URI = "http://neudist.org/neu/neuid";
    public static final Namespace NS_NEUID = DocumentHelper.createNamespace("neuid", NEUID_URI);

    public static final String NEUID_PREFIX = "neuid:";
    private static final String VALID_TOKEN = "[\\w.]+";
    private static final String VALID_ID = "^(neu:\\/)?" +
            "(\\/(" + VALID_TOKEN + "@" + VALID_TOKEN + ")?" +
            "((\\/)|" + VALID_TOKEN + ")*)$";

    private static final Pattern VALID = Pattern.compile(VALID_ID);

}
