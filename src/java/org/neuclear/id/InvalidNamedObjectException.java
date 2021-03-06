/*
 * $Id: InvalidNamedObjectException.java,v 1.4 2004/04/18 01:05:16 pelle Exp $
 * $Log: InvalidNamedObjectException.java,v $
 * Revision 1.4  2004/04/18 01:05:16  pelle
 * Forgot to add the <meta name="neu:type" content="Identity"/> tag to files generated by IdentityBuilder and it's sub classes.
 *
 * Revision 1.3  2003/12/19 18:03:34  pelle
 * Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
 * - For most cases the main exception to worry about now is InvalidNamedObjectException.
 * - Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
 *   runtime exception.
 * - Source and Store patterns each now have their own exceptions that generalizes the various physical
 *   exceptions that can happen in that area.
 *
 * Revision 1.2  2003/12/19 00:31:30  pelle
 * Lots of usability changes through out all the passphrase agents and end user tools.
 *
 * Revision 1.1  2003/12/11 23:57:29  pelle
 * Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
 * Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.
 *
 * Revision 1.3  2003/12/10 23:58:51  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.2  2003/11/21 04:45:13  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.1  2003/11/15 01:58:16  pelle
 * More work all around on web applications.
 *
 * Revision 1.4  2003/11/11 21:18:43  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.3  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.2  2003/09/24 23:56:48  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.1  2003/09/23 19:16:27  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:01  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:40:55  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.3  2003/02/14 21:10:26  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method receive() which allows one to receive a named object to the Identity's
 * default receiver.
 *
 * Revision 1.2  2003/02/14 05:10:12  pelle
 * New Source model is implemented.
 * It doesnt quite verify things correctly yet. I'm not yet sure why.
 * CommandLineSigner is simplified to make it easier to use.
 *
 * Revision 1.1.1.1  2002/09/18 10:55:39  pelle
 * First release in new CVS structure.
 * Also first public release.
 * This implemnts simple named objects.
 * - Identity Objects
 * - NSAuth Objects
 *
 * Storage systems
 * - In Memory Storage
 * - Clear text file based storage
 * - Encrypted File Storage (with SHA256 digested filenames)
 * - CachedStorage
 * - SoapStorage
 *
 * Simple SOAP client/server
 * - Simple Single method call SOAP client, for arbitrary dom4j based requests
 * - Simple Abstract SOAP Servlet for implementing http based SOAP Servers
 *
 * Simple XML-Signature Implementation
 * - Based on dom4j
 * - SHA-RSA only
 * - Very simple (likely imperfect) highspeed canonicalizer
 * - Zero support for X509 (We dont like that anyway)
 * - Super Simple
 *
 *
 * Revision 1.1.1.1  2002/05/29 10:02:20  pelle
 * Lets try one more time. This is the first rev of the next gen of Neudist
 *
 *
 */
package org.neuclear.id;

import org.dom4j.Element;
import org.dom4j.QName;
import org.neuclear.commons.NeuClearException;
import org.neuclear.xml.XMLTools;

/**
 * Thrown if an object is invalid. Also contains various helper methods for validating named objects
 */
public final class InvalidNamedObjectException extends NeuClearException {
    public InvalidNamedObjectException(final String name, final String message) {
        super(name + " is an invalid Identity\nCause: " + message);
        this.name = name;
    }

    public InvalidNamedObjectException(final String name, Throwable e) {
        super(name + " is an invalid Identity\nCause: " + e.getLocalizedMessage(), e);
        this.name = name;
    }

    public InvalidNamedObjectException(final String name) {
        super(name + " is an invalid Identity");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void assertElementQName(SignedNamedCore core, Element elem, QName qname) throws InvalidNamedObjectException {
        if (!elem.getQName().equals(qname))
            throw new InvalidNamedObjectException(core.getName(), "Element: " + elem.getQualifiedName() + " should be: " + qname.getQualifiedName());
    }

    public static Element assertContainsElementQName(SignedNamedCore core, Element elem, QName qname) throws InvalidNamedObjectException {
        final Element sub = elem.element(qname);
        if (sub == null)
            throw new InvalidNamedObjectException(core.getName(), "Element: " + elem.getQualifiedName() + " should be: " + qname.getQualifiedName());
        return sub;
    }

    public static Element assertContainsElementId(SignedNamedCore core, Element elem, String id) throws InvalidNamedObjectException {
        final Element sub = XMLTools.getByID(elem, id);
        if (sub == null)
            throw new InvalidNamedObjectException(core.getName(), "Element: " + elem.getQualifiedName() + " doesnt contain an element with id: " + id);
        return sub;
    }

    public static String assertAttributeQName(SignedNamedCore core, Element elem, QName qname) throws InvalidNamedObjectException {
        if (elem.attribute(qname) == null)
            throw new InvalidNamedObjectException(core.getName(), "Element: " + elem.getQualifiedName() + " should contain attribute: " + qname.getQualifiedName());
        return elem.attributeValue(qname);
    }

    public static void assertElementQName(Element elem, QName qname) throws InvalidNamedObjectException {
        if (!elem.getQName().equals(qname))
            throw new InvalidNamedObjectException("unknown", "Element: " + elem.getQualifiedName() + " should be: " + qname.getQualifiedName());
    }

    public static Element assertContainsElementQName(Element elem, QName qname) throws InvalidNamedObjectException {
        final Element sub = elem.element(qname);
        if (sub == null)
            throw new InvalidNamedObjectException("unknown", "Element: " + elem.getQualifiedName() + " should be: " + qname.getQualifiedName());
        return sub;
    }

    public static String assertAttributeQName(Element elem, QName qname) throws InvalidNamedObjectException {
        if (elem.attribute(qname) == null)
            throw new InvalidNamedObjectException("unknown", "Element: " + elem.getQualifiedName() + " should contain attribute: " + qname.getQualifiedName());
        return elem.attributeValue(qname);
    }

    private final String name;
}
