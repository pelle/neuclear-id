/*
 * $Id: SignedNamedCore.java,v 1.4 2003/12/08 19:32:32 pelle Exp $
 * $Log: SignedNamedCore.java,v $
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
 * This means that it is now possible to further send on or process a SignedNamedObject, leaving
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
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
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
import org.dom4j.QName;
import org.dom4j.DocumentHelper;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.time.TimeTools;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.XMLTools;
import org.neuclear.xml.xmlsec.XMLSecTools;
import org.neuclear.xml.xmlsec.KeyInfo;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.id.verifier.VerifyingReader;

import java.sql.Timestamp;
import java.io.InputStream;
import java.security.PublicKey;

/**
 * The SignedNamedObject is a <i>secure</i> object normally encapsulating a Digitally signed contract of some
 * sort.<p>
 * Instances of SignedNamedObject and its sub classes are never instantiated directly by client code.
 * Instead it is created by its Reader inner class. This Reader implements NamedObjectReader and is called by
 * VerifyingReader.<p>
 * In most cases a user will load NamedObject through one of two methods:
 * <ul><li>NSResolver for permanent contracts stored on the internet, such as Identity Certificates</li>
 * <li>The other way they are often received are as return values when sending your own objects to WebServices.</l>
 * </ul>
 * To actually create and sign your own object use the NamedObjectBuilder or its subclasses. Each subclass of
 * SignedNamedObject should have a corresponding subclass of NamedObjectBuilder.<p>
 * These NamedObjectBuilder objects should be signed using your Signer, before being sent on to a web service.
 * 
 * @see NamedObjectReader
 * @see org.neuclear.id.builders.NamedObjectBuilder
 * @see org.neuclear.id.verifier.VerifyingReader
 * @see org.neuclear.id.resolver.NSResolver
 * @see org.neuclear.senders.Sender
 * @see org.neuclear.commons.crypto.signers.Signer
 */
public final class SignedNamedCore  {

    private SignedNamedCore(final String name, final Identity signer, final Timestamp timestamp, final String encoded)  {
        this.name = name;
        this.signer = signer;
        this.timestamp = timestamp.getTime();
        this.encoded = encoded;
    }

    /**
     * Used to read and authenticate a SignedNamedCore.
     * @param elem
     * @return
     * @throws XMLException
     * @throws NeuClearException
     */
    public final static SignedNamedCore read(final Element elem) throws XMLException, NeuClearException {
        final String name = NSTools.normalizeNameURI(elem.attributeValue(getNameAttrQName()));
        final String signatoryName = NSTools.getParentNSURI(name);
        final Identity signatory = NSResolver.resolveIdentity(signatoryName);
        PublicKey publicKey = signatory.getPublicKey();
        if (NSTools.isHttpScheme(name)!=null){
            // We have a self signed http authenticated certificate and need to extract
            // the PublicKey from the xml
            final Element allowElement = elem.element(DocumentHelper.createQName("allow", NSTools.NS_NEUID));
            final KeyInfo ki = new KeyInfo(allowElement.element(XMLSecTools.createQName("KeyInfo")));
            publicKey= ki.getPublicKey();
        }
        if (XMLSecTools.verifySignature(elem, publicKey)) {
            final Timestamp timestamp = TimeTools.parseTimeStamp(elem.attributeValue("timestamp"));
            return new SignedNamedCore( name, signatory, timestamp,new String(XMLSecTools.canonicalize(elem)));
        } else
            throw new InvalidNamedObject(name + " isnt valid");
    }

    /**
     * Solely used by RootIdentity
     * @return
     */
    final static SignedNamedCore createRootCore()  {
        return new SignedNamedCore("neu://",null,new Timestamp(0),null);
    }
    private static QName getNameAttrQName() {
        return DocumentHelper.createQName("name", NSTools.NS_NEUID);

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
     * The Name of an object within it's parent Identity
     * <p/>
     * eg.:<pre>
     * getName() = "neu://test/hello"
     * getLocalName() = "hello":
     * </pre>
     * 
     * @return Name
     */
    public final String getLocalName() {
        final String fullName = getName();
        final int i = fullName.lastIndexOf('/');
        return fullName.substring(i + 1);
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
    public final Identity getSignatory() {
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

    public final byte[] getDigest() {
        return CryptoTools.digest(encoded.getBytes());
    }

    private final String name;
    private final Identity signer;
    private final long timestamp;
    private final String encoded;



}
