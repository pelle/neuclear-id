/*
 * $Id: NSTools.java,v 1.21 2003/12/12 15:12:50 pelle Exp $
 * $Log: NSTools.java,v $
 * Revision 1.21  2003/12/12 15:12:50  pelle
 * The ReceiverServletTest now passes.
 * Add first stab at a SigningServletTest which currently doesnt pass.
 *
 * Revision 1.20  2003/12/11 23:57:29  pelle
 * Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
 * Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.
 *
 * Revision 1.19  2003/12/11 16:16:14  pelle
 * Some changes to make the xml a bit more readable.
 * Also added some helper methods in AbstractElementProxy to make it easier to build objects.
 *
 * Revision 1.18  2003/12/10 23:58:51  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.17  2003/12/08 19:32:32  pelle
 * Added support for the http scheme into ID. See http://neuclear.org/archives/000195.html
 *
 * Revision 1.16  2003/12/06 00:17:03  pelle
 * Updated various areas in NSTools.
 * Updated URI Validation in particular to support new expanded format
 * Updated createUniqueID and friends to be a lot more unique and more efficient.
 * In CryptoTools updated getRandom() to finally use a SecureRandom.
 * Changed CryptoTools.getFormatURLSafe to getBase36 because that is what it really is.
 *
 * Revision 1.15  2003/11/21 04:45:13  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.14  2003/11/18 23:35:45  pelle
 * Payment Web Application is getting there.
 *
 * Revision 1.13  2003/11/11 21:18:43  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.12  2003/10/25 00:39:54  pelle
 * Fixed SmtpSender it now sends the messages.
 * Refactored CommandLineSigner. Now it simply signs files read from command line. However new class IdentityCreator
 * is subclassed and creates new Identities. You can subclass CommandLineSigner to create your own variants.
 * Several problems with configuration. Trying to solve at the moment. Updated PicoContainer to beta-2
 *
 * Revision 1.11  2003/10/23 22:02:36  pelle
 * Moved some certificates to live status at http://repository.neuclear.org
 * Updated NSTools.name2path to support neuids with @ signs.
 *
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
 * First import into the neuclear project. This was originally under the SF neuclear
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
 * The Identity object also has a new method receive() which allows one to receive a named object to the Identity's
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

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.dom4j.*;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.CryptoTools;

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
        if (name == null)
            return "neu://";
        if (!name.startsWith("neu://"))
            name = "neu:/" + name;
        if (!isValidName(name))
            throw new InvalidNamedObjectException("Name: '" + name + "' is not valid");
        return name;
    }

    /**
     * <p>Verifies if a NEU URI is syntactically correct.
     * </p>
     * <p/>
     * The first URI must follow the following Syntax:
     * </p>
     * <table border="2" valign="top">
     * <tr><th>Part</th><th>Required</th><th>Example</th><th>Description</th></tr>
     * <tr><td>prefix</td><td>Y</td><td><b><tt>neu:</tt></b></td><td>The URI prefix</td></tr>
     * <tr><td>scheme</td><td>N</td><td><tt><b>neuid<br/>pgp<br/>x509</td><td>short label identifying PKI Protocol. Must contain a maximum of 6 alphanumeric characters.
     * If not used the default scheme of <b><tt>neuid</tt></b> is used.</td></tr>
     * <tr><td>path prefix</td><td>Y</td><td><b><tt>:/</tt></b></td><td>Indicates the start of the Path segment.</td></tr>
     * <tr><td>User ID</td><td>N</td><td><tt><b>joe.user@</b></tt></td>
     * <td>Optional second level user id. Must start with an alphanumeric character and can contain 0 or more of either alpha
     * numeric characters as well as the following symbols minus ('-'), underscore ('_') and period ('.'). The user id must terminate
     * in with a '@' symbol.
     * </td></tr>
     * <tr><td>Top Level Identifier</td><td>N</td><td><tt><b>test<br/>neuclear.org<br/></b></tt></td>
     * <td>Name of the top level identifier. Must start with an alphanumeric character and can contain 0 or more of either alpha
     * numeric characters as well as the following symbols minus ('-'), underscore ('_') and period ('.'). If the name includes
     * any periods this indicates that the name must follow the https authentication scheme. If not the Identity must be signed by
     * the NeuClear.org root signer.</td></tr>
     * <tr><td>Sub Level Identifiers</td><td>N</td><td><tt><b>/sales<br/>/dev/test<br/></b></tt></td>
     * <td>Identifies Identity levels below a top level identity. This identifier can be repeated 0 or more times.
     * Each identifier must start with a forward slash ('/') followed by an
     * alphanumeric character and can contain 0 or more of either alpha
     * numeric characters as well as the following symbols minus ('-') and underscore ('_'). Note that the sub levels may not contain periods.</td></tr>
     * <tr><td>Transaction Identifier</td><td>N</td><td><tt><b>!12343onetwo</b></tt></td>
     * <td>Identifies transactions signed by the preceding Identity. Must start with an exlamation mark ('!') followed by an
     * alphanumeric character and can contain 0 or more of either alpha
     * numeric characters as well as the following symbols period ('.'), minus ('-') and underscore ('_'). </td></tr>
     * </table>
     * 
     * @param name to test
     * @return boolean
     */
    public static boolean isValidName(final String name) {
        if (Utility.isEmpty(name))
            return false;
        final Matcher matcher = VALID.matcher(name);
        return (matcher.matches());
    }

    /**
     * Returns the URI of the parent Identity for a given NEU Name
     * 
     * @param uri a valid NEU Name
     * @return Parent URI or null if name is the root
     * @throws NeuClearException if name is invalid
     */
    public static String getSignatoryURI(final String uri) throws NeuClearException {
        if (!isValidName(uri))
            throw new InvalidNamedObjectException("Invalid Neu ID: " + uri);
        final int bang = uri.indexOf('!');

        // We hava a Transaction ID. We always return its signer
        if (bang > -1)
            return uri.substring(0, bang);

        final int slash = uri.lastIndexOf('/');
        final int at = uri.indexOf('@');
        // We have a User ID
        if (slash < at)
            return uri.substring(0, slash + 1) + uri.substring(at + 1);
        // We have a top level
        if (uri.charAt(slash - 1) == '/')
            return uri.substring(0, slash + 1);
        //Regular
        return uri.substring(0, slash);
    }

    /**
     * Returns the last part of a NEU URI.
     * 
     * @param uri a valid NEU Name
     * @return Parent URI or null if name is the root
     * @throws NeuClearException if name is invalid
     */
    public static String getLocalName(final String uri) throws NeuClearException {
        if (!isValidName(uri))
            throw new InvalidNamedObjectException("Invalid Neu ID: " + uri);
        final int bang = uri.indexOf('!');

        // We hava a Transaction ID. We always return its signer
        if (bang > -1)
            return uri.substring(bang + 1);

        final int slash = uri.lastIndexOf('/');
        final int at = uri.indexOf('@');
        // We have a User ID
        if (slash < at)
            return uri.substring(slash + 1, at);
        // We have a top level
        if (uri.charAt(slash - 1) == '/')
            return uri.substring(slash + 1);
        //Regular
        return uri.substring(slash + 1);
    }

    /**
     * Creates a Globally Unique ID using the following algorithm:
     * <ol><li>Take given Identity URI</li>
     * <li>Appends Timestamp in ms</li>
     * <li>Appends large random number</li>
     * <li>Appends base36 SHA1 of Requesting Identity URI</li>
     * </ol>
     * Note if there is no Requesting Identity. Place any kind of seed in this parameter
     * 
     * @param signer        N
     * @param requester     
     * @param isTransaction is the new Id supposed to be a transaction id?
     * @return 
     */
    public static String createUniqueNamedID(final String signer, final String requester, final boolean isTransaction) {
        final Digest dig = new SHA1Digest();
        final StringBuffer buffy = new StringBuffer(signer);
        buffy.append((isTransaction ? '!' : '/'));
        buffy.append(System.currentTimeMillis());
        buffy.append(CryptoTools.createRandomID());
        final byte[] output = new byte[dig.getDigestSize()];
        final byte reqbytes[] = requester.getBytes();
        dig.update(reqbytes, 0, reqbytes.length);
        //TODO Add some more stuff like IP addresses etc to digest
        dig.doFinal(output, 0);

        buffy.append(CryptoTools.formatAsBase36(output));
        return buffy.toString();
    }

    /**
     * Creates a Globally Unique Transaction ID using the following algorithm:
     * <ol><li>Take given Identity URI</li>
     * <li>Appends Timestamp in ms</li>
     * <li>Appends large random number</li>
     * <li>Appends base36 SHA1 of Requesting Identity URI</li>
     * </ol>
     * Note if there is no Requesting Identity. Place any kind of seed in this parameter
     * 
     * @param signer    N
     * @param requester 
     * @return 
     */
    public static String createUniqueTransactionID(final String signer, final String requester) {
        return createUniqueNamedID(signer, requester, true);
    }

    /**
     * Convers a NEU Name URI into a path suitable for a filesystem or for inclusion as part of a web url.
     * Essentially it strips the prefixes of the URI. If the URI is of the format <tt>neu://bob@test</tt> it returns it as
     * /test/@bob as bob is essentially below test in the hierarchy.
     * 
     * @param name NEU Name URI
     * @return Path
     * @throws InvalidNamedObjectException If the URI is invalid
     */
    public static String name2path(final String name) throws InvalidNamedObjectException {
        if (!Utility.isEmpty(name)) {
            final Matcher matcher = STRIP_URI_ARROBA.matcher(name);
            if (matcher.matches()) {
                return "/" + Utility.denullString(matcher.group(3)) + (matcher.group(1) != null ? ("/@" + matcher.group(2)) : "") + Utility.denullString(matcher.group(4));
            }
        }
        throw new InvalidNamedObjectException("Invalid NEU ID: " + name);
    }

    /**
     * Checks to see if the following name should be resolved using the HTTP Resolving Scheme
     * 
     * @param name 
     * @return 
     */
    public static String isHttpScheme(final String name) {
        if (!Utility.isEmpty(name)) {
            final Matcher matcher = HTTP_SCHEME.matcher(name);
            if (matcher.matches())
                return "http://" + matcher.group(2) + "/_NEUID"; //TODO switch to https
        }
        return null;

    }

    /**
     * Verifies that Dom4j node is an element or document containing a NamedObject.
     * <br><b>Important</b>
     * It does not verify if it has been signed.
     * 
     * @param node 
     * @return 
     */
    public static boolean isNamedObject(Node node) {

        if (node == null) return false;
        Element elem = null;
        if (node instanceof Document)
            elem = ((Document) node).getRootElement();
        else if (node instanceof Element)
            elem = (Element) node;
        else
            return false;
        return !Utility.isEmpty(elem.attributeValue(DocumentHelper.createQName("name", NS_NEUID)));
    }

    private static final String HTTP_SCHEME_EX = "^neu:(neuid:)?\\/\\/(([\\w-]+\\.)+[\\w-]+)$";
    private static final Pattern HTTP_SCHEME = Pattern.compile(HTTP_SCHEME_EX);

    private static final String NEUID_URI = "http://neuclear.org/neu/neuid";
    public static final Namespace NS_NEUID = DocumentHelper.createNamespace("neuid", NEUID_URI);

    public static final String NEUID_PREFIX = "neuid:";

    private static final String SCHEME_PREFIX = "([\\w]{1,6}:)?";
    private static final String VALID_TOKEN = "[\\w][\\w.-]*";
    private static final String VALID_USER_TOKEN = "(([\\w][\\w.-]*)@)?";
    private static final String VALID_TOP_TOKEN = VALID_USER_TOKEN + "[\\w]([\\w.-]*[\\w])?";
    private static final String VALID_SUB_TOKEN = "(\\/[\\w][\\w-]*)*";
    private static final String VALID_TRAN_TOKEN = "(\\![\\w][\\w.-]*)?";
    private static final String VALID_ID = "^neu:" + SCHEME_PREFIX + "\\/\\/(" + VALID_TOP_TOKEN +
            VALID_SUB_TOKEN + VALID_TRAN_TOKEN + ")?$";

    private static final Pattern VALID = Pattern.compile(VALID_ID);

    private static final String STRIP_URI_ARROBA_EX = "neu://((" + VALID_TOKEN + ")@)?(" + VALID_TOKEN + ")?(" + VALID_SUB_TOKEN + VALID_TRAN_TOKEN + ")$";

    private static final Pattern STRIP_URI_ARROBA = Pattern.compile(STRIP_URI_ARROBA_EX);
}
