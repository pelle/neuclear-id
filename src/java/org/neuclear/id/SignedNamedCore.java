/*
 * $Id: SignedNamedCore.java,v 1.26 2004/05/11 16:00:31 pelle Exp $
 * $Log: SignedNamedCore.java,v $
 * Revision 1.26  2004/05/11 16:00:31  pelle
 * Changed the way hashCode works. Now hashCode is created during instantiation as the integer value of the first 4 bytes of the raw digest.
 * equals has been fixed
 * SignedNamedObject uses the above changes.
 *
 * Revision 1.25  2004/04/23 19:10:12  pelle
 * Lots of cleanups and improvements to the userinterface and look of the bux application.
 *
 * Revision 1.24  2004/04/15 15:33:40  pelle
 * Made the SignedNamedCore and SignedNamedObject unserializable for security reasons.
 *
 * Revision 1.23  2004/04/01 23:19:49  pelle
 * Split Identity into Signatory and Identity class.
 * Identity remains a signed named object and will in the future just be used for self declared information.
 * Signatory now contains the PublicKey etc and is NOT a signed object.
 *
 * Revision 1.22  2004/03/20 17:19:55  pelle
 * The problem with Enveloped signatures has now been fixed. It was a problem in the way transforms work. I have bandaided it, but in the future if better support for transforms need to be made, we need to rethink it a bit. Perhaps using the new crypto channel's in neuclear-commons.
 *
 * Revision 1.21  2004/03/03 23:26:42  pelle
 * Updated various tests to use the AbstractObjectCreationTest
 *
 * Revision 1.20  2004/03/02 18:59:11  pelle
 * Further cleanups in neuclear-id. Moved everything under id.
 *
 * Revision 1.19  2004/02/18 00:14:32  pelle
 * Many, many clean ups. I've readded Targets in a new method.
 * Gotten rid of NamedObjectBuilder and revamped Identity and Resolvers
 *
 * Revision 1.18  2004/01/20 17:39:12  pelle
 * Further updates to unit tests
 *
 * Revision 1.17  2004/01/19 23:49:45  pelle
 * Unit testing uncovered further issues with Base32
 * NSTools is now uptodate as are many other classes. All transactional builders habe been updated.
 * Well on the way towards full "green" on Junit.
 *
 * Revision 1.16  2004/01/19 17:54:59  pelle
 * Updated the NeuClear ID naming scheme to support various levels of semantics
 *
 * Revision 1.15  2004/01/18 21:20:29  pelle
 * Created Base32 encoder that now fully complies with Tyler's spec.
 *
 * Revision 1.14  2004/01/16 23:42:09  pelle
 * Added Base32 class. The Base32 encoding used wasnt following the standards.
 * Added user creatable Identity for Public Keys
 *
 * Revision 1.13  2004/01/14 06:42:15  pelle
 * Got rid of the verifyXXX() methods
 *
 * Revision 1.12  2004/01/13 23:38:26  pelle
 * Refactoring parts of the core of XMLSignature. There shouldnt be any real API changes.
 *
 * Revision 1.11  2004/01/10 00:03:21  pelle
 * Implemented new Schema for Transfer*
 * Working on it for Exchange*, so far all Receipts are implemented.
 * Added SignedNamedDocument which is a generic SignedNamedObject that works with all Signed XML.
 * Changed SignedNamedObject.getDigest() from byte array to String.
 * The whole malarchy in neuclear-pay does not build yet. The refactoring is a big job, but getting there.
 *
 * Revision 1.10  2004/01/09 16:34:40  pelle
 * changed use of base36 encoding to base32 to ensure compatibility with other schemes.
 *
 * Revision 1.9  2004/01/08 23:39:06  pelle
 * XMLSignature can now give you the Signing key and the id of the signer.
 * SignedElement can now self verify using embedded public keys as well as KeyName's
 * Added NeuclearKeyResolver for resolving public key's from Identity certificates.
 * SignedNamedObjects can now generate their own name using the following format:
 * neu:sha1://[sha1 of PublicKey]![sha1 of full signed object]
 * The resulting object has a special internally generted Identity containing the PublicKey
 * Identity can now contain nothing but a public key
 *
 * Revision 1.8  2003/12/20 00:21:19  pelle
 * overwrote the standard Object.toString(), hashCode() and equals() methods for SignedNamedObject/Core
 * fixed cactus tests
 * Added TransferRequestServlet
 * Added cactus tests to pay
 *
 * Revision 1.7  2003/12/19 18:03:34  pelle
 * Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
 * - For most cases the main exception to worry about now is InvalidNamedObjectException.
 * - Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
 *   runtime exception.
 * - Source and Store patterns each now have their own exceptions that generalizes the various physical
 *   exceptions that can happen in that area.
 *
 * Revision 1.6  2003/12/11 23:57:29  pelle
 * Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
 * Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.
 *
 * Revision 1.5  2003/12/10 23:58:51  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.4  2003/12/08 19:32:32  pelle
 * Added support for the http scheme into ID. See http://neuclear.org/archives/000195.html
 *
 * Revision 1.3  2003/11/21 13:57:27  pelle
 * Changed some mutable fields in immutable classes, making them truely immutable. Thus safer.
 *
 * Revision 1.2  2003/11/21 04:45:13  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.1  2003/11/20 16:01:25  pelle
 * Did a security review of the basic Verification process and needed to make changes.
 * I've introduced the SignedNamedCore which all subclasses of SignedNamedObject need to include in their constructor.
 * What does this mean?
 * It means that all subclasses of SignedNamedObject have a guaranteed "signed final ticket" that can only be created in one place.
 * This also simplifies the constructors as well as the NamedObjectReaders.
 * I've gone through making everything in these contracts that is possible final. Thus further ensuring the security.
 *
 * Revision 1.10  2003/11/19 23:33:59  pelle
 * Signers now can generatekeys via the generateKey() method.
 * Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
 * SignedNamedObject now contains the full xml which is returned with getEncoded()
 * This means that it is now possible to further receive on or process a SignedNamedObject, leaving
 * NamedObjectBuilder for its original purposes of purely generating new Contracts.
 * NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
 * Updated all major interfaces that used the old model to use the new model.
 *
 * Revision 1.9  2003/11/11 21:18:43  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.8  2003/11/10 21:08:49  pelle
 * More JavaDoc
 *
 * Revision 1.7  2003/10/25 00:39:54  pelle
 * Fixed SmtpSender it now sends the messages.
 * Refactored CommandLineSigner. Now it simply signs files read from command line. However new class IdentityCreator
 * is subclassed and creates new Identities. You can subclass CommandLineSigner to create your own variants.
 * Several problems with configuration. Trying to solve at the moment. Updated PicoContainer to beta-2
 *
 * Revision 1.6  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.5  2003/10/01 19:08:31  pelle
 * Changed XML Format. Now NameSpace has been modified to Identity also the
 * xml namespace prefix nsdl has been changed to neuid.
 * The standard constants for using these have been moved into NSTools.
 * The NamedObjectBuilder can also now take an Element, such as an unsigned template.
 *
 * Revision 1.4  2003/10/01 17:05:37  pelle
 * Moved the NeuClearCertificate class to be an inner class of Identity.
 *
 * Revision 1.3  2003/09/30 23:25:14  pelle
 * Added new JCE Provider and java Certificate implementation for NeuClear Identity.
 *
 * Revision 1.2  2003/09/29 23:17:31  pelle
 * Changes to the senders. Now the senders only work with NamedObjectBuilders
 * which are the only NamedObject representations that contain full XML.
 *
 * Revision 1.1  2003/09/24 23:56:48  pelle
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
 * Revision 1.1.1.1  2003/09/19 14:41:03  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.13  2003/02/18 14:57:19  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.12  2003/02/14 21:10:30  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method receive() which allows one to receive a named object to the Identity's
 * default receiver.
 *
 * Revision 1.11  2003/02/14 14:04:29  pelle
 * The New Source Classes work and NS resolution works as well.
 * I've renamed Target to TargetReference to prepare for the other main refactoring ahead. Implementation of
 * Senders.
 *
 * Revision 1.10  2003/02/10 22:30:06  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.9  2003/02/09 00:15:52  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.8  2002/12/17 21:53:28  pelle
 * Final changes for refactoring.
 *
 * Revision 1.7  2002/12/17 20:34:39  pelle
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
 * Revision 1.6  2002/10/03 01:51:58  pelle
 * Bunch of smaller fixes for bugs found during deployment.
 * Also a bit more documentation.
 * I'm happy with this being called rev. 0.4
 *
 * Revision 1.5  2002/10/02 21:03:44  pelle
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
 * Revision 1.4  2002/09/25 19:20:15  pelle
 * Added various new schemas and updated most of the existing ones.
 * Added explanation interface for explaining the purpose of a
 * SignedNamedObject to a user. We may want to use XSL instead.
 * Also made the signing webapp look a bit nicer.
 *
 * Revision 1.3  2002/09/21 23:11:13  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * Revision 1.2  2002/09/20 01:15:18  pelle
 * Added prototype webapplication under src/java
 * SOAPServlet appears to work
 * Any webservices taking named objects should subclass from ReceiverServlet
 * SigningServlet is not completely working right now, but
 * will be the main prototype of a web based signer.
 *
 * Other new features are GenericNamedObject for simple instantiation of
 * arbitrary named objects.
 *
 * Revision 1.1.1.1  2002/09/18 10:55:40  pelle
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
 * Revision 1.1.1.1  2002/05/29 10:02:22  pelle
 * Lets try one more time. This is the first rev of the next gen of Neudist
 *
 *
 */
package org.neuclear.id;

import org.dom4j.Element;
import org.neuclear.commons.LowLevelException;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.xml.xmlsec.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PublicKey;
import java.sql.Timestamp;

/**
 * <p>The SignedNamedCore is a non extendible core object used when building SignedNamedObjects.
 * All implementations of SignedNamedObject, must contain this core which implements all the basic features.
 * </p><p>
 * The SignedNamedCore has
 *
 * @see NamedObjectReader
 * @see SignedNamedObject
 * @see org.neuclear.id.verifier.VerifyingReader
 * @see org.neuclear.id.resolver.Resolver
 * @see org.neuclear.id.senders.Sender
 * @see org.neuclear.commons.crypto.signers.Signer
 */
public final class SignedNamedCore {
    /**
     * SignedNamedCore for creating SignedNamedObjects from Nymous sources
     *
     * @param pub
     * @param encoded
     */
    private SignedNamedCore(final PublicKey pub, final String encoded) {
        this.signer = new Signatory(pub);
        final byte[] sha = CryptoTools.digest(encoded.getBytes());
        this.digest = CryptoTools.encodeBase32(sha);
        this.name = "neu:" + signer.getName() + "!" + digest;
        this.timestamp = System.currentTimeMillis();
        this.encoded = encoded;
        this.hashcode = sha[0] | sha[1] << 8 | sha[2] << 16 | sha[3] << 24;
    }

    /**
     * Used to read and authenticate a SignedNamedCore.
     *
     * @param elem
     * @return
     * @throws InvalidNamedObjectException
     */
    public final static SignedNamedCore read(final Element elem) throws InvalidNamedObjectException {
        try {
            return readUnnamed(elem);
        } catch (XMLSecurityException e) {
            throw new InvalidNamedObjectException("Failed Verification");
        }
/*
        final String name = getSignatoryName(elem);
        try {
            if (name==null){ // We have an unnamed object
                return readUnnamed(elem);
            }

            final String signatoryName = NSTools.getSignatoryURI(name);
            final Identity signatory = NSResolver.resolveIdentity(signatoryName);
            PublicKey publicKey = signatory.getPublicKey();
            if (NSTools.isHttpScheme(name) != null) {
                // We have a self signed http authenticated certificate and need to extract
                // the PublicKey from the xml
                final Element allowElement = InvalidNamedObjectException.assertContainsElementQName(elem,createQName("allow"));
                final KeyInfo ki = new KeyInfo(InvalidNamedObjectException.assertContainsElementQName(allowElement, XMLSecTools.createQName("KeyInfo")));
                publicKey = ki.getPublicKey();
            }
            if (XMLSecTools.verifySignature(elem)) {
                final Timestamp timestamp = TimeTools.parseTimeStamp(InvalidNamedObjectException.assertAttributeQName(elem,createQName("timestamp")));
                return new SignedNamedCore(name, signatory, timestamp, encodeElement(elem));
            } else
                throw new InvalidNamedObjectException(name);
        } catch (XMLSecurityException e) {
            throw new InvalidNamedObjectException(name);
        } catch (ParseException e) {
            throw new InvalidNamedObjectException(name,"invalid timestamp");
        } catch (CryptoException e) {
             throw new InvalidNamedObjectException(name,"invalid timestamp");
        }
*/
    }

    private static String encodeElement(final Element elem) {
        try {
            return new String(XMLSecTools.canonicalize(elem));
        } catch (XMLSecurityException e) {
            throw new LowLevelException(e);
        }
    }

    private static SignedNamedCore readUnnamed(final Element elem) throws XMLSecurityException, InvalidNamedObjectException {
        try {
            final XMLSignature sig = new EnvelopedSignature(elem);
            final PublicKey pub = sig.getSignersKey();
            return new SignedNamedCore(pub, encodeElement(elem));
        } catch (InvalidSignatureException e) {
            throw new InvalidNamedObjectException("Unnamed object failed Signature verification");
        }
    }


    /**
     * The full name (URI) of an object
     * 
     * @return String containing the fully qualified URI of an object
     */
    public final String getName() {
        return name;
    }

    /**
     * The time the object was signed
     * 
     * @return 
     */
    public final Timestamp getTimeStamp() {
        return new Timestamp(timestamp);

    }

    /**
     * The Signatory of the current document. If the objects name is <tt>"neu://bob/abc"</tt>, then the signer
     * would be the Identity object <tt>"neu://bob/"</tt>
     * 
     * @return 
     */
    public final Signatory getSignatory() {
        return signer;
    }

    /**
     * The original xml document
     * 
     * @return 
     */
    public final String getEncoded() {
        return encoded;
    }

    public final String getDigest() {
        return digest;
    }

    public final int hashCode() {
        return hashcode;
    }

    public final String toString() {
        return name;    //To change body of overriden methods use Options | File Templates.
    }

    public final boolean equals(Object object) {
        if (object == null)
            return false;
        if (object == this)
            return true;
        if (!(object instanceof SignedNamedCore))
            return false;

        return digest.equals(((SignedNamedCore) object).getDigest());    //To change body of overriden methods use Options | File Templates.
    }

    // Disable Serialization
    private final void writeObject(ObjectInputStream in) throws IOException {
        throw new IOException("object can not be serialized");
    }

    //Disable Cloning
    public final Object clone() throws java.lang.CloneNotSupportedException {
        throw new java.lang.CloneNotSupportedException();
    }

    private final String name;
    private final Signatory signer;
    private final long timestamp;
    private final String encoded;
    private final String digest;
    private final int hashcode;


}
